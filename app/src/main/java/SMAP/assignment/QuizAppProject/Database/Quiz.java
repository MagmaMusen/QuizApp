package SMAP.assignment.QuizAppProject.Database;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Quiz implements IEntity<String>{
    private String name;
    private int userId;
    private int rating;
    private List<String> questionIds;
    @Exclude
    @Override
    public String getEntityKey()
    {
        return "BOB";
    }
    public Quiz(){

    }

    public Quiz(String name, int userId, int rating) {
        this.name = name;
        this.userId = userId;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
}
