package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.Database.User;

public class ListViewModel extends ViewModel {

    MutableLiveData<List<Quiz>> quizzes;
    FirebaseAuth auth;
    Repository repository;

    public ListViewModel()
    {
        quizzes = new MutableLiveData<>();
        repository = Repository.getInstance();
    }
    public String createQuiz()
    {
        return repository.createQuiz(new Quiz("Default", auth.getCurrentUser().getUid(), false));
    }
    public Task<User> getCurrentUserName()
    {
        return repository.getCurrentUser();
    }
    public void updateSubscriberList()
    {
        repository.loadSubscribed().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                        quizzes.setValue(newData);
                    }
                }
            }
        });
    }
    public LiveData<List<Quiz>> getQuizzes(){
        return quizzes;
    }
    public String getQuizId(int index)
    {
        return quizzes.getValue().get(index).getEntityKey();
    }
}
