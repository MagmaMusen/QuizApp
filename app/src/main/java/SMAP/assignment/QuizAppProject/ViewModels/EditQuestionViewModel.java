package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.Database.Repository;


public class EditQuestionViewModel extends AndroidViewModel {
    Repository repository;

    public EditQuestionViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();
    }

    public void deleteQuestion(Question question)
    {
        repository.delete(question);
    }
    public void updateQuestion(Question question)
    {
        repository.update(question);
    }
    public void createNewQuestion(Question question)
    {
        repository.createQuestion(question);
    }
    public Question getCurrentQuestion()
    {
        return repository.getCurrentQuestion();
    }
}
