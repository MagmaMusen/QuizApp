package SMAP.assignment.QuizAppProject.Database;

import android.text.BoringLayout;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Quiz implements IEntity{
    private String name;
    private String userId;
    private int rating;
    private Boolean shared;
    private List<String> questionIds;
    @Exclude
    private String id;
    @Override
    @Exclude
    public String getCollectionName()
    {
        return "quiz";
    }
    @Exclude
    public void setEntityKey(String id)
    {
        this.id = id;
    }
    @Exclude
    @Override
    public String getEntityKey()
    {
        return id;
    }
    public Quiz(){

    }

    public Quiz(String name, String userId, Boolean shared) {
        this.name = name;
        this.userId = userId;
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<String> getQuestions() {
        return questionIds;
    }

    public void setQuestions(List<String> questions) {
        this.questionIds = questions;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
