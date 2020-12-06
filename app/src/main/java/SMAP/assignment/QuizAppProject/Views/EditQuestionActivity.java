package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.EditQuestionViewModel;

public class EditQuestionActivity extends AppCompatActivity {

    private static final String TAG = "EditQuestionActivity";

    Button btnCancel, btnSave, btnDelete;
    ImageView imgQuestion;
    EditText editAnswer, editQuestion, editImage;
    EditQuestionViewModel vm;
    Boolean newQuestion;

    private Question question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        vm = new ViewModelProvider(this).get(EditQuestionViewModel.class);
        question = vm.getCurrentQuestion();
        setupUI();
    }

    private void setupUI() {
        // Find views.
        btnCancel = findViewById(R.id.btnEditCancel);
        btnSave = findViewById(R.id.btnEditSave);
        btnDelete = findViewById(R.id.btnEditDelete);
        imgQuestion = findViewById(R.id.imgEditQuestionImage);
        editAnswer = findViewById(R.id.editEditAnswerText);
        editQuestion = findViewById(R.id.editEditQuestionText);
        editImage = findViewById(R.id.editEditImageText);

        prepopulateEditField();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = editImage.getText().toString();
                String answer = editAnswer.getText().toString();
                String question = editQuestion.getText().toString();
                Question editQuestion = vm.getCurrentQuestion();
                editQuestion.setAnswer(answer);
                editQuestion.setQuestion(question);
                editQuestion.setImage(image);
                if(editQuestion.isNew)
                {
                    vm.createNewQuestion(editQuestion);
                }
                else
                {
                    vm.updateQuestion(editQuestion);
                }
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.deleteQuestion(question);
                finish();
            }
        });

    }
    public void prepopulateEditField()
    {
        if(question != null) {
            editAnswer.setText(question.getAnswer());
            editQuestion.setText(question.getQuestion());
            editImage.setText(question.getImage());
        }
    }
}