package com.example.voyavue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.AdminDashBoardInfo;
import com.example.voyavue.repositories.UserRepo;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    Button btnAddVerifiedPost, btnLogOut;
    TextView textViewNumOfUsers, textViewMaleUsers, textViewFemaleUsers;
    TextView textViewTotalNumberOfPosts, textViewNumOfVerifiedPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnAddVerifiedPost = findViewById(R.id.btnAddVerifiedPost);
        btnLogOut = findViewById(R.id.btnLogOut);

        textViewNumOfUsers = findViewById(R.id.textViewNumOfUsers);
        textViewMaleUsers = findViewById(R.id.textViewMaleUsers);
        textViewFemaleUsers = findViewById(R.id.textViewFemaleUsers);
        textViewTotalNumberOfPosts = findViewById(R.id.textViewTotalNumberOfPosts);
        textViewNumOfVerifiedPosts = findViewById(R.id.textViewNumOfVerifiedPosts);

        fetchAdminInfo();

        btnAddVerifiedPost.setOnClickListener(view ->
                startActivity(new Intent(AdminActivity.this, NewPostActivity.class)));

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void fetchAdminInfo() {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<AdminDashBoardInfo> call = apiCall.getAdminDashBoardDetails();
        call.enqueue(new Callback<AdminDashBoardInfo>() {
            @Override
            public void onResponse(Call<AdminDashBoardInfo> call, Response<AdminDashBoardInfo> response) {
                if (response.body() != null) {

                    AdminDashBoardInfo info = response.body();

                    textViewNumOfUsers.setText(info.getNumberOfUsers());
                    textViewMaleUsers.setText(info.getNumberOfMaleUsers());
                    textViewFemaleUsers.setText(info.getNumberOfFemaleUsers());
                    textViewTotalNumberOfPosts.setText(info.getNumberOfPosts());
                    textViewNumOfVerifiedPosts.setText(info.getNumberVerifiedPosts());

                }
            }

            @Override
            public void onFailure(Call<AdminDashBoardInfo> call, Throwable t) {
                Log.d("Admin Activity:", "onFailure: " + t.getMessage());
            }
        });
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        UserRepo uRepo = UserRepo.getInstance();
        uRepo.clearUserInfo();

        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}