package SMAP.assignment.QuizAppProject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.Database.User;

public class SignInActivity extends AppCompatActivity {
    //Button btn_signIn, btn_add, btn_load;
    //Button btn_rate;
    private Button btnLogin;
    private EditText edtUsername

    EditText edt_displayName;
    FirebaseAuth auth;
    Repository repository;
    Boolean newData = false;
    String username;
    private static final String TAG = "SignInActivity";
    public static final int REQUEST_LOGIN = 1010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpUI();
        if (auth.getCurrentUser() != null) {
            gotoActivity(auth.getCurrentUser().getUid());
        }
        repository.getQuizzes().observe(this, new Observer<List<Quiz>>() {
            @Override
            public void onChanged(List<Quiz> quizzes) {
                newData = true;
            }
        });

    }
    private void setUpUI()
    {
        repository = Repository.getInstance();
        if(auth == null)
        {
            auth = FirebaseAuth.getInstance();
        }
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        /*
        edt_displayName = findViewById(R.id.edt_displayName);

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        btn_load = findViewById(R.id.btn_load);
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        btn_rate = findViewById(R.id.btn_rating);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate();
            }
        });

        btn_signIn = findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edt_displayName.getText().toString();
                signIn();
            }
        });*/
    }
    private boolean signIn() {

        //add here if we want more login options
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), REQUEST_LOGIN
        );
        return true;
    }
    public void rate()
    {
        
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_LOGIN)
            {
                String uid = auth.getCurrentUser().getUid();
                repository.createUser(new User(uid, null, username));
                gotoActivity(uid);
            }

        }
    }
    private void add()
    {
        repository.createQuiz(new Quiz("Test quiz", auth.getCurrentUser().getUid(), false));

    }
    private void load()
    {
        List<Quiz> quizzes = new ArrayList<>(repository.getQuizzes().getValue());
        if(quizzes != null)
        {
            for(Quiz q : quizzes)
            {
                if(!q.getShared())
                {
                    Log.d(TAG, "load: something went wrong");
                }
            }
        }
    }
    private void gotoActivity(String uid)
    {
        Toast.makeText(this, "signed in", Toast.LENGTH_SHORT).show();
        repository.createUser(new User(auth.getCurrentUser().getUid(), null, edt_displayName.getText().toString()));
    }
}