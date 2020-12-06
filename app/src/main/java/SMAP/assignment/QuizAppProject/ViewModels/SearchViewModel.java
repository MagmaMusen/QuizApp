package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.Views.SearchActivity;

public class SearchViewModel extends ViewModel {

    private Repository repository;
    MutableLiveData<List<Quiz>> quizzes;
    public SearchViewModel()
    {
        repository = Repository.getInstance();
    }
    public void searchQuiz(String quizName){

        repository.updateQuizList(quizName.toLowerCase());
    }
    public LiveData<List<Quiz>> getQuizzes()
    {
        return repository.getSearch();
    }
    public Boolean toggleFollow(Quiz quiz)
    {
        return repository.toggleFollow(quiz.getEntityKey());
    }
    public void setCurrentQuiz(Quiz quiz)
    {
        repository.setCurrentQuiz(quiz);
    }

}
