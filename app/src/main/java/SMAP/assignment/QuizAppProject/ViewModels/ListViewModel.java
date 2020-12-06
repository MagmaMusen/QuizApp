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

    FirebaseAuth auth;
    Repository repository;

    public ListViewModel()
    {
        repository = Repository.getInstance();
    }
    public String createQuiz()
    {
        return repository.createQuiz(new Quiz("Default", auth.getCurrentUser().getUid(), false));
    }
    public String getCurrentUserName()
    {
        return repository.getCurrentUser().getDisplayName();
    }
    public void updateSubscriberList()
    {
        if(repository.getCurrentUser().getSubscribedQuizzes() == null)
        {
            return;
        }
        repository.loadSubscribed();
    }
    public LiveData<List<Quiz>> getQuizzes(){
        return repository.getSubscribed();
    }
    /*
    public String getQuizId(int index)
    {
        return quizzes.getValue().get(index).getEntityKey();
    }

     */
}
