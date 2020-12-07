package com.example.voyavue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {

    Button btnAddVerifiedPost;
    TextView textViewNumOfUsers, textViewAvgPostPerUser, textViewMaleUsers, textViewFemaleUsers;
    TextView textViewTotalNumberOfPosts, textViewAvgNumOfLikes, textViewNumOfVerifiedPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnAddVerifiedPost = findViewById(R.id.btnAddVerifiedPost);
        textViewNumOfUsers = findViewById(R.id.textViewNumOfUsers);
        textViewAvgPostPerUser = findViewById(R.id.textViewAvgPostPerUser);
        textViewMaleUsers = findViewById(R.id.textViewMaleUsers);
        textViewFemaleUsers = findViewById(R.id.textViewFemaleUsers);
        textViewTotalNumberOfPosts = findViewById(R.id.textViewTotalNumberOfPosts);
        textViewAvgNumOfLikes = findViewById(R.id.textViewAvgNumOfLikes);
        textViewNumOfVerifiedPosts = findViewById(R.id.textViewNumOfVerifiedPosts);

        btnAddVerifiedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, NewPostActivity.class));
            }
        });
    }
}