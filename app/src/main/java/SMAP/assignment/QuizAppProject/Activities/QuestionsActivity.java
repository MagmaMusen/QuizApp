package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

    private QuestionAdapter adapter;

    private String quizId;
    private LiveData<List<Question>> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        vm = new ViewModelProvider(this).get(QuestionsViewModel.class);

        Intent intent = getIntent();
        quizId = intent.getStringExtra(Constants.QUIZID);
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


    }


    @Override
    public void onQuestionClicked(int index) {
        Log.d(TAG,"Click!");
    }
}