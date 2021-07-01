package com.example.android.coursebookingapp.screens.adminFragments;

import android.os.AsyncTask;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.example.android.coursebookingapp.AppUtils;
import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.database.Course;
import com.example.android.coursebookingapp.database.CourseBookingDataBase;
import com.example.android.coursebookingapp.database.CourseDAO;
import com.example.android.coursebookingapp.databinding.CourseDetailFragmentBinding;

public class CourseDetailFragment extends Fragment {

    private String courseFullName_;
    private String courseCode_;
    private String courseName_;

    private CourseDAO courseDAO;
    private CourseBookingDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CourseDetailFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.course_detail_fragment,
                container,
                false);

        courseFullName_ = CourseDetailFragmentArgs.fromBundle(getArguments()).getCourseFullName();

        db = Room.databaseBuilder(getContext(),
                CourseBookingDataBase.class, AppUtils.DATA_BASE_NAME).build();

        courseDAO = db.courseDao();

        if(courseFullName_ !=null){
            int nameSeparatorIndex = -1;
            nameSeparatorIndex = courseFullName_.indexOf("|");

            // Extract the course name and code from the
            // full name passed as an argument
            courseName_ = courseFullName_.substring(0,nameSeparatorIndex).trim();
            courseCode_ = courseFullName_.substring(nameSeparatorIndex+1, courseFullName_.length()).trim();

            // Place the name and code onto
            // the corresponding edit text
            binding.editCourseName.setText(courseName_);
            binding.editCourseCode.setText(courseCode_);
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrDelete(binding.editCourseName.getText().toString(),
                        binding.editCourseCode.getText().toString(),AppUtils.ACTION_SAVE);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrDelete(binding.editCourseName.getText().toString(),
                        binding.editCourseCode.getText().toString(),AppUtils.ACTION_DELETE);
            }
        });

        getActivity().setTitle("Course detail");
        return binding.getRoot();
    }

    private void saveOrDelete(String cName,String cCode,int action) {

        UpDateCourseTask upDateCourseTask = new UpDateCourseTask();
        //
        if(cCode.isEmpty() || cName.isEmpty()) {
            Toast.makeText(getContext(),"Make sure no field is empty",Toast.LENGTH_LONG).show();
        }else {
            courseName_ = cName.trim();
            courseCode_ = cCode.trim();

            upDateCourseTask.execute(action);
        }
    }
    private class UpDateCourseTask extends AsyncTask<Integer,Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... action) {

            if(action[0] == AppUtils.ACTION_SAVE) {
                courseDAO.insertOneCourse(new Course(courseName_, courseCode_));
                return true;
            }else if (action[0] == AppUtils.ACTION_DELETE){
                int nbDel = courseDAO.delete(courseName_, courseCode_);

                if( nbDel> 0){
                    return true;
                }else{
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if(status){
                Toast.makeText(getContext(),"The Operation Succeed",Toast.LENGTH_LONG).show();
                NavDirections direction = CourseDetailFragmentDirections.actionCourseDetailFragmentToCourseListFragment();

                NavHostFragment.findNavController(getParentFragment()).navigate(direction);
            }else{
                Toast.makeText(getContext(),"Operation failed",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(status);
        }
    }
}
