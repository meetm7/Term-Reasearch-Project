package com.example.voyavue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.voyavue.models.Post;

import java.util.List;

public class CustomGrid extends BaseAdapter {

    List<Post> allPosts;

    public CustomGrid(List<Post> passedPostLists){
        this.allPosts = passedPostLists;
    }
    public void ChangeData(List<Post> passedPostLists){
        this.allPosts = passedPostLists;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return allPosts.size();
    }

    @Override
    public Object getItem(int i) {
        return allPosts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView;
        if (view == null) {  // if it's not recycled, initialize some attributes
            imageView = view.findViewById(R.id.imageViewPost);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }

        byte[] encodeByte = Base64.decode(allPosts.get(i).getImg(), Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        imageView.setImageBitmap(bitmap2);
        return imageView;

    }
}
