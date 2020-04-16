package com.example.segi10.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.segi10.R;
import com.google.firebase.auth.FirebaseUser;


public class AdminHomeFragment extends Fragment {

    FirebaseUser firebaseUser;
    Button btnManageCourses,btnOpenRegistration,btnOnClickEditUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the portal_dialog for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_home, container, false);

        btnManageCourses = (Button) view.findViewById(R.id.btnManageCourses);
        btnOpenRegistration = (Button) view.findViewById(R.id.btnOpenRegistration);
        btnOnClickEditUser = (Button) view.findViewById(R.id.btnOnClickEditUser);

        btnManageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ManageCourse = new Intent(getActivity(), AdminAddCourse.class);
                startActivity(ManageCourse);
            }
        });


        btnOnClickEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditUsers = new Intent(getActivity(), EditUsers.class);
                startActivity(EditUsers);
            }
        });

        btnOpenRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenRegistration = new Intent(getActivity(), AdminPortal.class);
                startActivity(OpenRegistration);
            }
        });


        return view;
    }

}
