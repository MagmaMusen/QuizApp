package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import SMAP.assignment.QuizAppProject.Model.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.Views.Adapters.ListAdapter;

public class ListActivity extends AppCompatActivity implements ListAdapter.IListItemClickedListener {

    //UI Widgets
    private Button btnSearch, btnCreate;
    private TextView txtLoggedInUser;
    private RecyclerView rcvQuizzes;
    private ListAdapter listAdapter;

    //Skal komme fra ViewModel i stedet
    private ArrayList<Quiz> quizzes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    btnSearch = findViewById(R.id.btnSearch);
    btnCreate = findViewById(R.id.btnCreateQuiz);
    txtLoggedInUser = findViewById(R.id.txtLoggedInUser);

    //setup recyclerview
        listAdapter = new ListAdapter(this);
        rcvQuizzes = findViewById(R.id.rcvQuizzes);
        rcvQuizzes.setLayoutManager(new LinearLayoutManager(this));
        rcvQuizzes.setAdapter(listAdapter);

    //TODO: Update rcv here

    }

    @Override
    public void onListClicked(int index) {
        //Go to the single QuizView
    }
}