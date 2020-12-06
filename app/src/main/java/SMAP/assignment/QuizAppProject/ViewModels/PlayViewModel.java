package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Data.Repository;
import SMAP.assignment.QuizAppProject.Models.Question;

public class PlayViewModel  extends AndroidViewModel {
    Repository repo;
    private String TAG = "PlayViewModel";
    private MutableLiveData<List<Question>> questions;

    public PlayViewModel(@NonNull Application application) {
        super(application);

        repo = Repository.getRepository(application);
    }

    public LiveData<List<Question>> getQuestions(String quizId){
        //repo.getQuestion for quizId
        // Dummy get.
        Log.d(TAG, "getQuestions called!");
        if(questions == null)
        {
            questions = new MutableLiveData<>();
            questions.setValue(new ArrayList<Question>());
            for(int i = 0; i<3; i++)
            {
                questions.getValue().add(new Question());
            }
            questions.getValue().get(1).setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Leonardo_DiCaprio_2014.jpg/265px-Leonardo_DiCaprio_2014.jpg");
            questions.getValue().get(2).setImage("https://upload.wikimedia.org/wikipedia/commons/c/c4/Streep_san_sebastian_2008_2.jpg");

            Log.d(TAG, "Added dummy data!");
        }
        return questions;
    }

    public void wrongAnswer(String questionId){

    }

}
