package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.EditQuestionViewModel;

public class EditQuestionActivity extends AppCompatActivity {

    Button btnCancel, btnSave, btnDelete;
    ImageView imgQuestion;
    EditText editAnswer, editQuestion;
    EditQuestionViewModel vm;
    String quizId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        vm = new ViewModelProvider(this).get(EditQuestionViewModel.class);
        Intent intent = getIntent();

        // We need a question Id
        quizId = intent.getStringExtra(Constants.QUIZID);


        setupUI();
    }

    private void setupUI() {
        // Find views.
        btnCancel = findViewById(R.id.btnEditCancel);
        btnSave = findViewById(R.id.btnEditSave);
        btnDelete = findViewById(R.id.btnEditDelete);
        imgQuestion = findViewById(R.id.imgEditQuestionImage);
        editAnswer = findViewById(R.id.editEditAnswerText);
        editQuestion = findViewById(R.id.editEditAnswerText);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}