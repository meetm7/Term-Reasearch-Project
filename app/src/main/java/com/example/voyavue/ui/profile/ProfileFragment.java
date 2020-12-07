package com.example.voyavue.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.voyavue.R;
import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.AdditionProfileInfo;
import com.example.voyavue.models.User;
import com.example.voyavue.repositories.UserRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ProfileFragViewModel profileFragViewModel;

    private Spinner spinnerGender;
    private static final String[] genders = {"Male", "Female"};

    private EditText etUserName, etUserEmail, etFName, etLName, etContactNumber, etDob, etBio, etPosts, etFriends;

    private Button btnUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileFragViewModel =
                ViewModelProviders.of(this).get(ProfileFragViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileFragViewModel.init();

        init(root);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_spinner_item, genders);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        profileFragViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                etUserName.setText(user.getUserName());
                etUserEmail.setText(user.getEmail());
                etContactNumber.setText(user.getEmail());
                etFName.setText(user.getFirstName());
                etLName.setText(user.getLastName());
                etContactNumber.setText(user.getContactNumber());

                if (user.getSex().compareTo("Male") == 0) {
                    spinnerGender.setSelection(0);
                } else {
                    spinnerGender.setSelection(1);
                }

                etDob.setText(user.getDob().split("T")[0]);
                etBio.setText(user.getBio());
            }
        });

        profileFragViewModel.getAdditionalInfo().observe(getViewLifecycleOwner(), new Observer<AdditionProfileInfo>() {
            @Override
            public void onChanged(AdditionProfileInfo info) {
                etPosts.setText(info.getNumberOfPosts());
                etFriends.setText(info.getNumberOfFriends());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gender = spinnerGender.getSelectedItem().toString();
                User newInfo = new User(etFName.getText().toString(),
                        etLName.getText().toString(),
                        etUserName.getText().toString(),
                        etUserEmail.getText().toString(),
                        etContactNumber.getText().toString(),
                        etDob.getText().toString(),
                        gender,
                        etBio.getText().toString(),
                        false
                );

                updateUserInfo(newInfo);
            }
        });

        return root;
    }

    public void updateUserInfo(User userInfo) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<User> call = apiCall.updateUserInfo(userInfo);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    UserRepo uRepo = UserRepo.getInstance();
                    uRepo.setUser(response.body());

                    Toast.makeText(getContext(), "User Info Updated", Toast.LENGTH_SHORT).show();
                    Log.d("Response", "onResponse: " + response.body().toString());
                } else {
                    Log.d("ProfileFrag:", "Cannot get user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("ProfileFrag:", "Cannot get data" + t.getMessage());
            }
        });
    }

    private void init(View r) {
        etUserName = r.findViewById(R.id.etUName);
        etUserEmail = r.findViewById(R.id.etEmailId);
        etFName = r.findViewById(R.id.etFName);
        etLName = r.findViewById(R.id.etLName);
        etContactNumber = r.findViewById(R.id.etContactNumber);
        etDob = r.findViewById(R.id.etDOB);
        etBio = r.findViewById(R.id.etBio);
        etPosts = r.findViewById(R.id.etPosts);
        etFriends = r.findViewById(R.id.etFriends);

        spinnerGender = r.findViewById(R.id.spinnerGender);
        btnUpdate = r.findViewById(R.id.btnUpdate);
    }
}