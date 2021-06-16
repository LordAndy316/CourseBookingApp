package com.example.android.coursebookingapp.screens.adminFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.database.Admin;
import com.example.android.coursebookingapp.database.AdminDAO;
import com.example.android.coursebookingapp.database.Course;
import com.example.android.coursebookingapp.database.CourseBookingDataBase;
import com.example.android.coursebookingapp.database.CourseDAO;
import com.example.android.coursebookingapp.database.Instructor;
import com.example.android.coursebookingapp.database.InstructorDAO;
import com.example.android.coursebookingapp.database.Student;
import com.example.android.coursebookingapp.database.StudentDAO;
import com.example.android.coursebookingapp.databinding.CourseListFragmentBinding;
import com.example.android.coursebookingapp.screens.mainFragments.LoginSignupFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {

    public int ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    // The following constants represent
    //
    public int ADD_COURSE = 6;
    public int REMOVE_COURSE = 7;
    public int GET_ALL_COURSES = 8;

    // Create the daos
    private AdminDAO adminDAO;
    private CourseDAO courseDAO;
    private InstructorDAO instructorDAO;
    private StudentDAO studentDAO;

    public String DATA_BASE_NAME = "course_booking_database";

    //
    private String courseName_;
    private String courseCode_;

    private ArrayList<String> courseArrList_;
    private CourseBookingDataBase db;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        CourseListFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.course_list_fragment,
                container,
                false);

        //
        db = Room.databaseBuilder(getContext(),
                CourseBookingDataBase.class, DATA_BASE_NAME).build();

        courseDAO = db.courseDao();

        // Create the adapter to hold the list of courses
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, new ArrayList<String>());

        // Start a background thread to get all the courses
        // from the database
        CourseOperations courseOperations = new CourseOperations();
        courseOperations.execute();

        //
        binding.listView.setAdapter(adapter);

        // In case there is no course,
        // display the empty state
        if(adapter.getCount() > 0){
           binding.emptyGroupView.setVisibility(View.VISIBLE);
        }else{
            binding.emptyGroupView.setVisibility(View.GONE);
        }

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String courseNameAndCode = adapter.getItem(position);


                NavDirections direction = CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment()
                        .setCourseFullName(courseNameAndCode);

                NavHostFragment.findNavController(getParentFragment()).navigate(direction);
                // Separate the text
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections direction = CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment();

                NavHostFragment.findNavController(getParentFragment()).navigate(direction);
            }
        });
        // Put together the adapter and the listView

        return binding.getRoot();
    }

    private class CourseOperations extends AsyncTask<Integer,Void,List<String>> {
        @Override
        protected List<String> doInBackground(Integer... operation) {

            List<Course> allCourse = courseDAO.getAll();
            List<String> courseStringList = new ArrayList<String>();

            if(!allCourse.isEmpty()){
                for(int i=0; i<allCourse.size();i++){
                    courseStringList.add(allCourse.get(i).courseName + " | "+allCourse.get(i).courseCode);
                }
                return courseStringList;
            };
            return null;
        }

        @Override
        protected void onPostExecute(List<String> courseList) {

            if(courseList != null) {
                courseArrList_ = (ArrayList<String>) courseList;
                adapter.addAll(courseList);
                synchronized(adapter){
                    adapter.notifyAll();
                }

                //courseArrList_ = (ArrayList<String>) courseList;
            }
            // Add it to the listView here
            //
            super.onPostExecute(courseList);
        }
    }
}
