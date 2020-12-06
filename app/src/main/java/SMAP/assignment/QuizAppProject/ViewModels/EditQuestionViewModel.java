package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import SMAP.assignment.QuizAppProject.Data.Repository;
import SMAP.assignment.QuizAppProject.Models.Question;

public class EditQuestionViewModel extends AndroidViewModel {
    Repository repo;

    public EditQuestionViewModel(@NonNull Application application) {
        super(application);

        repo = Repository.getRepository(application);
    }


    public MutableLiveData<Question>getQuestion(String quizId, String questionId) {
        MutableLiveData<Question> test = new MutableLiveData<>();
        Question q = new Question();
        q.setAnswer("Existing answer from firebase");
        q.setId("asdv");
        q.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Quentin_Tarantino_C%C3%A9sars_2014_4.jpg/212px-Quentin_Tarantino_C%C3%A9sars_2014_4.jpg");

        test.setValue(new Question());
        return test;
    }

    public void addQuestion(String quizId, Question question) {
        // Overwrite current questiong if quizId exists and create new if already exists.
    }

    public void removeQuestion(String quizId, String questionId) {
        if(questionId != null) {
            // Remove question from quiz if exists, otherwise do nothing.
        }
    }
}
