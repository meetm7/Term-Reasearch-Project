package com.example.voyavue.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voyavue.CustomPosterAdapter;
import com.example.voyavue.LoginActivity;
import com.example.voyavue.MainActivity;
import com.example.voyavue.R;
import com.example.voyavue.models.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private final Post post = new Post("Meet_m7",
            "0",
            "Image Title",
            1,
            "This is post description",
            "Tag1",
            "Delhi",
            "7PM",
            "700$",
            true,
            true);
    private final List<Post> allPosts = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getContext(), LoginActivity.class));
//            }
//        });
        allPosts.add(post);
        allPosts.add(post);
        allPosts.add(post);
        allPosts.add(post);
        allPosts.add(post);
        allPosts.add(post);
        final CustomPosterAdapter customPosterAdapter = new CustomPosterAdapter(allPosts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView = root.findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customPosterAdapter);
        return root;
    }
}