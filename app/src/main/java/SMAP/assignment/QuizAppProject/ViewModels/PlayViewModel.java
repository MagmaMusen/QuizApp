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

    public PlayViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();
    }
    public List<Question> getQuestions()
    {
        return repository.getQuestions().getValue();
    }
}
