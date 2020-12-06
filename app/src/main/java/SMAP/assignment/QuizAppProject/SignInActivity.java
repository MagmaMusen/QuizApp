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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.Database.Repository;
import SMAP.assignment.QuizAppProject.Database.User;
import SMAP.assignment.QuizAppProject.Views.ListActivity;

public class SignInActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUsername;
    FirebaseAuth auth;
    Repository repository;
    String username;
    private static final String TAG = "SignInActivity";
    public static final int REQUEST_LOGIN = 1010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpUI();
        if (auth.getCurrentUser() != null) {
            setUser();
        }
    }
    private void setUpUI()
    {
        repository = Repository.getInstance();
        if(auth == null)
        {
            auth = FirebaseAuth.getInstance();
        }
        edtUsername = findViewById(R.id.edtUsername);
        btnLogin = findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    private boolean signIn() {
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_LOGIN)
            {
                repository.createUser(new User(auth.getCurrentUser().getUid(), null, edtUsername.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                gotoActivity();
                            }
                        });
            }
        }
    }
    private void setUser()
    {
        repository.setCurrentUser().addOnSuccessListener(new OnSuccessListener<User>() {
            @Override
            public void onSuccess(User user) {
                gotoActivity();
            }
        });

    }
    private void gotoActivity()
    {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
}