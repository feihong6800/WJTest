package com.example.wjtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvRegister;
    private EditText mEtEmail, mEtPassword;
    private Button mBtnLogin;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mTvRegister = findViewById(R.id.al_tvRegister);
        mEtEmail = findViewById(R.id.al_etEmail);
        mEtPassword = findViewById(R.id.al_etPassword);
        mBtnLogin = findViewById(R.id.al_btnLogin);
        mProgressBar = findViewById(R.id.al_progressBar);

        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.al_tvRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.al_btnLogin:
                loginUser();
                break;
        }
    }

    private void loginUser() {
        String email = mEtEmail.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        if (email.isEmpty()){
            mEtEmail.setError("Email is required!");
            mEtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEtEmail.setError("Email address is invalid!");
            mEtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            mEtPassword.setError("Password is required!");
            mEtPassword.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //redirect to homepage
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RecordActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login failed! Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}