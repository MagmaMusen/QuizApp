package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import SMAP.assignment.QuizAppProject.Data.Repository;
import SMAP.assignment.QuizAppProject.Models.Quiz;

public class SingleQuizViewModel extends AndroidViewModel {

    private static final String TAG = "SingleQuizViewModel";
    Repository repo;


    public SingleQuizViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getRepository(application);
    }

    public LiveData<Quiz> getQuiz(String quizId){
        MutableLiveData<Quiz> testQuiz = new MutableLiveData<>();
        testQuiz.setValue(new Quiz("fiske quiz", "quizidhere", "magnusidwrong", false));
        return testQuiz;
        //return repo.getQuiz(quizId);
    }


    public String getQuizOwnerDisplayName(){
        // Should check owner of quiz display name through repo!
        return "Magnus";
    }

    public String getCurrentUserId(){
        // Should check currentUser through repo!
        return "magnusid";
    }

    public void setShared(Boolean shareStatus, String quizId) {
        // Should change shared status of quiz.
        Log.d(TAG, "setShared: " + shareStatus);
    }

    public void setFollow(Boolean followStatus, String userId, String quizId) {
        // Should change follow status of quiz.
        Log.d(TAG, "setFollow: " + followStatus);
    }
}
