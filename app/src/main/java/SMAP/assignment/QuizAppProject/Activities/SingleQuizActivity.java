package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import SMAP.assignment.QuizAppProject.R;

public class SingleQuizActivity extends AppCompatActivity {

    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_quiz);

        setupUI();
    }

    private void setupUI(){
        btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoQuestionsActivity();
            }
        });

    }

    private void gotoQuestionsActivity(){
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
    }
}

