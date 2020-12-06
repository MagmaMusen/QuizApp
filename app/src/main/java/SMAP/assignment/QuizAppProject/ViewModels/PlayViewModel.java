package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.Database.Repository;

public class PlayViewModel  extends AndroidViewModel {
    Repository repository;
    private String TAG = "PlayViewModel";
    private MutableLiveData<List<Question>> questions;

    public PlayViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();
    }

    public LiveData<List<Question>> getQuestions(String quizId){
        //repo.getQuestion for quizId
        // Dummy get.
        Log.d(TAG, "getQuestions called!");
        if(questions == null)
        {
            questions = new MutableLiveData<>();
            questions.setValue(new ArrayList<Question>());
            for(int i = 0; i<20; i++)
            {
                //questions.getValue().add(new TextImageToTextQuestion());
            }
            Log.d(TAG, "Added dummy data!");
        }
        return questions;
    }
    public List<Question> getQuestions()
    {
        return null;
    }
    public void wrongAnswer(String questionId){

    }

}
