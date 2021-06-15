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
import com.example.android.coursebookingapp.databinding.LoginSignupFragmentBinding;

public class CourseListFragment extends Fragment {


    public int ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LoginSignupFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.course_list_fragment,
                container,
                false);

        binding.getRoot();
    }


}
