package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.User;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.Services.RecommendationService;
import SMAP.assignment.QuizAppProject.ViewModels.ListViewModel;
import SMAP.assignment.QuizAppProject.Views.Adapters.ListAdapter;

public class ListActivity extends AppCompatActivity implements ListAdapter.IListItemClickedListener {

    //UI Widgets
    private static final String TAG = "ListActivity";
    private Button btnSearch, btnCreate;
    private TextView txtLoggedInUser;
    private RecyclerView rcvQuizzes;
    private ListAdapter listAdapter;

    private ListViewModel vm;

    private String username = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        vm = new ViewModelProvider(this).get(ListViewModel.class);
        setUpUI();
        vm.getQuizzes().observe(this, new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                listAdapter.updateQuizList(quizzes);
            }
        });

        Log.d(TAG, "onCreate: Run!");

        // Start service that recommends a random shared quiz periodically.
        Intent RecommendServiceIntent = new Intent(this, RecommendationService.class);
        startService(RecommendServiceIntent);
    }

    private void setUpUI()
    {
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToSearch();
            }
        });

        btnCreate = findViewById(R.id.btnCreateQuiz);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToCreate();
            }
        });

        txtLoggedInUser = findViewById(R.id.txtLoggedInUser);

        txtLoggedInUser.setText(vm.getCurrentUserName());
        listAdapter = new ListAdapter(this);
        rcvQuizzes = findViewById(R.id.rcvQuizzes);
        rcvQuizzes.setLayoutManager(new LinearLayoutManager(this));
        rcvQuizzes.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.updateSubscriberList();
    }

    public void GoToSearch(){

        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    public void GoToCreate(){
        vm.createQuiz();
        vm.clearQuestions();
        Intent i = new Intent(this, QuestionsActivity.class);
        startActivity(i);
    }
    @Override
    public void onListClicked(Quiz quiz) {

        vm.setCurrentQuiz(quiz);
        Intent i = new Intent(this, SingleQuizActivity.class);
        startActivity(i);
    }
}