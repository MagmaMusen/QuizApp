package SMAP.assignment.QuizAppProject.Database;

import android.text.method.TextKeyListener;

import com.google.firebase.firestore.Exclude;

public class Question implements IEntity{
    private String answer;
    private String question;
    private String image;
    @Exclude
    public Boolean isNew;

    public Question() {

    }

    public Question(String answer, String question, String image) {
        this.answer = answer;
        this.question = question;
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Exclude
    @Override
    public String getEntityKey() {
        return id;
    }
    @Exclude
    public void setEntityKey(String id)
    {
        this.id = id;
    }
    @Exclude
    String id;
    @Exclude
    @Override
    public String getCollectionName() {
        return "question";
    }
}
