package com.example.android.coursebookingapp.screens;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavArgs;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.android.coursebookingapp.R;
import com.example.android.coursebookingapp.database.Admin;
import com.example.android.coursebookingapp.database.AdminDAO;
import com.example.android.coursebookingapp.database.CourseBookingDataBase;
import com.example.android.coursebookingapp.database.CourseDAO;
import com.example.android.coursebookingapp.database.Instructor;
import com.example.android.coursebookingapp.database.InstructorDAO;
import com.example.android.coursebookingapp.database.Student;
import com.example.android.coursebookingapp.database.StudentDAO;
import com.example.android.coursebookingapp.databinding.LoginSignupFragmentBinding;
import com.example.android.coursebookingapp.databinding.IntroductionFragmentBinding;

public class LoginSignupFragment extends Fragment {

    public int ACTION_LOGIN = 1;
    public int ACTION_SIGNUP = 2;

    public static int  ROLE_ADMIN = 3;
    public int ROLE_INSTRUCTOR = 4;
    public int ROLE_STUDENT = 5;

    public String DATA_BASE_NAME = "course_booking_database";

    private CourseBookingDataBase db;

    private AdminDAO adminDAO;
    private CourseDAO courseDAO;
    private InstructorDAO instructorDAO;
    private StudentDAO studentDAO;

    private String password;
    private String username;
    private String name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LoginSignupFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_signup_fragment,
                container,
                false);


        // Make an instance of the database
        // This can be done in the main thread
        db = Room.databaseBuilder(getContext(),
                CourseBookingDataBase.class, DATA_BASE_NAME).build();

        // Initialize all the DAO objects
        //
        adminDAO = db.adminDao();
        courseDAO = db.courseDao();
        instructorDAO = db.instructorDao();
        studentDAO = db.studentDao();

        // Get the role and the action that the
        // user want to perform
        int role = LoginSignupFragmentArgs.fromBundle(getArguments()).getRole();
        int action = LoginSignupFragmentArgs.fromBundle(getArguments()).getAction();

        //loginSignupFragmentArgs.getRole();
        //val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        if(action == ACTION_LOGIN) {
            binding.signupLoginButton.setText("Login");
            binding.loginSignupTitle.setText(matchRoleWithHeader(role)+" Login");

            // Create the admin account
            // incase it's not already there
            InsertionTask insertionTask = new InsertionTask();
            insertionTask.execute(ROLE_ADMIN);
            // I don't know what to do, fuck
        } else {
            // The confirmation button will be signup
            binding.signupLoginButton.setText("Signup");
            // Make the reenter password
            // and name field visible
            binding.reenterPasswordOutlinedTextField.setVisibility(View.VISIBLE);
            binding.nameOutlinedTextField.setVisibility(View.VISIBLE);

            // Set the text for the title
            binding.loginSignupTitle.setText(matchRoleWithHeader(role)+" Signup");
        }

        // Place a listener onto the login_signup button
        // We need to use the database here
        binding.signupLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action == ACTION_LOGIN){

                }else{

                    name = binding.editName.getText().toString();
                    username = binding.editUsername.getText().toString();
                    password = "";

                    if(name.isEmpty() || username.isEmpty() || binding.editPassword.getText().toString().isEmpty()
                    || binding.editPassword.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(),"Please make sure to complete all the fields",Toast.LENGTH_LONG).show();
                    }else {

                        if(binding.editPassword.getText().toString().equals(binding.editReenterPassword.getText().toString())) {
                            password = binding.editPassword.getText().toString();
                        }else {
                            Toast.makeText(getContext(),"The two passwords field should match",Toast.LENGTH_LONG).show();
                        }

                        InsertionTask insertionTask = new InsertionTask();
                        // Register a new element
                        if(role == ROLE_STUDENT){
                            insertionTask.execute(ROLE_STUDENT);
                        }else if(role == ROLE_INSTRUCTOR){
                            // Insert the new instructor
                            // into the database
                            insertionTask.execute(ROLE_INSTRUCTOR);
                        }
                        // Get the role

                        // register into the database with that
                        // role
                    }

                    //if(.isEmpty())
                    /**
                     && !binding.editPassword.getText().toString().isEmpty()
                     && !binding.editPassword.getText().toString().isEmpty()*/
                }

                //
            }
        });

        return binding.getRoot();//super.onCreateView(inflater, container, savedInstanceState);
    }

    public String matchRoleWithHeader(int role) {

        if(role == ROLE_ADMIN) {
            return "Admin";
        }else if(role == ROLE_STUDENT) {
            return "Student";
        }else{
            return "Instructor";
        }
    }

    private class testTask extends AsyncTask<Integer,Void,String>{
        @Override
        protected String doInBackground(Integer... integers) {
            if(adminDAO.getAll().isEmpty())
                return "Videu";
            return "Plein";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }

    private class InsertionTask extends AsyncTask<Integer,Void,Integer>{
        @Override
        protected Integer doInBackground(Integer... role) {
            if(role[0] == ROLE_INSTRUCTOR) {
                instructorDAO.insertOneInstructor(new Instructor(username,password,name));
                return ROLE_INSTRUCTOR;
            }else if(role[0] == ROLE_STUDENT){
                studentDAO.insertOneStudent(new Student(username,password,name));
                return ROLE_STUDENT;
            }else if(role[0] == ROLE_ADMIN){
                if(adminDAO.getAll().isEmpty()){
                    adminDAO.insertOneAdmin(new Admin("admin","admin123","Admin"));
                }
            }

            return -1;
        }

        @Override
        protected void onPostExecute(Integer role) {
            // Send the role
            // and name

            // Find the nave and bring the role
            // and the name
            NavDirections direction = LoginSignupFragmentDirections.actionLoginSignupFragmentDestinationToWelcomeFragment()
                    .setName(name)
                    .setRole(role);
            Navigation.findNavController().navigate(direction);

            //
            super.onPostExecute(role);
        }
    }
    // Circle the field in red
}
