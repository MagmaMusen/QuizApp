package SMAP.assignment.QuizAppProject.Models;

import java.util.List;

public class User {
    String displayName;
    String uid;
    List<String> subscribedQuizzes;

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getSubscribedQuizzes() {
        return subscribedQuizzes;
    }
    public void setSubscribedQuizzes(List<String> subscribedQuizzes) {
        this.subscribedQuizzes = subscribedQuizzes;
    }
}

