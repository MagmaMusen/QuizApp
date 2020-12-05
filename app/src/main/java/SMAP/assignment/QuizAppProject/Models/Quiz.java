package SMAP.assignment.QuizAppProject.Models;

import java.util.List;

public class Quiz {
    private String name;
    private List<String> questions;
    private String id;
    private String userId;
    private Boolean shared;

    public Quiz(String name, String id, String userId, Boolean shared){
        this.name = name;
        this.id = id;
        this.userId = userId;
        this.shared = shared;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuestions() {
        return questions;
    }
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getShared() {
        return shared;
    }
    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
