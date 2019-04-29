package com.example.r3cykl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userEmail;
    EditText userPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.text_login).setOnClickListener(this);

        userEmail = findViewById(R.id.tb_email);
        userPassword = findViewById(R.id.tb_password);
        //progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    public void goToLoginPage(View view) {
        startActivity(new Intent(this, MainActivity.class));

    }

    private void registerUser(){
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Enter a valid Email");
            userEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            userPassword.setError("Enter a password");
            userPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            userPassword.setError("password is too short");
            userPassword.requestFocus();
            return;
        }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Success: You're are now a friend our planet",Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent = new Intent(RegisterActivity.this, userHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Youre already registered", Toast.LENGTH_SHORT).show();
                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                }
            });

        }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                Toast.makeText(getApplicationContext(), "Success: You're are now a friend our planet",Toast.LENGTH_SHORT).show();
                registerUser();
                break;
            case R.id.text_login:
                break;
        }

//    public void registerUser(View view) {
//    }
    }
}
