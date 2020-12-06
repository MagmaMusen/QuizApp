package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.PlayViewModel;

public class PlayActivity extends AppCompatActivity {

    private ImageView imgQuestion;
    private TextView txtQuestion;
    private Button btnExit, btnSkip, btnMore, btnLess, btnAnswer;
    private EditText editAnswerInput;
    private PlayViewModel vm;
    private String quizId;
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        setupUI();

        vm = new ViewModelProvider(this).get(PlayViewModel.class);

        List<Question> questions = vm.getQuestions();

        // We need weights on individual questions from the user.
    }

    private void setupUI(){
        // Find views.
        imgQuestion = findViewById(R.id.imgPlayQuestion);
        btnExit = findViewById(R.id.btnPlayExit);
        btnAnswer = findViewById(R.id.btnPlayAnswer);
        btnMore = findViewById(R.id.btnPlayMore);
        btnLess = findViewById(R.id.btnPlayLess);
        btnSkip = findViewById(R.id.btnPlaySkip);
        txtQuestion = findViewById(R.id.txtPlayQuestionText);
        editAnswerInput = findViewById(R.id.editPlayAnswerInput);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}