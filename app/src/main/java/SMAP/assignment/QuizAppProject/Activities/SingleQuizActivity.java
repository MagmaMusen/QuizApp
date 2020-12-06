package SMAP.assignment.QuizAppProject.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Models.Quiz;
import SMAP.assignment.QuizAppProject.Models.User;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.SingleQuizViewModel;

public class SingleQuizActivity extends AppCompatActivity {

    static final String TAG = "SingleQuizActivity";
    private TextView txtTitle, txtQuizOwner;
    private Button btnEdit, btnPlay, btnShareAdd, btnBack, btnDelete;
    private SingleQuizViewModel vm;
    private String quizId;
    private String ownerId;
    private LiveData<Quiz> quiz;
    private LiveData<User> user;
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

        // DEBUG DELETE THIS TEST QUIZID!!!!!!!!!!!!!!!!!!!!!!!!!!
        quizId = "magnusquizid";

        quiz = vm.getQuiz(quizId);

        // Get current user.
        user = vm.getCurrentUser();

        if(user == null){
            Log.d(TAG, "onCreate: Couldn't get user!");
            finish();
        }

        // Get quiz owner ID.
        ownerId = quiz.getValue().getUserId();

        // Check if user is quiz owner.
        isOwner = ((ownerId == user.getValue().getUid()) ? true : false);

        if(isOwner){
            // Check if sharing.
            isShared = ((quiz.getValue().getShared() == true) ? true : false);
        } else {
            // Check if following.
            isFollowed = ((user.getValue().getSubscribedQuizzes().contains(quizId)) ? true : false);
            Log.d(TAG, "onCreate: IS FOLLOWED: " + isFollowed +" " + user.getValue().getSubscribedQuizzes().contains(quizId) + quizId );

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
        btnBack = findViewById(R.id.btnSingleBack);
        btnDelete = findViewById(R.id.btnSingleDelete);

        updateEditUI();
        updateShareUI();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                vm.deleteQuiz(quizId);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SingleQuizActivity.this);
                builder.setMessage("Delete quiz?").setPositiveButton("Delete", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlayActivity();
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
                vm.setFollow(false, user.getValue().getUid(), quizId);
                isFollowed = false;
            } else {
                vm.setFollow(true, user.getValue().getUid(), quizId);
                isFollowed = true;
            }
        }
        updateShareUI();
    }

    private void updateEditUI(){
        // Enables and disables edit and delete buttons depending on ownership.
        if(isOwner) {
            btnDelete.setEnabled(true);
            btnEdit.setEnabled(true);
        } else {
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
        }
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

    private void gotoPlayActivity(){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(Constants.QUIZID, quizId);
        startActivity(intent);
    }

    private void gotoQuestionsActivity(){
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra(Constants.QUIZID, quizId);
        startActivity(intent);
    }
}

