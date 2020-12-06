package SMAP.assignment.QuizAppProject.Activities;

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
import SMAP.assignment.QuizAppProject.Models.Question;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.EditQuestionViewModel;

public class EditQuestionActivity extends AppCompatActivity {

    private static final String TAG = "EditQuestionActivity";

    Button btnCancel, btnSave, btnDelete;
    ImageView imgQuestion;
    MutableLiveData<Question> question;
    EditText editAnswer, editQuestion, editImage;
    EditQuestionViewModel vm;
    String quizId, questionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);


        vm = new ViewModelProvider(this).get(EditQuestionViewModel.class);

        setupUI();

        // Unpack intent.
        Intent intent = getIntent();
        quizId = intent.getStringExtra(Constants.QUIZID);
        questionId = intent.getStringExtra(Constants.QUESTIONID);

        //DEBUG
        quizId = "asdfasdfasdf";

        if(quizId == null) {
            Log.d(TAG, "onCreate: ERROR no quizId!");
            finish();
        }

        if(questionId == null){
            question = new MutableLiveData<>();

        } else {
            question = vm.getQuestion(quizId, questionId);
            prepopulateEditField();
        }
        editAnswer.setText("Test");
        editQuestion.setText("Test");
        editImage.setText("Test");


    }

    private void setupUI() {
        // Find views.
        btnCancel = findViewById(R.id.btnEditCancel);
        btnSave = findViewById(R.id.btnEditSave);
        btnDelete = findViewById(R.id.btnEditDelete);
        imgQuestion = findViewById(R.id.imgEditQuestionImage);
        editAnswer = findViewById(R.id.editEditAnswerText);
        editQuestion = findViewById(R.id.editEditAnswerText);
        editImage = findViewById(R.id.editEditImageText);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.getValue().setImage(editImage.getText().toString());
                question.getValue().setAnswer(editAnswer.getText().toString());
                question.getValue().setQuestionText(editQuestion.getText().toString());

                vm.addQuestion(quizId, question.getValue());
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.removeQuestion(quizId, questionId);
                finish();
            }
        });
        }

    public void prepopulateEditField(){
        if(question != null) {
            editAnswer.setText(question.getValue().getAnswer());
            editQuestion.setText(question.getValue().getQuestionText());
            editImage.setText(question.getValue().getImage());
        }
    }
}