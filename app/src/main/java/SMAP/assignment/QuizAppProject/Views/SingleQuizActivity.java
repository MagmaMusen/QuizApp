package SMAP.assignment.QuizAppProject.Views;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.User;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.SingleQuizViewModel;

public class SingleQuizActivity extends AppCompatActivity {

    static final String TAG = "SingleQuizActivity";
    private TextView txtTitle, txtQuizOwner;
    private Button btnEdit, btnPlay, btnShareAdd, btnBack, btnDelete;
    private SingleQuizViewModel vm;
    private Quiz quiz;
    private Boolean isOwner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_quiz);

        // Set view model.
        vm = new ViewModelProvider(this).get(SingleQuizViewModel.class);

        quiz = vm.getSelectedQuiz();
        vm.ensureQuizLoaded();
        if(vm.getCurrentUserId() == null){
            Log.d(TAG, "onCreate: " + "UserId is null!");
            finish();
        }
        setupUI();
    }

    private void setupUI(){
        // Quiz title setup.
        txtTitle = findViewById(R.id.txtSingleQuizTitle);
        txtTitle.setText(quiz.getName());

        // Quiz owner setup.
        txtQuizOwner = findViewById(R.id.txtSingleQuizOwner);
        Log.d(TAG, "setupUI: ");
        vm.getQuizOwner().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User owner = documentSnapshot.toObject(User.class);
                Log.d(TAG, "onSuccess: " + owner.getUid() + " ? " + vm.getCurrentUserId());
                if(owner.getUid().equals(vm.getCurrentUserId()))
                {
                    Log.d(TAG, "onSuccess: they are equal");
                    isOwner = true;
                }
                txtQuizOwner.setText(getResource(R.string.txtSingleQuizOwnerPrefix) + " " + owner.getDisplayName());
                updateEditUI();
                toggleUi();
            }
        });

        btnPlay = findViewById(R.id.btnPlaySingleQuiz);
        btnShareAdd = findViewById(R.id.btnShareAddSingleQuiz);
        btnEdit = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnSingleBack);
        btnDelete = findViewById(R.id.btnSingleDelete);


        updateEditUI();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                vm.deleteQuiz(vm.getSelectedQuiz());
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
                //vm.setCurrentQuestion();
                gotoQuestionsActivity();
            }
        });
        btnShareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOwner) {
                    // Toggle shared.
                    Boolean shared = vm.toggleShared();
                    if(shared)
                    {
                        btnShareAdd.setText(getResources().getString(R.string.btnMakePrivate));
                    }
                    else
                    {
                        btnShareAdd.setText(getResources().getString(R.string.btnShare));
                    }
                } else {
                    //Toggle Follow
                    Boolean isFollowed = vm.toggleFollowQuiz();
                    if(isFollowed) {
                        btnShareAdd.setText(getResources().getString(R.string.btnUnfollow));
                    } else {
                        btnShareAdd.setText(getResources().getString(R.string.btnFollow));
                    }

                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlayActivity();
            }
        });
    }
    private String getResource(int id)
    {
        return this.getResources().getString(id);
    }


    //function for button
    private void toggleUi(){
        if(isOwner) {
            // Toggle shared.
            Boolean shared = vm.getSelectedQuiz().getShared();
            if(shared)
            {
                btnShareAdd.setText(this.getResources().getString(R.string.btnMakePrivate));
            }
            else
            {
                btnShareAdd.setText(this.getResources().getString(R.string.btnShare));
            }
        } else {
            //Toggle Follow
            Boolean isFollowed = vm.getFollowed(vm.getSelectedQuiz().getEntityKey());
            if(isFollowed) {
                btnShareAdd.setText(this.getResources().getString(R.string.btnUnfollow));
            } else {
                btnShareAdd.setText(this.getResources().getString(R.string.btnFollow));
            }

        }
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
    private void gotoPlayActivity()
    {
        if(vm.getSelectedQuiz().getQuestions() == null)
        {
            return;
        }
        if(vm.getSelectedQuiz().getQuestions().size() == 0)
        {
            return;
        }
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }
    private void gotoQuestionsActivity(){
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
    }
}

