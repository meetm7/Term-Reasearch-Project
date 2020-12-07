package com.example.voyavue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText editTxtEmail, editTxtEnterPassword, editTxtReEnterPassword, editTxtUserName, editTxtMobNo, editTxtDOB, editTxtFirstName, editTxtLastName, editTxtBio;
    Button btnRegister;
    TextView txtViewAlreadyMember;
    ProgressBar progressBar;
    Spinner spinnerSex;

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
        editTxtUserName = findViewById(R.id.editTxtUserName);
        editTxtMobNo = findViewById(R.id.editTxtMobNo);
        editTxtDOB = findViewById(R.id.editTxtDOB);
        editTxtFirstName = findViewById(R.id.editTxtFirstName);
        editTxtLastName = findViewById(R.id.editTxtLastName);
        editTxtBio = findViewById(R.id.editTxtBio);

        spinnerSex = findViewById(R.id.spinnerSex);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        editTxtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtEmail.getText().toString().trim())) {
                    editTxtEmail.setError("Email is required!");
                    return;
                }
            }
        });

        editTxtEnterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtEnterPassword.getText().toString().trim())) {
                    editTxtEnterPassword.setError("Password is required!");
                    return;
                }
                if (editTxtEnterPassword.getText().toString().trim().length() < 6) {
                    editTxtEnterPassword.setError("Password must be greater than 6 characters!");
                    return;
                }
            }
        });

        editTxtReEnterPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtReEnterPassword.getText().toString().trim())) {
                    editTxtReEnterPassword.setError("Please re-enter password!");
                    return;
                }
                if (editTxtReEnterPassword.getText().toString().trim().equals(editTxtEnterPassword.getText().toString().trim()) != true) {
                    editTxtReEnterPassword.setError("Passwords do not match with each other!");
                    return;
                }
            }
        });

        editTxtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtUserName.getText().toString().trim())) {
                    editTxtUserName.setError("Username is required!");
                    return;
                }
            }
        });

        editTxtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtFirstName.getText().toString().trim())) {
                    editTxtFirstName.setError("First name is required!");
                    return;
                }
            }
        });

        editTxtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (TextUtils.isEmpty(editTxtLastName.getText().toString().trim())) {
                    editTxtLastName.setError("Last name is required!");
                    return;
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editTxtEmail.getText().toString().trim();
                String password = editTxtEnterPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTxtEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTxtEnterPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 6) {
                    editTxtEnterPassword.setError("Password must be greater than 6 characters!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created!", Toast.LENGTH_LONG).show();
                            user = new User(editTxtFirstName.getText().toString(),
                                    editTxtLastName.getText().toString(),
                                    editTxtUserName.getText().toString(),
                                    editTxtEmail.getText().toString(),
                                    editTxtMobNo.getText().toString(),
                                    editTxtDOB.getText().toString(),
                                    spinnerSex.getSelectedItem().toString(),
                                    editTxtBio.getText().toString(),
                                    false
                            );

                            addUserNewUser(user);

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        txtViewAlreadyMember.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }

    public void addUserNewUser(User userInfo) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<User> call = apiCall.addUser(userInfo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    UserRepo uRepo = UserRepo.getInstance();

                    uRepo.setUser(response.body());

                    if (uRepo.getUser().getValue().isAdmin()) {
                        startActivity(new Intent(RegisterActivity.this, AdminActivity.class));
                    } else {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("UserRepo", "Cannot create user");
            }
        });
    }
}