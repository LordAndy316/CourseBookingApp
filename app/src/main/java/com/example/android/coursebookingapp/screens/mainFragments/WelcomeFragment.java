package com.example.android.coursebookingapp.screens.mainFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.android.coursebookingapp.AdminActivity;
import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.databinding.IntroductionFragmentBinding;
import com.example.android.coursebookingapp.databinding.WelcomeFragmentBinding;
import com.example.android.coursebookingapp.screens.WelcomeFragmentArgs;

public class WelcomeFragment extends Fragment {


    public int ACTION_LOGIN = 1;
    public int ACTION_SIGNUP = 2;

    public int   ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    private int CheckedButtonId;

    private String name;
    private int role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        WelcomeFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.welcome_fragment,
                container,
                false);

        role = WelcomeFragmentArgs.fromBundle(getArguments()).getRole();
        name = WelcomeFragmentArgs.fromBundle(getArguments()).getName();


        binding.welcomeText.setText("Welcome "+name);
        if(!getRole(role).isEmpty()) {
            binding.roleText.setText("You logged in as a "+getRole(role));
        }

        if(role == ROLE_ADMIN) {
            binding.action1Button.setText("Course List");
        } else{
            binding.action1Button.setText("Get Started");
        }

        binding.action1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(role == ROLE_ADMIN) {
                    intent = new Intent(getContext(), AdminActivity.class);
                    startActivity(intent);
                }
            }
        });

        return binding.getRoot();
    }

    private String getRole(int roleCode) {
        if(roleCode == ROLE_ADMIN)  {
            return "Admin";
        }else if(roleCode == ROLE_INSTRUCTOR) {
            return "Instructor";
        }else if (roleCode == ROLE_STUDENT) {
            return "Student";
        }
        return "";
    }
}
