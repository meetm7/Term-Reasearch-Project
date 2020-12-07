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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voyavue.models.Post;

import java.util.List;

public class CustomPosterAdapter extends RecyclerView.Adapter<CustomPosterAdapter.CustomViewHolder> {

    List<Post> postLists;
    Context context;

    RecyclerViewClickListener mListner;

    public CustomPosterAdapter(List<Post> passedPostLists, RecyclerViewClickListener listner) {
        this.postLists = passedPostLists;
        this.mListner = listner;
    }

    public void ChangeData(List<Post> passedPostLists) {
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
        holder.txtViewNumberOfViews.setText("Views: " + postLists.get(position).getImgViews());
        holder.tvBestTime.setText(postLists.get(position).getBestTimeToVisit());
    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgViewPostPicture;
        TextView txtViewPostTitle, tvBestTime, txtViewPostOwnerUserName, txtViewPostDescription, txtViewNumberOfViews;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgViewPostPicture = itemView.findViewById(R.id.imgViewPostPicture);

            txtViewPostOwnerUserName = itemView.findViewById(R.id.txtViewPostOwnerUserName);
            txtViewPostTitle = itemView.findViewById(R.id.txtViewPostTitle);
            txtViewPostDescription = itemView.findViewById(R.id.txtViewPostDescription);
            txtViewNumberOfViews = itemView.findViewById(R.id.txtViewNumberOfViews);
            tvBestTime = itemView.findViewById(R.id.tvBestTime);

        }

        @Override
        public void onClick(View v) {
            mListner.onClick(v, postLists.get(getAdapterPosition()).get_id());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, String id);
    }
}
