package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Data.Repository;
import SMAP.assignment.QuizAppProject.Models.Quiz;
import SMAP.assignment.QuizAppProject.Models.User;

public class SingleQuizViewModel extends AndroidViewModel {

    private static final String TAG = "SingleQuizViewModel";
    Repository repo;


    public SingleQuizViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getRepository(application);
    }

    public LiveData<Quiz> getQuiz(String quizId){
        MutableLiveData<Quiz> testQuiz = new MutableLiveData<>();
        testQuiz.setValue(new Quiz("fiske quiz", "magnusquizid", "magnusid", false));
        return testQuiz;
        //return repo.getQuiz(quizId);
    }

    public void deleteQuiz(String quizId){
        // Delete the entire quiz and remove it from all follow lists etc.
    }

    public String getQuizOwnerDisplayName(){
        // Should check owner of quiz display name through repo!
        return "Magnus";
    }

    public LiveData<User> getCurrentUser(){
        // Should check currentUser through repo!
        List<String> subscribedTo = new ArrayList<>();
        subscribedTo.add("magnusquizid");
        subscribedTo.add("gustavquzid");

        MutableLiveData<User> testuser = new MutableLiveData<>();
        testuser.setValue(new User());
        testuser.getValue().setDisplayName("Magnus");
        testuser.getValue().setSubscribedQuizzes(subscribedTo);
        testuser.getValue().setUid("magnusid");
        return testuser;
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
