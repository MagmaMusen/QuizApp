package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import SMAP.assignment.QuizAppProject.Model.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.Views.Adapters.SearchAdapter;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.ISearchItemClickedListener {

    private RecyclerView rcvSearch;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //setup RecyclerView
        searchAdapter = new SearchAdapter(this);
        rcvSearch = findViewById(R.id.rcvSearchQuizzes);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(searchAdapter);


        //TODO: Update rcv here
    }


    public void addQuiz(Quiz quizClicked){
        //Go to Single Quiz View
    }

    @Override
    public void onSearchClicked(int index) {
        //Do nothing when clicked on?
    }
}