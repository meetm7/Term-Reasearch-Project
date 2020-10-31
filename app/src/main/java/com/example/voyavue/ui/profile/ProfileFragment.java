package com.example.voyavue.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.voyavue.R;
import com.example.voyavue.models.User;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileFragViewModel profileFragViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileFragViewModel =
                ViewModelProviders.of(this).get(ProfileFragViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView tv = root.findViewById(R.id.tv_bio);
        final Button btn = root.findViewById(R.id.btn_retrive);

        profileFragViewModel.init();

        profileFragViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                tv.setText(users.toString());
            }
        });

        return root;
    }
}