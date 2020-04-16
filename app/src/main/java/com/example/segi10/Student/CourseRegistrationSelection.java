package com.example.segi10.Student;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Adapter.CourseSelectorAdapter;
import com.example.segi10.Adapter.course_reg_adapter;
import com.example.segi10.CourseRegistrationSummary;
import com.example.segi10.Model.Course;
import com.example.segi10.Model.OfferedCourse;
import com.example.segi10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationSelection extends AppCompatActivity {

    DatabaseReference dbref;

    String selectedCourses = "";
    String strUserId, strUsername, strFaculty,strEmail="";
    private RecyclerView recyclerView;
    private CourseSelectorAdapter mAdapter;
    List<OfferedCourse> offeredCourseList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_registration_selection);

            setToolbar("Course Selection");
            populateRecycler();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            selectedCourses = intent.getStringExtra("isChecked");
            //selectedCourses += isChecked;
            Toast.makeText(getApplicationContext(), selectedCourses, Toast.LENGTH_SHORT).show();
        }
    };


    public void populateRecycler(){
        dbref = FirebaseDatabase.getInstance().getReference().child("Portals").child("PTL32020")
                .child("Offered Courses");
        offeredCourseList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CourseSelectorAdapter(offeredCourseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offeredCourseList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    OfferedCourse offeredCourse = ds.getValue(OfferedCourse.class);
                    offeredCourseList.add(offeredCourse);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAdapter.setOnItemClickListener(new CourseSelectorAdapter.OnItemClickListener() {
            @Override
            public void onViewClicked(int position) {

            }

            @Override
            public void oncheck(int position) {

            }

        });

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }


    public void onClickProceed(View v){
        try {
            Toast.makeText(getApplicationContext(), selectedCourses, Toast.LENGTH_SHORT).show();

            Intent RegistrationSummary = new Intent(getApplicationContext(), CourseRegistrationSummary.class);
            RegistrationSummary.putExtra("selectedCourses",selectedCourses);
            RegistrationSummary.putExtra("prevActivity","courseRegistration");
            startActivity(RegistrationSummary);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setToolbar(String title) {
        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CourseRegistrationStudentData.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                /*Toast.makeText(getApplicationContext(), lblCourseID.getText().toString(),Toast.LENGTH_SHORT).show();

                if(lblCourseID.getText().toString().equals("")){
                    finish();
                    //startActivity(new Intent(getApplicationContext(), AdminPortalInfo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                if(!lblCourseID.getText().toString().equals("")){
                    setAllToNull();
                    selectCourseLayout.setVisibility(View.VISIBLE);
                    registrationInfo.setVisibility(View.GONE);
                }*/
            }
        });

    }
}


