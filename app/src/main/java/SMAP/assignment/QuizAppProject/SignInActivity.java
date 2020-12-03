package SMAP.assignment.QuizAppProject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Repository;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    public static final int REQUEST_LOGIN = 1010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpUI();
    }
    private void setUpUI()
    {
        if(auth == null)
        {
            auth = FirebaseAuth.getInstance();
        }
    }
    public boolean signIn() {
        if (auth.getCurrentUser() != null) {
            gotoActivity();
        } else {
            //add here if we want more login options
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );
            startActivityForResult(
                    AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), REQUEST_LOGIN
            );
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_LOGIN)
            {
                String uid = auth.getCurrentUser().getUid();

                gotoActivity();
            }

        }
    }

    private void gotoActivity()
    {
        //go to some activity
    }
}