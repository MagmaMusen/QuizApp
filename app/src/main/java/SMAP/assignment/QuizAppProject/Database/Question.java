package SMAP.assignment.QuizAppProject.Database;

import android.text.method.TextKeyListener;

import com.google.firebase.firestore.Exclude;

public class Question implements IEntity<String>{
    private String answer;
    private String question;
    private int image;
    @Exclude
    @Override
    public String getEntityKey()
    {
        return "question";
    }
    public Question() {

    }

    public Question(String answer, String question, int image) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
