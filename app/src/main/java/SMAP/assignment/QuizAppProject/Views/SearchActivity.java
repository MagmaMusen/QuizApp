package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.ListViewModel;
import SMAP.assignment.QuizAppProject.ViewModels.SearchViewModel;
import SMAP.assignment.QuizAppProject.Views.Adapters.SearchAdapter;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ISearchItemClickedListener {

    private Button btnBack, btnSearchConfirm;
    private EditText edtSearch;

    private RecyclerView rcvSearch;
    private SearchAdapter searchAdapter;

    private SearchViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        vm = new ViewModelProvider(this).get(SearchViewModel.class);
        setUpUI();
        vm.getQuizzes().observe(this, new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                searchAdapter.updateQuizList(quizzes);
            }
        });
    }
    private void setUpUI()
    {

        edtSearch = findViewById(R.id.edtSearch);
        btnSearchConfirm = findViewById(R.id.btnSearchConfirm);
        btnSearchConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.searchQuiz(edtSearch.getText().toString());
            }
        });
        btnBack = findViewById(R.id.btnBackSearch);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //setup RecyclerView
        searchAdapter = new SearchAdapter(this);
        rcvSearch = findViewById(R.id.rcvSearchQuizzes);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(searchAdapter);
    }

    public void addQuiz(Quiz quiz){
        vm.addQuiz(quiz);
    }

    @Override
    public void onSearchClicked(int index) {
        /*
        Intent i = new Intent(this, SingleQuizActivity.class);
        String quizId = vm.getQuizId(index);
        i.putExtra("QUIZ_ID", quizId);

         */
    }
}