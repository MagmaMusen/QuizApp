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
import android.widget.EditText;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.SearchViewModel;
import SMAP.assignment.QuizAppProject.Views.Adapters.SearchAdapter;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ISearchItemClickedListener {

    private Button btnBack;
    private EditText edtSearch;

    private RecyclerView rcvSearch;
    private SearchAdapter searchAdapter;

    private SearchViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearch = findViewById(R.id.edtSearch);
        btnBack = findViewById(R.id.btnBackSearch);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vm = new ViewModelProvider(this).get(SearchViewModel.class);
        vm.getQuizzes().observe(this, new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                searchAdapter.updateQuizList(quizzes);
            }
        });
        //Søg i database - TODO: Korrekt måde at søge på?
        vm.searchQuiz(edtSearch.getText().toString());


        //setup RecyclerView
        searchAdapter = new SearchAdapter(this);
        rcvSearch = findViewById(R.id.rcvSearchQuizzes);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(searchAdapter);

    }


    public void addQuiz(Quiz quizClicked){
        //TODO: tilpas så den passer
        //Intent i = new Intent(this, SingleQuizActivity.class);
        //Skal måske ikke være 101, siden det er den er i ListActivity
        //startActivityForResult(i, 101);
    }

    @Override
    public void onSearchClicked(int index) {
        //Do nothing when clicked on?
    }
}