package com.example.r3cykl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userEmail;
    EditText userPassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_register_here).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

        userEmail = findViewById(R.id.tb_email);
        userPassword = findViewById(R.id.tb_password);
        //progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void goToRegisterPage() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.text_register_here:
                goToRegisterPage();
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent;
                    intent = new Intent(MainActivity.this, scannerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return;
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
