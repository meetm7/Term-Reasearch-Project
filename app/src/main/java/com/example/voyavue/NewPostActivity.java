package com.example.voyavue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    //Button btnUpload, btnSave, btnFecth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        encoded = null;

        //btnUpload = findViewById(R.id.btnUpload);
        //btnSave = findViewById(R.id.btn_save);
        //btnFecth = findViewById(R.id.btn_fetch);

//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(
//                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                savePost();
//            }
//        });
//
//        btnFecth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fetchPost();
//            }
//        });

        ImageView imgViewPost = findViewById(R.id.imgViewPost);
        imgViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });


    }

    private void fetchPost() {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<List<Post>> call = apiCall.getPosts(UserRepo.getInstance().getUser().getValue().getUserName());

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("TAG", "onResponse: " + response.body());

                byte[] encodeByte = Base64.decode(response.body().get(0).getImg(), Base64.DEFAULT);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                //ImageView imageView2 = (ImageView) findViewById(R.id.imgView2);
                //imageView2.setImageBitmap(bitmap2);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("TAG", "onResponse: Failed");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            InputStream inputStream = null;

            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ImageView imageView = (ImageView) findViewById(R.id.imgViewPost);
            imageView.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            encoded = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);

            byte[] encodeByte = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        }
    }

    private void savePost() {

        final Post post = new Post(UserRepo.getInstance().getUser().getValue().getUserName(),
                encoded,
                "new Image",
                2,
                "img desc",
                "img Tag",
                "location",
                "time to",
                "expe ",
                false,
                false);

        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<Post> call = apiCall.addPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d("TAG", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}