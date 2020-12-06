package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Models.Question;
import SMAP.assignment.QuizAppProject.Models.Quiz;
import SMAP.assignment.QuizAppProject.QuestionAdapter;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.QuestionsViewModel;

public class QuestionsActivity extends AppCompatActivity implements QuestionAdapter.IQuestionItemClickedListener{

    private final String TAG = "QuestionsActivity";
    private QuestionsViewModel vm;
    private RecyclerView rcv;
    private Button btnBack, btnNew;
    private QuestionAdapter adapter;
    private EditText editQuizNameInput;

    private String quizId;
    private String questionId;
    private LiveData<List<Question>> questions;
    private LiveData<Quiz> quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        vm = new ViewModelProvider(this).get(QuestionsViewModel.class);

        Intent intent = getIntent();
        quizId = intent.getStringExtra(Constants.QUIZID);

        // Check if new or existing quiz.

        quiz = vm.getQuiz(quizId);
        questions = vm.getQuestions(quizId);

        setupUI();

        // SEBALLE: Skal questions komme tilbage som livedata<list>
        // eller kan vi observe på en almindelig list inde i quiz.

        vm.getQuestions(quizId).observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Log.d(TAG, "OnChanged in observe called!");
                adapter.updateQuestionList(questions);
            }
        });


    }

    private void setupUI(){

        // Recyclerview setup.
        adapter = new QuestionAdapter(this, this);
        rcv = findViewById(R.id.rcvQuestions);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);

        editQuizNameInput = findViewById(R.id.editQuestionsName);
        editQuizNameInput.setText(quiz.getValue().getName());

        btnBack = findViewById(R.id.btnQuestionsBack);
        btnNew = findViewById(R.id.btnQuestionsNew);



        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionId = null;
                gotoEditQuestionActivity();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuizName();
                finish();
            }
        });
    }


    @Override
    public void onQuestionClicked(int index) {
        questionId = questions.getValue().get(index).getId();
        gotoEditQuestionActivity();
    }

    private void gotoEditQuestionActivity(){
        Intent intent = new Intent(this, EditQuestionActivity.class);
        intent.putExtra(Constants.QUIZID, quizId);
        intent.putExtra(Constants.QUESTIONID, questionId);
        startActivity(intent);
    }

    private void saveQuizName(){
        // Save quiz name if specified, otherwise give name "Unnamed Quiz".
        if(editQuizNameInput.getText().toString() == null) {
            vm.setQuizName(quizId, "Unnamed Quiz");
        } else {
            vm.setQuizName(quizId, editQuizNameInput.getText().toString());
        }
    }
}