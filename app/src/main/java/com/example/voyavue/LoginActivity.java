package com.example.voyavue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.User;
import com.example.voyavue.repositories.UserRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editTxtLoginEmail, editTxtLoginPassword;
    Button btnLogin;
    TextView txtViewNewMember;
    ProgressBar progressBarLogin;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            fetchUserInfo(currentUser.getEmail());
        }
    }

    public void fetchUserInfo(String email) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<User> call = apiCall.getUserDetails(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    UserRepo uRepo = UserRepo.getInstance();
                    uRepo.setUser(response.body());

                    startMainActivity(uRepo.getUser().getValue());

                    Log.d("Response", "onResponse: " + response.body().toString());
                } else {
                    Log.d("UserRepo:", "Cannot get user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("UserRepo:", "Cannot get user data");
            }
        });
    }

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
                if (TextUtils.isEmpty(editTxtLoginEmail.getText().toString().trim())) {
                    editTxtLoginEmail.setError("Full name is required!");
                    return;
                }
            }
        });

        editTxtLoginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtLoginPassword.getText().toString().trim())) {
                    editTxtLoginPassword.setError("Password is required!");
                    return;
                }
                if (editTxtLoginPassword.getText().toString().trim().length() < 6) {
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

                if (TextUtils.isEmpty(email)) {
                    editTxtLoginEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTxtLoginPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 6) {
                    editTxtLoginPassword.setError("Password must be greater than 6 characters!");
                    return;
                }

                progressBarLogin.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            fetchUserInfo(user.getEmail());
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBarLogin.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        txtViewNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    void startMainActivity(User loggedInUser) {
        Toast.makeText(LoginActivity.this, "Welcome: " + loggedInUser.getUserName(), Toast.LENGTH_LONG).show();

        if (loggedInUser.isAdmin()) {
            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        finish();

    }
}