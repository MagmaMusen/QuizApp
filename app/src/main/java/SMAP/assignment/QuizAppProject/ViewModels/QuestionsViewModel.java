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
import SMAP.assignment.QuizAppProject.Models.TextImageToTextQuestion;

public class QuestionsViewModel extends AndroidViewModel {

    private String TAG = "QuestionViewModel";
    private MutableLiveData<List<Question>> questions;

    private Repository repo;


    public QuestionsViewModel(@NonNull Application application) {
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
            for(int i = 0; i<20; i++)
            {
                questions.getValue().add(new TextImageToTextQuestion());
            }
            Log.d(TAG, "Added dummy data!");
        }
        return questions;
    }


}
