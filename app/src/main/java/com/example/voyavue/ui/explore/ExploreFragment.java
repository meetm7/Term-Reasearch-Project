package com.example.voyavue.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        exploreViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> customPosterAdapter.ChangeData(posts));

        exploreViewModel.getFilteredPosts().observe(getViewLifecycleOwner(), posts -> customPosterAdapter.ChangeData(posts));

        spinnerTagsFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                exploreViewModel.filterByTags(spinnerTagsFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        spinnerLocationFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                exploreViewModel.filterByLocation(spinnerLocationFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        tglBtnVerifiedFilter.setOnClickListener(v -> {
            if (tglBtnVerifiedFilter.isChecked()) {
                tglBtnVerifiedFilter.setTextOff("Unverified Posts");
                exploreViewModel.fetchPost();
            } else {
                tglBtnVerifiedFilter.setTextOn("Verified Posts");
                exploreViewModel.filterVerifiedPosts();
            }
        });

        return root;
    }


    private void setOnClickListner() {
        mListner = (v, id) -> {
            Intent i = new Intent(v.getContext(), NewPostActivity.class);
            i.putExtra("postId", id);
            i.putExtra("isEditable", false);

            startActivity(i);
        };
    }
}