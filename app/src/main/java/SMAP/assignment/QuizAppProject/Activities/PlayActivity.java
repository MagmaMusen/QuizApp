package SMAP.assignment.QuizAppProject.Activities;

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
import SMAP.assignment.QuizAppProject.Models.Question;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.PlayViewModel;
import SMAP.assignment.QuizAppProject.ViewModels.QuestionsViewModel;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "PlayActivity";
    private ImageView imgQuestion;
    private TextView txtQuestion, txtQuestionNumber;
    private Button btnExit, btnSkip, btnAnswer;
    private EditText editAnswerInput;
    private PlayViewModel vm;
    private String quizId;
    private int currentQuestionIndex = -1;

    private LiveData<List<Question>> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        setupUI();

        vm = new ViewModelProvider(this).get(PlayViewModel.class);

        Intent intent = getIntent();
        quizId = intent.getStringExtra(Constants.QUIZID);
        questions = vm.getQuestions(quizId);

        // Set index to -1 and go to next question, which is 0.
        currentQuestionIndex = -1;
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
                Toast.makeText(getApplicationContext(),"Skipped, answer was " + questions.getValue().get(currentQuestionIndex).getAnswer(), Toast.LENGTH_SHORT).show();
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
                answerInput = answerInput.replaceAll("\\s+","");
                answerInput = answerInput.toLowerCase();

                String answerCorrect = questions.getValue().get(currentQuestionIndex).getAnswer();
                answerCorrect = answerCorrect.replaceAll("\\s+","");
                answerCorrect = answerCorrect.toLowerCase();

                // Compare.
                if(answerInput.equals(answerCorrect)){
                    // Correct answer.
                    Toast.makeText(getApplicationContext(),"Correct!", Toast.LENGTH_SHORT).show();
                    editAnswerInput.setText("");
                    nextQuestion();

                } else {
                    // Wrong answer.
                    Toast.makeText(getApplicationContext(),"Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void nextQuestion() {

        if(currentQuestionIndex < questions.getValue().size() - 1){
            currentQuestionIndex++;
        } else {
            finish();
        }
        txtQuestionNumber.setText((currentQuestionIndex + 1) + " of " + questions.getValue().size());
        Question q = questions.getValue().get(currentQuestionIndex);
        if(q.getImage() != null) {
            Glide.with(imgQuestion.getContext()).load(q.getImage()).into(imgQuestion);
            Log.d(TAG, "changeQuestion: Glide tried to load image with glide!");
        }

        txtQuestion.setText(q.getQuestionText());
    }
}