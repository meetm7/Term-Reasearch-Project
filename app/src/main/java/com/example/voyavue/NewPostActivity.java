package com.example.voyavue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.Post;
import com.example.voyavue.repositories.UserRepo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    String encoded;
    EditText editTxtImgTitle, editTxtImgDesc, editTxtTimeToVisit, editTxtCost;
    ImageView imgViewPost;
    Spinner spinnerImgTags, spinnerLocation;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        encoded = null;

        imgViewPost = findViewById(R.id.imgViewPost);
        editTxtImgTitle = findViewById(R.id.editTxtImgTitle);
        editTxtImgDesc = findViewById(R.id.editTxtImgDesc);
        editTxtTimeToVisit = findViewById(R.id.editTxtTimeToVisit);
        editTxtCost = findViewById(R.id.editTxtCost);
        spinnerImgTags = findViewById(R.id.spinnerImgTags);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        btnPost = findViewById(R.id.btnPost);

        imgViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            InputStream inputStream = null;

            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ImageView imageView = findViewById(R.id.imgViewPost);
            imageView.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            encoded = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

        }
    }

    private void savePost() {

        if (TextUtils.isEmpty(editTxtImgTitle.getText().toString()) ||
                TextUtils.isEmpty(editTxtTimeToVisit.getText().toString()) ||
                TextUtils.isEmpty(editTxtCost.getText().toString()) ||
                TextUtils.isEmpty(editTxtImgDesc.getText().toString())){
            Toast.makeText(NewPostActivity.this, "Please fill all the fields before posting", Toast.LENGTH_LONG).show();
            return;
        }

        final Post post = new Post("",UserRepo.getInstance().getUser().getValue().getUserName(),
                encoded,
                editTxtImgTitle.getText().toString(),
                0,
                editTxtImgDesc.getText().toString(),
                spinnerImgTags.getSelectedItem().toString(),
                spinnerLocation.getSelectedItem().toString(),
                editTxtTimeToVisit.getText().toString(),
                editTxtCost.getText().toString(),
                false,
                false);

        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<Post> call = apiCall.addPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(NewPostActivity.this, "Post Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(NewPostActivity.this, "Failed to perform action", Toast.LENGTH_LONG).show();
            }
        });
    }
}