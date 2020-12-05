package SMAP.assignment.QuizAppProject.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.Views.SearchActivity;

public class SearchViewModel extends ViewModel {

    private Repository repository;
    MutableLiveData<List<Quiz>> quizzes;
    public SearchViewModel()
    {
        repository = Repository.getInstance();
    }
    public void searchQuiz(final String quizName){

        repository.getAllQuizzes().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot value) {
                if(value != null && !value.isEmpty())
                {
                    List<Quiz> newData = new ArrayList<>();
                    for(DocumentSnapshot doc : value.getDocuments())
                    {
                        Quiz quiz = doc.toObject(Quiz.class);
                        if(quiz != null && quiz.getName().contains(quizName))
                        {
                            quiz.setEntityKey(doc.getId());
                            newData.add(quiz);
                        }
                        quizzes.setValue(newData);
                    }
                }
            }
        });
    }
    public LiveData<List<Quiz>> getQuizzes()
    {
        if(quizzes == null)
        {
            quizzes = new MutableLiveData<>();
        }
        return quizzes;
    }
    public void addQuiz(Quiz quiz)
    {
        repository.addQuiz(quiz.getEntityKey());
    }
    public String getQuizId(int index)
    {
        return null;//quizzes.get(index).getEntityKey();
    }
}
