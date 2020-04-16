package com.example.segi10.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segi10.Adapter.course_reg_adapter;
import com.example.segi10.Model.Course;
import com.example.segi10.R;
import com.example.segi10.Student.StudentMainActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseSelector extends AppCompatActivity {

    String selectedCourses = "";

    private RecyclerView recyclerView;
    private course_reg_adapter mAdapter;
    List<Course> courseList;

    Toolbar toolbar;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selector);

        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), StudentMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

       // populateRecyclerView();
    }

    public void onClickProceed(View view) {

    }

   /* public  void populateRecyclerView(){
        reference = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new course_reg_adapter(courseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Course courseTemp = ds.getValue(Course.class);
                    courseList.add(courseTemp);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       // mAdapter.setOnItemClickListener(new course_reg_adapter.OnItemClickListener() {
            @Override
            public void onViewClicked(int position) {

            }

            @Override
            public void oncheck(int position) {
                //Toast.makeText(getApplicationContext(),)
            }
        });

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

    }*/

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            selectedCourses = intent.getStringExtra("isChecked");
            //selectedCourses += isChecked;
            Toast.makeText(getApplicationContext(), selectedCourses,Toast.LENGTH_SHORT).show();
        }
    };
}
