package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;

public class QuestionsViewModel extends AndroidViewModel {

    private String TAG = "QuestionViewModel";
    private MutableLiveData<List<Question>> questions;

    private Repository repository;


    public QuestionsViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();
    }
    //TODO watch this
    public void setCurrentQuestion(Question question)
    {
        repository.setCurrentQuestion(question);
    }

    public LiveData<List<Question>> getQuestions(){
        return repository.getQuestions();
    }
    public void loadQuestions()
    {
        repository.loadQuestions();
    }
    public String getCurrentQuiz()
    {
        return repository.getCurrentQuiz().getName();
    }
    public void updateQuiz(String name)
    {
        repository.getCurrentQuiz().setName(name);
        repository.update(repository.getCurrentQuiz());
    }


}
