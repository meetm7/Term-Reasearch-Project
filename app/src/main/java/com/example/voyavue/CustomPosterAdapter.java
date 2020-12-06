package com.example.voyavue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voyavue.models.Post;

import java.util.List;
import java.util.TooManyListenersException;

public class CustomPosterAdapter extends RecyclerView.Adapter<CustomPosterAdapter.CustomViewHolder> {

    List<Post> postLists;
    Context context;

    public CustomPosterAdapter(List<Post> passedPostLists){
        this.postLists = passedPostLists;
    }

    public void ChangeData(List<Post> passedPostLists){
        this.postLists = passedPostLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View postView = layoutInflater.inflate(R.layout.layout_post, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(postView);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtViewPostOwnerUserName.setText(postLists.get(position).getUserName());
        holder.txtViewPostTitle.setText(postLists.get(position).getImgTitle());
        holder.txtViewPostDescription.setText(postLists.get(position).getImgDesc());

        byte[] encodeByte = Base64.decode(postLists.get(position).getImg(), Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        holder.imgViewPostPicture.setImageBitmap(bitmap2);
    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView imgViewPostOwnerPhoto, imgViewPostPicture;
        TextView txtViewPostTitle, txtViewPostOwnerUserName, txtViewPostDescription;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPostOwnerPhoto = itemView.findViewById(R.id.imgViewPostOwnerPhoto);
            imgViewPostPicture = itemView.findViewById(R.id.imgViewPostPicture);

            txtViewPostOwnerUserName = itemView.findViewById(R.id.txtViewPostOwnerUserName);
            txtViewPostTitle = itemView.findViewById(R.id.txtViewPostTitle);
            txtViewPostDescription = itemView.findViewById(R.id.txtViewPostDescription);

            imgViewPostPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Opening Post!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
