package com.example.voyavue.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voyavue.CustomGrid;
import com.example.voyavue.CustomPosterAdapter;
import com.example.voyavue.NewPostActivity;
import com.example.voyavue.R;
import com.example.voyavue.models.Post;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private ExploreViewModel exploreViewModel;
    private RecyclerView recyclerView;
    private Spinner spinnerTagsFilter;
    private Spinner spinnerLocationFilter;
    private ToggleButton tglBtnVerifiedFilter;

    private CustomPosterAdapter.RecyclerViewClickListener mListner;

    private final List<Post> allPosts = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exploreViewModel =
                ViewModelProviders.of(this).get(ExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        setOnClickListner();

        recyclerView = root.findViewById(R.id.recyclerViewAllPosts);
        spinnerTagsFilter = root.findViewById(R.id.spinnerTagsFilter);
        spinnerLocationFilter = root.findViewById(R.id.spinnerLocationFilter);
        tglBtnVerifiedFilter = root.findViewById(R.id.tglBtnVerifiedFilter);

        final CustomPosterAdapter customPosterAdapter = new CustomPosterAdapter(allPosts, mListner);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customPosterAdapter);

        exploreViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                customPosterAdapter.ChangeData(posts);
            }
        });

        return root;
    }


    private void setOnClickListner() {
        mListner = new CustomPosterAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, String id) {
                Intent i = new Intent(v.getContext(), NewPostActivity.class);
                i.putExtra("postId", id);
                i.putExtra("isEditable", true);

                startActivity(i);
            }
        };
    }
}