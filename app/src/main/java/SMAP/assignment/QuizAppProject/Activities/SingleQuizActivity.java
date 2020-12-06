package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Models.Quiz;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.SingleQuizViewModel;

public class SingleQuizActivity extends AppCompatActivity {

    static final String TAG = "SingleQuizActivity";
    private TextView txtTitle, txtQuizOwner;
    private Button btnEdit, btnPlay, btnShareAdd;
    private SingleQuizViewModel vm;
    private String quizId;
    private String userId;
    private LiveData<Quiz> quiz;
    private Boolean isOwner = false;
    private Boolean isShared = false;
    private Boolean isFollowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_quiz);

        // Set view model.
        vm = new ViewModelProvider(this).get(SingleQuizViewModel.class);

        // Unpack intent.
        Intent intent = getIntent();
        quizId = intent.getStringExtra(Constants.QUIZID);
        quiz = vm.getQuiz(quizId);

        if(vm.getCurrentUserId() == null){
            Log.d(TAG, "onCreate: " + "UserId is null!");
            finish();
        }

        // Get user ID.
        userId = quiz.getValue().getUserId();

        // Check if owner.
        isOwner =((userId == vm.getCurrentUserId()) ? true : false);

        if(isOwner){
            // Check if shared.
            isShared = ((quiz.getValue().getShared() == true) ? true : false);
        } else {
            // Check if followed.

            // Add check!!!!!!!!!!!!
        }

        setupUI();
    }

    private void setupUI(){
        // Quiz title setup.
        txtTitle = findViewById(R.id.txtSingleQuizTitle);
        txtTitle.setText(quiz.getValue().getName());

        // Quiz owner setup.
        txtQuizOwner = findViewById(R.id.txtSingleQuizOwner);
        // THIS SHOULD USE DISPLAY NAME.
        txtQuizOwner.setText(this.getResources().getString(R.string.txtSingleQuizOwnerPrefix) + " " + vm.getQuizOwnerDisplayName());

        btnPlay = findViewById(R.id.btnPlaySingleQuiz);
        btnShareAdd = findViewById(R.id.btnShareAddSingleQuiz);
        btnEdit = findViewById(R.id.btnEdit);

        updateShareUI();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoQuestionsActivity();
            }
        });

        btnShareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShareAdd();
            }
        });

    }



    private void toggleShareAdd(){
        if(isOwner) {
            // Toggle shared.
            if(isShared) {
                vm.setShared(false, quizId);
                isShared = false;
            } else {
                vm.setShared(true, quizId);
                isShared = true;
            }
        } else {
            if(isFollowed) {
                vm.setFollow(false, userId, quizId);
                isFollowed = false;
            } else {
                vm.setFollow(true, userId, quizId);
                isFollowed = true;
            }
        }
        updateShareUI();
    }

    private void updateShareUI(){
        if(isOwner) {
            // Quiz is owned by current user.
            // Share button setup.
            if(isShared) {
                btnShareAdd.setText(this.getResources().getString(R.string.btnMakePrivate));
            } else {
                btnShareAdd.setText(this.getResources().getString(R.string.btnShare));
            }
        } else {
            // Quiz is owned by another user.
            // Follow button setup.

            // HER SKAL TJEKKES FOR FOLLOWING
            if(isFollowed) {
                btnShareAdd.setText(this.getResources().getString(R.string.btnUnfollow));
            } else {
                btnShareAdd.setText(this.getResources().getString(R.string.btnFollow));
            }

        }
    }

    private void gotoQuestionsActivity(){
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra(Constants.QUIZID, quizId);
        startActivity(intent);
    }
}

