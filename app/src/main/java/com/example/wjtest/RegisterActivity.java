package com.example.wjtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvLogin, mTvBirthDate;
    private EditText mEtName, mEtEmail, mEtContact, mEtPassword, mEtConfirmPassword;
    private RadioGroup mRgGender;
    private Button mBtnRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    private String gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar();

        mAuth = FirebaseAuth.getInstance();

        mTvLogin = findViewById(R.id.ar_tvLogin);
        mTvBirthDate = findViewById(R.id.ar_tvBirthDate);
        mEtName = findViewById(R.id.ar_etName);
        mEtEmail = findViewById(R.id.ar_etEmail);
        mEtPassword = findViewById(R.id.ar_etPassword);
        mEtConfirmPassword = findViewById(R.id.ar_etConfirmPassword);
        mEtContact = findViewById(R.id.ar_etContact);
        mRgGender = findViewById(R.id.ar_rgGender);
        mBtnRegister = findViewById(R.id.ar_btnRegister);
        mProgressBar = findViewById(R.id.ar_progressBar);

        mTvLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ar_tvLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.ar_btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name = mEtName.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();
        String dateOfBirth = mTvBirthDate.getText().toString().trim();
        String contacNo = mEtContact.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String confirmPass = mEtConfirmPassword.getText().toString().trim();

        if (name.isEmpty()){
            mEtName.setError("Full name is required!");
            mEtName.requestFocus();
            return;
        }
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

        if (gender.isEmpty()){
            Toast.makeText(this, "Gender is required!", Toast.LENGTH_SHORT).show();
            mRgGender.requestFocus();
            return;
        }

        if (dateOfBirth.equals("Click to select")){
            Toast.makeText(this, "Date of birth is required!", Toast.LENGTH_SHORT).show();
            mTvBirthDate.requestFocus();
            return;
        }

        if (contacNo.isEmpty()){
            mEtContact.setError("Contact number is required!");
            mEtContact.requestFocus();
            return;
        }
        if (contacNo.length() < 10 ){
            mEtContact.setError("Contact number must be at least 10 characters!");
            mEtContact.requestFocus();
            return;
        }

        if (password.isEmpty()){
            mEtPassword.setError("Password is required!");
            mEtPassword.requestFocus();
            return;
        }
        if (password.length() < 8 ){
            mEtPassword.setError("Password should contain at least 8 characters!");
            mEtPassword.requestFocus();
            return;
        }

        if (confirmPass.isEmpty()){
            mEtConfirmPassword.setError("Confirm password is required!");
            mEtConfirmPassword.requestFocus();
            return;
        }
        if (!confirmPass.equals(password)){
            mEtConfirmPassword.setError("Both passwords does not match!");
            mEtConfirmPassword.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(name, email, gender, dateOfBirth, contacNo, "0", true);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User has successfully registered!", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            }
                            else {
                                //redirect to login page
                                Toast.makeText(RegisterActivity.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

    public void rbGender_clicked(View view) {
        int x = mRgGender.getCheckedRadioButtonId();
        RadioButton r = findViewById(x);
        gender = r.getText().toString().trim();
    }

    public void tvBirthDate_clicked(View view) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog birthDate = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //do something
                        mTvBirthDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                year,
                month,
                day
        );

        birthDate.getDatePicker().setMaxDate(System.currentTimeMillis());

        birthDate.show();
    }
}