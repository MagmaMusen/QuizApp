package SMAP.assignment.QuizAppProject.ViewModels;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;

public class SingleQuizViewModel extends AndroidViewModel {

    private static final String TAG = "SingleQuizViewModel";
    Repository repository;

    private Quiz selectedQuiz;
    public SingleQuizViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public Quiz getSelectedQuiz()
    {
        if(selectedQuiz == null)
        {
            selectedQuiz = repository.getCurrentQuiz();
        }
        return selectedQuiz;
    }
    public Task<DocumentSnapshot> getQuizOwnerDisplayName(){
        return repository.getUserName(selectedQuiz.getUserId());
    }
    public Boolean toggleFollowQuiz()
    {
        return repository.toggleFollow(selectedQuiz.getEntityKey());
    }
    public String getCurrentUserId(){
        // Should check currentUser through repo!
        return repository.getCurrentUser().getUid();
    }

    public Boolean toggleShared() {
        // Should change shared status of quiz.
        return repository.toggleShared(selectedQuiz);
    }
}
