package SMAP.assignment.QuizAppProject.Views;

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
import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.Views.Adapters.QuestionAdapter;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.QuestionsViewModel;

public class QuestionsActivity extends AppCompatActivity implements QuestionAdapter.IQuestionItemClickedListener{

    private final String TAG = "QuestionsActivity";
    private QuestionsViewModel vm;
    private RecyclerView rcv;
    private Button btnBack, btnNew;
    private QuestionAdapter adapter;
    private EditText editQuizNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        vm = new ViewModelProvider(this).get(QuestionsViewModel.class);

        setupUI();

        vm.getQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                adapter.updateQuestionList(questions);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        editQuizNameInput.setText(vm.getCurrentQuiz());
        vm.loadQuestions();
    }

    private void setupUI(){

        // Recyclerview setup.
        adapter = new QuestionAdapter(this, this);
        rcv = findViewById(R.id.rcvQuestions);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);

        editQuizNameInput = findViewById(R.id.editQuestionsName);

        btnBack = findViewById(R.id.btnQuestionsBack);
        btnNew = findViewById(R.id.btnQuestionsNew);



        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Question question = new Question();
                question.isNew = true;
                vm.setCurrentQuestion(question);
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
    public void onQuestionClicked(Question question) {
        question.isNew = false;
        vm.setCurrentQuestion(question);
        gotoEditQuestionActivity();
    }

    private void gotoEditQuestionActivity(){
        Intent intent = new Intent(this, EditQuestionActivity.class);
        startActivity(intent);
    }

    private void saveQuizName(){
        String name = editQuizNameInput.getText().toString();
        if(name != null) {
            vm.updateQuiz(name);
        }
    }
}