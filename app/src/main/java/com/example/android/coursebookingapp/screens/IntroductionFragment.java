package com.example.android.coursebookingapp.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.databinding.IntroductionFragmentBinding;
import com.example.android.coursebookingapp.screens.IntroductionFragmentDirections;
import com.google.android.material.button.MaterialButtonToggleGroup;


public class IntroductionFragment extends Fragment {

    public int ACTION_LOGIN = 1;
    public int ACTION_SIGNUP = 2;

    public static int  ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    private int CheckedButtonId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        IntroductionFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.introduction_fragment,
                container,
                false);

        CheckedButtonId = -1;

        binding.roleToggleButtons.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                CheckedButtonId = checkedId;
                if(CheckedButtonId== R.id.admin_button){
                    binding.signupButton.setEnabled(false);
                }else {
                    binding.signupButton.setEnabled(true);
                }
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(CheckedButtonId == R.id.student_button) {
                        openLoginSignupFragment(ACTION_SIGNUP,ROLE_STUDENT,v);
                    }else if(CheckedButtonId == R.id.teacher_button) {
                        openLoginSignupFragment(ACTION_SIGNUP,ROLE_INSTRUCTOR,v);
                    }else{
                        Toast.makeText(getContext(),"You need to select a role",Toast.LENGTH_LONG).show();
                    }
                }
                /*else{
                }*/

        });

        //
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckedButtonId == R.id.student_button) {
                    openLoginSignupFragment(ACTION_LOGIN,ROLE_STUDENT,v);
                }else if(CheckedButtonId == R.id.teacher_button) {
                    openLoginSignupFragment(ACTION_LOGIN,ROLE_INSTRUCTOR,v);
                } else if(CheckedButtonId == R.id.admin_button){
                    openLoginSignupFragment(ACTION_LOGIN,ROLE_ADMIN,v);
                }else{
                    Toast.makeText(getContext(),"You need to select a role",Toast.LENGTH_LONG);
                }
            }
        });

        return binding.getRoot();
        //super.onCreateView(inflater, container, savedInstanceState);
    }

    public void openLoginSignupFragment(int action, int role, View v){
        NavDirections direction = IntroductionFragmentDirections
                .actionIntroductionFragmentToLoginSignupFragment()
                .setAction(action)
                .setRole(role);
        Navigation.findNavController(v).navigate(direction);
    }
}
