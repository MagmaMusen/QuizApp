package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import SMAP.assignment.QuizAppProject.Data.Repository;

public class EditQuestionViewModel extends AndroidViewModel {
    Repository repo;

    public EditQuestionViewModel(@NonNull Application application) {
        super(application);

        repo = Repository.getRepository(application);
    }


}
