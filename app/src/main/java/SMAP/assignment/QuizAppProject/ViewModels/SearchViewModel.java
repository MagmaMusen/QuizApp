package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import SMAP.assignment.QuizAppProject.Model.Quiz;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Quiz>> quizzes;

    public LiveData<List<Quiz>> getQuizzes(){
        if(quizzes == null){
            quizzes = new MutableLiveData<List<Quiz>>();
        }
        return quizzes;
    }

}
