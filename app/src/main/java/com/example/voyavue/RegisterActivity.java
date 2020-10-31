package com.example.voyavue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voyavue.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText editTxtEmail, editTxtEnterPassword, editTxtReEnterPassword;
    Button btnRegister;
    TextView txtViewAlreadyMember;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtEnterPassword = findViewById(R.id.editTxtEnterPassword);
        editTxtReEnterPassword = findViewById(R.id.editTxtReEnterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtViewAlreadyMember = findViewById(R.id.txtViewAlreadyMember);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            //finish();
        }

        editTxtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtEmail.getText().toString().trim())){
                    editTxtEmail.setError("Email is required!");
                    return;
                }
            }
        });

        editTxtEnterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtEnterPassword.getText().toString().trim())){
                    editTxtEnterPassword.setError("Password is required!");
                    return;
                }
                if (editTxtEnterPassword.getText().toString().trim().length() < 6){
                    editTxtEnterPassword.setError("Password must be greater than 6 characters!");
                    return;
                }
            }
        });

        editTxtReEnterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtReEnterPassword.getText().toString().trim())){
                    editTxtReEnterPassword.setError("Please re-enter password!");
                    return;
                }
                if (editTxtReEnterPassword.getText().toString().trim().equals(editTxtEnterPassword.getText().toString().trim()) != true){
                    editTxtReEnterPassword.setError("Passwords do not match with each other!");
                    return;
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editTxtEmail.getText().toString().trim();
                String password = editTxtEnterPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    editTxtEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editTxtEnterPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 6){
                    editTxtEnterPassword.setError("Password must be greater than 6 characters!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created!", Toast.LENGTH_LONG).show();
                            user = new User("", "", "", email, "", null, "" ,"");
                            startActivity(new Intent(getApplicationContext(), UserInfoRegisterActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        txtViewAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}