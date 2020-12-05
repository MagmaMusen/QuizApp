package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import SMAP.assignment.QuizAppProject.Model.Quiz;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Quiz>> quizzes;
    private MutableLiveData<List<Quiz>> searchQuizzes;

    public LiveData<List<Quiz>> getQuizzes(){
        if(quizzes == null){
            quizzes = new MutableLiveData<List<Quiz>>();
        }
        return quizzes;
    }

    public LiveData<List<Quiz>> searchQuiz(String quizName){
        //Call something backend to get quizzes with name
        return searchQuizzes;
    }

}
