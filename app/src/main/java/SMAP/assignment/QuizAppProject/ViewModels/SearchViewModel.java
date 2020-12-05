package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Quiz>> quizzes;
    private Repository repository;
    private MutableLiveData<List<Quiz>> searchQuizzes;

    public SearchViewModel()
    {
        repository = Repository.getInstance();
    }
    public LiveData<List<Quiz>> getQuizzes(){
        if(quizzes == null){
            quizzes = new MutableLiveData<List<Quiz>>();
        }
        return quizzes;
    }

    public LiveData<List<Quiz>> searchQuiz(String quizName){
        searchQuizzes.setValue(repository.searchQuizByName(quizName));
        return searchQuizzes;
    }

}
