package io.github.nenepadi.djorna;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQ_CODE = 9001;
    private GoogleSignInClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton mSignInBtn = findViewById(R.id.btnSignIn);
        mSignInBtn.setColorScheme(SignInButton.COLOR_AUTO);
        mSignInBtn.setOnClickListener(LoginActivity.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mClient = GoogleSignIn.getClient(LoginActivity.this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount mAccount = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
        changeUI(mAccount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE){
            Task<GoogleSignInAccount> mTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(mTask);
        }
    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    private void signIn() {
        Intent signInIntent = mClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_CODE);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task){
        try{
            GoogleSignInAccount tAccount = task.getResult(ApiException.class);
            changeUI(tAccount);
        } catch(ApiException apiE){
            changeUI(null);
        }
    }

    private void changeUI(@Nullable GoogleSignInAccount account){
        if(account == null){
            Toast.makeText(LoginActivity.this, "Please make sure you login!", Toast.LENGTH_LONG).show();
        } else{
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("profile_email", account.getEmail());
            mainIntent.putExtra("profile_name", account.getDisplayName());

            // send image ... still thinking if the UI can contain it ..
            mainIntent.putExtra("profile_image", account.getPhotoUrl());
            startActivity(mainIntent);
        }
    }
}
