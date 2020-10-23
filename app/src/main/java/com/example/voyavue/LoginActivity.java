package com.example.voyavue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    EditText editTxtLoginEmail, editTxtLoginPassword;
    Button btnLogin;
    TextView txtViewNewMember;
    ProgressBar progressBarLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTxtLoginEmail = findViewById(R.id.editTxtLoginEmail);
        editTxtLoginPassword = findViewById(R.id.editTxtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtViewNewMember = findViewById(R.id.txtViewNewMember);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        mAuth = FirebaseAuth.getInstance();

        editTxtLoginEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtLoginEmail.getText().toString().trim())){
                    editTxtLoginEmail.setError("Full name is required!");
                    return;
                }
            }
        });

        editTxtLoginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtLoginPassword.getText().toString().trim())){
                    editTxtLoginPassword.setError("Password is required!");
                    return;
                }
                if (editTxtLoginPassword.getText().toString().trim().length() < 6){
                    editTxtLoginPassword.setError("Password must be greater than 6 characters!");
                    return;
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTxtLoginEmail.getText().toString().trim();
                String password = editTxtLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    editTxtLoginEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editTxtLoginPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 6){
                    editTxtLoginPassword.setError("Password must be greater than 6 characters!");
                    return;
                }

                progressBarLogin.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "User logged in!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        txtViewNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }
}