package SMAP.assignment.QuizAppProject.Database;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;


public class Repository{
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static Repository repository;
    private User user;

    private void setCurrentUser(User user)
    {
        this.user = user;
    }
    private User getCurrentUser()
    {
        return user;
    }
    private void getMyQuizzes()
    {

    }
    private MutableLiveData<List<Quiz>> quizzes;
    private static final String TAG = "Repository";
    public void createUser(final User user)
    {
        db.collection("user").document(auth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setCurrentUser(user);
            }
        });
    }
    public void setCurrentUserDisplayName(String name)
    {
        FirebaseUser user = auth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
    public LiveData<List<Question>> getQuestions(List<String> ids)
    {
        List<Question> questions = new ArrayList<>();
        for(String id : ids)
        {
            questions.add(getQuestion(id));
        }
        MutableLiveData<List<Question>> questionData = new MutableLiveData<>();
        questionData.setValue(questions);
        return questionData;
    }
    public LiveData<List<Quiz>> getQuizzes()
    {
        if(quizzes == null)
        {
            quizzes = new MutableLiveData<>();
            loadQuizData();
        }
        return quizzes;
    }
    public void createQuiz(final Quiz quiz)
    {
        db.collection("quiz").add(quiz).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                getCurrentUser().addSubscribedQuiz(documentReference.getId());
            }
        });
    }
    public void createQuestion(Question question, final Quiz quiz) {
        CollectionReference cf = db.collection("question");
        cf.add(question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //evt problem her (by ref or copy)
                        quiz.getQuestions().add(documentReference.getId());
                        //update
                        update(quiz);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error creating '" + "item" + "' in '" + "questions" + "'!", e);
            }
        });
    }
    private Question getQuestion(String id)
    {
        final String documentName = id;
        DocumentReference documentReference = db.collection("questions").document(documentName);
        Log.i(TAG, "Getting '" + documentName + "' in '" + "questions" + "'.");
        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, Question>() {
            @Override
            public Question then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    return documentSnapshot.toObject(Question.class);
                } else {
                    Log.d(TAG, "Document '" + documentName + "' does not exist in '" + "questions" + "'.");
                    return Question.class.newInstance();
                }
            }
        }).getResult();
    }
    private void update(IEntity entity) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = db.collection(entity.getCollectionName()).document(documentName);

        documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error updating '" + documentName + "' in '" + "quiz" + "'.", e);
            }
        });
    }
    public void setRating(Quiz quiz, int rating)
    {
        quiz.setRating(rating);
        update(quiz);
    }
    public List<Quiz> getMostPopularQuizzes()
    {
        final List<Quiz> quizzes = new ArrayList<>();
        CollectionReference cf = db.collection("quiz");
        cf.orderBy("rating", Query.Direction.ASCENDING).limit(2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null)
                {

                    for(QueryDocumentSnapshot snap :queryDocumentSnapshots)
                    {
                        quizzes.add(snap.toObject(Quiz.class));
                    }
                }
            }
        });
        return quizzes;
    }
    public Task<Boolean> existsQuiz(final String documentName) {
        DocumentReference documentReference = db.collection("quiz").document(documentName);
        Log.i(TAG, "Checking existence of '" + documentName + "' in '" + "quiz" + "'.");

        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<DocumentSnapshot> task) {
                Log.d(TAG,"Checking if '" + documentName + "' exists in '" + "quiz" +"'.");
                return task.getResult().exists();
            }
        });
    }
    private void loadQuizData() //todo only load shared quizzes
    {
        db.collection("quiz").whereEqualTo("shared", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value != null && !value.isEmpty())
                        {
                            List<Quiz> newQuizes = new ArrayList<>();
                            for(DocumentSnapshot doc : value.getDocuments())
                            {
                                Quiz quiz = doc.toObject(Quiz.class);
                                if(quiz != null)
                                {
                                    quiz.setEntityKey(doc.getId());
                                    newQuizes.add(quiz);
                                }
                            }
                            quizzes.setValue(newQuizes);
                        }
                    }
                });
        getMyQuizzes();
    }
    private Repository()
    {
        auth = FirebaseAuth.getInstance();
         db = FirebaseFirestore.getInstance();
    }
    public static Repository getInstance()
    {
        if(repository == null)
        {
            repository = new Repository();
        }
        return repository;
    }


}