package SMAP.assignment.QuizAppProject.Database;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class User implements IEntity{
    private String uid;
    private List<String> subscribedQuizzes;
    private String displayName;
    @Exclude
    @Override
    public String getEntityKey()
    {
        return uid;
    }
    @Exclude
    @Override
    public String getCollectionName()
    {
        return "user";
    }
    @Exclude
    public void addSubscribedQuiz(String quizId)
    {
        if(subscribedQuizzes == null)
        {
            subscribedQuizzes = new ArrayList<>();
        }
        subscribedQuizzes.add(quizId);
    }

    public User(){}

    public User(String uid, List<String> subscribedQuizzes, String displayName) {
        this.uid = uid;
        this.subscribedQuizzes = subscribedQuizzes;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getSubscribedQuizzes() {
        return subscribedQuizzes;
    }

    public void setSubscribedQuizzes(List<String> subscribedQuizzes) {
        this.subscribedQuizzes = subscribedQuizzes;
    }
}
