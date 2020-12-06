package SMAP.assignment.QuizAppProject.Models;

public class Question {

    private String id;
    private String answer = "George Clooney";
    private String image = "https://upload.wikimedia.org/wikipedia/commons/7/74/GeorgeClooneyHWoFJan12_%28headshot%29.jpg";
    private String questionText = "Who is this actor?";

    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer(){
        return answer;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
