package SMAP.assignment.QuizAppProject.Database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Repository implements IRepository{
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static Repository repository;

    private MutableLiveData<List<Quiz>> quizes;
    private static final String TAG = "Repository";
    public LiveData<List<Question>> getQuestions(List<String> ids)
    {
        List<Question> questions = new ArrayList<>();
        for(String id : ids)
        {
            questions.add(getQuestion(id).getResult());
        }
        MutableLiveData<List<Question>> questionData = new MutableLiveData<>();
        questionData.setValue(questions);
        return questionData;
    }
    public LiveData<List<Quiz>> getQuizes()
    {
        if(quizes == null)
        {
            quizes = new MutableLiveData<List<Quiz>>();
            loadQuizData();
        }
        return quizes;
    }
    public void createQuestion(Question entity, Quiz quiz) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = db.collection("question").document(documentName);
        Log.i(TAG, "Creating '" + documentName + "' in '" + "question" + "'.");
        if(documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error creating '" + documentName + "' in '" + "question" + "'!", e);
            }
        }).isSuccessful())
        {
            quiz.getQuestions().add(entity.getEntityKey());
            updateQuiz(quiz);
        }
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
    private void updateQuiz(Quiz entity) {
        final String documentName = entity.getEntityKey();
        DocumentReference documentReference = db.collection("quiz").document(documentName);
        Log.i(TAG, "Updating '" + documentName + "' in '" + "quiz" + "'.");

        documentReference.set(entity).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "There was an error updating '" + documentName + "' in '" + "quiz" + "'.", e);
            }
        }).getResult();
    }
    private void loadQuizData()
    {
        db.collection("quizes")
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
                                    newQuizes.add(quiz);
                                }
                            }
                            quizes.setValue(newQuizes);
                        }
                    }
                });
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
