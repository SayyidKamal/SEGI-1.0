package com.example.segi10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Adapter.selected_courses_adapter;
import com.example.segi10.Course.CourseSelector;
import com.example.segi10.Model.Course;
import com.example.segi10.Model.Registration;
import com.example.segi10.Model.User;
import com.example.segi10.Student.CourseRegistrationSelection;
import com.example.segi10.Student.CourseRegistrationStudentData;
import com.example.segi10.Student.StudentMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseRegistrationSummary extends AppCompatActivity {

    DatabaseReference dbref;

    String strUserId, strUsername, strFaculty, strEmail, prevActivity, selectedCourses="";
    TextView lblName, lblId, lblPassportNo,lblEmail, lblfaculty,lblPhoneNo, lblAddress, lblTotalCredits;
    Button btnEdit, btnProceed;
    double totalCredits;
    long maxid = 0;

    private RecyclerView recyclerView;
    private selected_courses_adapter mAdapter;
    List<Course> courseList;

    Toolbar toolbar;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_registration_summary);

            prevActivity = getIntent().getStringExtra("prevActivity");

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            findView();
            setToolbar("Course Registration Summary");
            getUserData();

            if(prevActivity.equals("Main-menu")) {
                btnProceed.setVisibility(View.GONE);
                getSelectedCourses();
            }else{
                selectedCourses = getIntent().getStringExtra("selectedCourses");
                insertCoursesData(selectedCourses);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findView(){
        lblName = (TextView) findViewById(R.id.lblName);
        lblId = (TextView) findViewById(R.id.lblStudentId);
        lblPassportNo = (TextView) findViewById(R.id.lblPassportNo);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblTotalCredits = (TextView) findViewById(R.id.lblTotalCredits);
        btnProceed = (Button) findViewById(R.id.btnProceed);
    }

    private void getUserData(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                lblName.setText(user.getName());
                lblPassportNo.setText(user.getIc_Passport());
                lblId.setText(user.getSegiID());
                lblEmail.setText(user.getEmail());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getSelectedCourses(){
        try {

            dbref = FirebaseDatabase.getInstance().getReference().child("Portals").child("PTL32020")
                    .child("Registrations");
            dbref.orderByChild("studID").equalTo(strUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {

                        if(dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Registration reg = snapshot.getValue(Registration.class);
                                Toast.makeText(getApplicationContext(), "OUR INFO : " + reg.getCourseCode(), Toast.LENGTH_LONG).show();
                                selectedCourses = reg.getCourseCode();
                                insertCoursesData(selectedCourses);
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void insertCoursesData(String selectedCourses){
        try {

            courseList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mAdapter = new selected_courses_adapter(courseList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);


            dbref = FirebaseDatabase.getInstance().getReference().child("Courses");
            //dbref.addValueEventListener(valueEventListener);

            courseList.clear();
            final List<String> myList = new ArrayList<String>(Arrays.asList(selectedCourses.split(",")));
            for(int i = 0; i < myList.size(); i++){
                Query query = FirebaseDatabase.getInstance().getReference()
                        .child("Courses")
                        .orderByChild("code")
                        .equalTo(myList.get(i));
                query.addListenerForSingleValueEvent(valueEventListener);

            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickProceed(View view){

        dbref = FirebaseDatabase.getInstance().getReference().child("Portals").child("PTL32020")
                .child("Registrations");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Date t = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        String regID, studID, regDate, courseCodes;
        maxid=maxid + 1;
        if (maxid < 10) {
            regID = "REG00" + maxid ;
        }else if (maxid < 100){
            regID = "REG0" + maxid ;
        }else {
            regID = "REG" + maxid ;
        }


        studID= strUserId;
        regDate = df.format(t);
        courseCodes= selectedCourses;


        Registration registration = new Registration(regID, studID, regDate, courseCodes);
        dbref.child(regID).setValue(registration);

        Intent actStudentMenu = new Intent(getApplicationContext(), StudentMainActivity.class);
        startActivity(actStudentMenu);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
                //courseList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Course courseTemp = ds.getValue(Course.class);
                    totalCredits += Double.parseDouble(courseTemp.getCreditHours());
                    courseList.add(courseTemp);

                }
                mAdapter.notifyDataSetChanged();
                lblTotalCredits.setText(Double.toString(totalCredits));
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void setToolbar(String title) {
        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CourseRegistrationSelection.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

    }

}
