package SMAP.assignment.QuizAppProject.Data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;



import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import SMAP.assignment.QuizAppProject.Models.Quiz;
import SMAP.assignment.QuizAppProject.R;

public class Repository {

    private static final String TAG = "Repository";


    private ExecutorService executor;

    private Application application;

    private static Repository instance;

    // Singleton pattern.
    public static Repository getRepository(final Application app) {
        if (instance == null){
           instance = new Repository(app);
        }
        return instance;
    }

    public Repository(Application app){
        application = app;

    }

    public LiveData<Quiz> getQuiz(String quizId) {
        return null;
    }

    public LiveData<List<Quiz>> getQuizzes() {
        return null;
    }




}
