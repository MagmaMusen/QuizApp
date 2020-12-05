package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.ListViewModel;
import SMAP.assignment.QuizAppProject.Views.Adapters.ListAdapter;

public class ListActivity extends AppCompatActivity implements ListAdapter.IListItemClickedListener {

    //UI Widgets
    private Button btnSearch, btnCreate;
    private TextView txtLoggedInUser;
    private RecyclerView rcvQuizzes;
    private ListAdapter listAdapter;

    private ListViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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


    vm = new ViewModelProvider(this).get(ListViewModel.class);
    vm.getQuizzes().observe(this, new Observer<List<Quiz>>() {
        @Override
        public void onChanged(List<Quiz> quizzes) {
            listAdapter.updateQuizList(quizzes);
        }
    });


    //setup recyclerview
        listAdapter = new ListAdapter(this);
        rcvQuizzes = findViewById(R.id.rcvQuizzes);
        rcvQuizzes.setLayoutManager(new LinearLayoutManager(this));
        rcvQuizzes.setAdapter(listAdapter);

    //TODO: Update rcv here

    }

    public void GoToSearch(){
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i,101);
    }

    //TODO: skal rettes til så den passer til det View Magnus har lavet
    public void GoToCreate(){
        //Intent i = new Intent(this, QuestionActivity.class);
        //startActivityForResult(i, 102);
    }

    @Override
    public void onListClicked(int index) {
        //TODO: skal også rettes til så den passer
        //Intent i = new Intent(this, SingleQuizActivity.class);
        //i.putExtra("QuizData", vm.getQuiz().getValue().get(index).getId());
        //startActivityForResult(i, 103);
    }
}