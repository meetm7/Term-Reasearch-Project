package com.example.voyavue.data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.voyavue.MainActivity;
import com.example.voyavue.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private FirebaseAuth mAuth;
    public Result<LoggedInUser> login(String username, String password) {

        mAuth = FirebaseAuth.getInstance();
        try {
            // TODO: handle loggedInUser authentication

            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        new Result.Success<>(user.getEmail());
                    }
                }
            });
            return new Result.Success<>(username);

//            if (username.compareTo("harmeet@gmail.com") == 0) {
//                LoggedInUser fakeUser =
//                        new LoggedInUser(
//                                java.util.UUID.randomUUID().toString(),
//                                username);
//                return new Result.Success<>(fakeUser);
//            }
//
//            else {
//                throw new Exception("User not found!");
//            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}