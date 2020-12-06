package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.PlayViewModel;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "PlayActivity";
    private ImageView imgQuestion;
    private TextView txtQuestion, txtQuestionNumber;
    private Button btnExit, btnSkip, btnAnswer;
    private EditText editAnswerInput;
    private PlayViewModel vm;
    private int currentQuestionIndex = 0;
    private int score;
    private List<Question> quizQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        setupUI();
        vm = new ViewModelProvider(this).get(PlayViewModel.class);

        quizQuestions = vm.getQuestions();
        nextQuestion();
    }

    private void setupUI(){
        // Find views.
        imgQuestion = findViewById(R.id.imgPlayQuestion);
        btnExit = findViewById(R.id.btnPlayExit);
        btnAnswer = findViewById(R.id.btnPlayAnswer);
        btnSkip = findViewById(R.id.btnPlaySkip);
        txtQuestion = findViewById(R.id.txtPlayQuestionText);
        txtQuestionNumber = findViewById(R.id.txtPlayQuestionNumber);
        editAnswerInput = findViewById(R.id.editPlayAnswerInput);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Skipped, answer was " + quizQuestions.get(currentQuestionIndex).getAnswer(), Toast.LENGTH_SHORT).show();
                score--;
                updateIndex();
                nextQuestion();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove whitespace and go to lowercase for answer and input.
                String answerInput = editAnswerInput.getText().toString();
                answerInput = answerInput.replaceAll("\\s+","").toLowerCase();

                String answerCorrect = quizQuestions.get(currentQuestionIndex).getAnswer();
                answerCorrect = answerCorrect.replaceAll("\\s+","").toLowerCase();

                // Compare.
                if(answerInput.equals(answerCorrect)){
                    // Correct answer.
                    Toast.makeText(getApplicationContext(),"Correct!", Toast.LENGTH_SHORT).show();
                    editAnswerInput.setText("");
                    score++;
                    updateIndex();
                    nextQuestion();

                } else {
                    // Wrong answer.
                    Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void nextQuestion() {
        txtQuestionNumber.setText((currentQuestionIndex + 1) + " of " + quizQuestions.size());

        //todo support images somehow
        Question q = quizQuestions.get(currentQuestionIndex);
        txtQuestion.setText(q.getQuestion());
    }
    private void updateIndex()
    {
        currentQuestionIndex++;
        currentQuestionIndex %= quizQuestions.size();
    }
}