package SMAP.assignment.QuizAppProject.Database;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;


public class Repository{
    private static final String TAG = "Repository";
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static Repository repository;
    //user stuff
    public Task<DocumentSnapshot> getUserName(String id)
    {
        return db.collection("user").document(id).get();
    }
    private User user;
    public User getCurrentUser()
    {
        return user;
    }
    private void setUser(User user)
    {
        this.user = user;
    }
    public Task<User> setCurrentUser()
    {
        DocumentReference documentReference = db.collection("user").document(auth.getCurrentUser().getUid());
        return documentReference.get().continueWith(new Continuation<DocumentSnapshot, User>() {
            @Override
            public User then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    return documentSnapshot.toObject(User.class);
                } else {
                    return User.class.newInstance();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<User>() {
            @Override
            public void onSuccess(User user) {
                setUser(user);
            }
        });
    }
    public Task<Void> createUser(final User user)
    {
        return db.collection("user").document(auth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                setUser(user);
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
    //question stuff
    private Question currentQuestion;
    private MutableLiveData<List<Question>> questions;
    public void clearQuestions()
    {
        questions = new MutableLiveData<>();
    }
    public LiveData<List<Question>> getQuestions()
    {
        if(questions == null)
        {
            questions = new MutableLiveData<>();
        }
        return questions;
    }
    public void loadQuestions()
    {
        if(currentQuiz.getQuestions() == null )
        {
            currentQuiz.setQuestions(new ArrayList<String>());
        }
        if(currentQuiz.getQuestions().size() == 0)
        {
            return;
        }
        if(questions == null)
        {
            questions = new MutableLiveData<>();
        }
        db.collection("question")
                .whereIn(FieldPath.documentId(), currentQuiz.getQuestions())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        if(value != null && !value.isEmpty())
                        {
                            List<Question> newData = new ArrayList<>();
                            for(DocumentSnapshot doc : value.getDocuments())
                            {
                                Question question = doc.toObject(Question.class);
                                if(question != null)
                                {
                                    question.setEntityKey(doc.getId());
                                    newData.add(question);
                                }
                            }
                            questions.setValue(newData);
                        }
                    }
                });
    }
    public void setCurrentQuestion(Question question)
    {
        currentQuestion = question;
    }
    public Question getCurrentQuestion()
    {
        return currentQuestion;
    }
    public void createQuestion(Question question) {
        CollectionReference cf = db.collection("question");
        cf.add(question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //evt problem her (by ref or copy)
                        if(currentQuiz.getQuestions() == null)
                        {
                            currentQuiz.setQuestions(new ArrayList<String>());
                        }
                        List<String> quizData = currentQuiz.getQuestions();
                        quizData.add(documentReference.getId());
                        currentQuiz.setQuestions(quizData);
                        //update
                        update(currentQuiz);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "There was an error creating '" + "item" + "' in '" + "questions" + "'!", e);
                    }
                });
    }
    //quiz stuff
    private MutableLiveData<List<Quiz>> subscribedQuizzes;
    public LiveData<List<Quiz>> getSubscribed()
    {
        if(subscribedQuizzes == null)
        {
            subscribedQuizzes = new MutableLiveData<>();
        }
        return subscribedQuizzes;
    }
    public void loadSubscribed()
    {
        db.collection("quiz").whereIn(FieldPath.documentId(), user.getSubscribedQuizzes())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        if(value != null && !value.isEmpty())
                        {
                            List<Quiz> newData = new ArrayList<>();
                            for(DocumentSnapshot doc : value.getDocuments())
                            {
                                Quiz quiz = doc.toObject(Quiz.class);
                                if(quiz != null)
                                {
                                    quiz.setEntityKey(doc.getId());
                                    newData.add(quiz);
                                }
                                subscribedQuizzes.setValue(newData);
                            }
                        }
                    }
                });
    }
    private MutableLiveData<List<Quiz>> searchQuizzes;
    public LiveData<List<Quiz>> getSearch()
    {
        if(searchQuizzes == null)
        {
            searchQuizzes = new MutableLiveData<>();
        }
        return searchQuizzes;
    }
    public void updateQuizList(final String quizName)
    {
        db.collection("quiz").whereEqualTo("shared", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        if(value != null && !value.isEmpty())
                        {
                            List<Quiz> newData = new ArrayList<>();
                            for(DocumentSnapshot doc : value.getDocuments())
                            {
                                Quiz quiz = doc.toObject(Quiz.class);
                                if(quiz != null && quiz.getName().toLowerCase().contains(quizName))
                                {
                                    quiz.setEntityKey(doc.getId());
                                    newData.add(quiz);
                                }
                                searchQuizzes.setValue(newData);
                            }
                        }
                    }
                });
    }


    private MutableLiveData<List<Quiz>> allSharedQuizzes;

    public LiveData<List<Quiz>> getAllSharedQuizzes(){
        if(allSharedQuizzes == null) {
            allSharedQuizzes = new MutableLiveData<>();
        }
        updateSharedQuizzesGetter();
        return allSharedQuizzes;
    }

    private void updateSharedQuizzesGetter(){
        db.collection("quiz").whereEqualTo("shared", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        if(value != null && !value.isEmpty())
                        {
                            List<Quiz> newData = new ArrayList<>();
                            for(DocumentSnapshot doc : value.getDocuments())
                            {
                                Quiz quiz = doc.toObject(Quiz.class);
                                quiz.setEntityKey(doc.getId());
                                newData.add(quiz);

                                allSharedQuizzes.setValue(newData);
                            }
                        }
                    }
                });
    }




    public void createQuiz(final Quiz quiz)
    {
        setCurrentQuiz(quiz);
        db.collection("quiz").add(quiz).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                user.addSubscribedQuiz(documentReference.getId());
                quiz.setEntityKey(documentReference.getId());
                update(user);
                setCurrentQuiz(quiz);
            }
        });

    }
    public Boolean toggleFollow(String quizId)
    {
        if(user.getSubscribedQuizzes() != null)
        {
            if(user.getSubscribedQuizzes().contains(quizId))
            {
                user.getSubscribedQuizzes().remove(quizId);
                return false;
            }
        }
        user.addSubscribedQuiz(quizId);
        update(user);
        return true;
    }

    private Quiz currentQuiz;
    public void setCurrentQuiz(Quiz quiz)
    {
        currentQuiz = quiz;
    }
    public Quiz getCurrentQuiz()
    {
        return currentQuiz;
    }
    public Boolean toggleShared(Quiz quiz)
    {
        quiz.setShared(!quiz.getShared());
        update(quiz);
        return quiz.getShared();
    }
    //general
    public void update(IEntity entity) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = db.collection(entity.getCollectionName()).document(documentName);

        documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error updating '" + documentName + "' in '" + "quiz" + "'.", e);
            }
        });
    }
    public void delete(IEntity entity)
    {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = db.collection(entity.getCollectionName()).document(documentName);

        documentReference.delete();
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
