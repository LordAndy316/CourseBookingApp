package com.example.android.coursebookingapp.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.databinding.IntroductionFragmentBinding;
import com.example.android.coursebookingapp.databinding.WelcomeFragmentBinding;

public class WelcomeFragment extends Fragment {


    public int ACTION_LOGIN = 1;
    public int ACTION_SIGNUP = 2;

    public static int  ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    private int CheckedButtonId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        WelcomeFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.welcome_fragment,
                container,
                false);



        return binding.getRoot();
    }

}
