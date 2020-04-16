package com.example.segi10.Student;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.segi10.CourseRegistrationSummary;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class StudHomeFragment extends Fragment {

    FirebaseUser firebaseUser;
    LinearLayout btn_register_course,btn_view_registration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the portal_dialog for this fragment
        View view =  inflater.inflate(R.layout.fragment_stud_home, container, false);
        btn_register_course = (LinearLayout) view.findViewById(R.id.btn_register_course);
        btn_view_registration = (LinearLayout) view.findViewById(R.id.btn_view_registration);

        btn_register_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Registration = new Intent(getActivity(), CourseRegistrationStudentData.class);
                /*Registration.putExtra("id", strUserId);
                Registration.putExtra("name", strUsername);
                Registration.putExtra("faculty", strFaculty);
                Registration.putExtra("email", strEmail);*/
                //Registration.putExtra("selectedCourses", selectedCourses);
                //Registration.putExtra("prevActivity", "Main-menu");
                startActivity(Registration);

            }
        });

        btn_view_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegistrationSummary = new Intent(getActivity(), CourseRegistrationSummary.class);
                //RegistrationSummary.putExtra("selectedCourses", selectedCourses);
                RegistrationSummary.putExtra("prevActivity", "Student Main");
                startActivity(RegistrationSummary);
            }
        });


        return view;
    }

}
