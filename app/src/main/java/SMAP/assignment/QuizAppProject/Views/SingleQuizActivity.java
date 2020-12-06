package SMAP.assignment.QuizAppProject.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import SMAP.assignment.QuizAppProject.Constants;
import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.User;
import SMAP.assignment.QuizAppProject.R;
import SMAP.assignment.QuizAppProject.ViewModels.SingleQuizViewModel;

public class SingleQuizActivity extends AppCompatActivity {

    static final String TAG = "SingleQuizActivity";
    private TextView txtTitle, txtQuizOwner;
    private Button btnEdit, btnPlay, btnShareAdd;
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
        vm.getQuizOwnerDisplayName().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User owner = documentSnapshot.toObject(User.class);
                txtQuizOwner.setText(getResource(R.string.txtSingleQuizOwnerPrefix) + " " + owner.getDisplayName());
            }
        });

        btnPlay = findViewById(R.id.btnPlaySingleQuiz);
        btnShareAdd = findViewById(R.id.btnShareAddSingleQuiz);
        btnEdit = findViewById(R.id.btnEdit);

        toggleUi();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoQuestionsActivity();
            }
        });

        btnShareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleUi();
            }
        });

    }
    private String getResource(int id)
    {
        return this.getResources().getString(id);
    }


    //function for button
    private void toggleUi(){
        if(quiz.getUserId() == vm.getCurrentUserId()) {
            // Toggle shared.
            Boolean shared = vm.toggleShared();
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
            Boolean isFollowed = vm.toggleFollowQuiz();
            if(isFollowed) {
                btnShareAdd.setText(this.getResources().getString(R.string.btnUnfollow));
            } else {
                btnShareAdd.setText(this.getResources().getString(R.string.btnFollow));
            }

        }
    }
    private void gotoQuestionsActivity(){
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
    }
}

