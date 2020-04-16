package com.example.segi10.Student;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.CourseRegistrationSummary;
import com.example.segi10.Fragments.ProfileFragment;
import com.example.segi10.Model.User;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseRegistrationStudentData extends AppCompatActivity {

    String selectedCourses = "";
    TextView lblName, lblId, lblIcPassportNo,lblEmail,lblPhoneNo, lblAddress, btnEditPersInfo;
    Button btnEdit, btnProceed;
    LinearLayout personalInformationLayout, selectCourseLayout;

    private RecyclerView recyclerView;


    Toolbar toolbar;


    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_registration_student_data);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            findViews();
            setToolbar("Student Data");
            getUserData();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void getUserData(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                lblName.setText(user.getName());
                lblIcPassportNo.setText(user.getIc_Passport());
                lblId.setText(user.getSegiID());
                lblPhoneNo.setText(user.getPhoneNo());
                lblAddress.setText(user.getAddress());
                lblEmail.setText(user.getEmail());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setToolbar(String title) {
        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

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

     public void findViews(){

         lblName = (TextView) findViewById(R.id.lblName);
         lblId = (TextView) findViewById(R.id.lblStudentId);
         lblIcPassportNo = (TextView) findViewById(R.id.lblIcPassportNo);
         lblEmail = (TextView) findViewById(R.id.lblEmail);
         lblPhoneNo = (TextView) findViewById(R.id.lblPhoneNo);
         lblAddress = (TextView) findViewById(R.id.lblAddress);

         btnProceed = (Button) findViewById(R.id.btnProceed);

         personalInformationLayout= findViewById(R.id.personalInformationLayout);
         selectCourseLayout= findViewById(R.id.selectCourseLayout);
     }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            selectedCourses = intent.getStringExtra("isChecked");
            //selectedCourses += isChecked;
            Toast.makeText(getApplicationContext(), selectedCourses,Toast.LENGTH_SHORT).show();
        }
    };

    public void toProfile(View view) {
        Intent Profile = new Intent(CourseRegistrationStudentData.this, ProfileFragment.class);
        startActivity(Profile);
    }

    public void toCourseSelector(View view) {
        try {

            startActivity(new Intent(CourseRegistrationStudentData.this,CourseRegistrationSelection.class));

            /*personalInformationLayout.setVisibility(View.GONE);
            selectCourseLayout.setVisibility(View.VISIBLE);

            recyclerView= findViewById(R.id.recyclerView);

            final CourseSelectorAdapter mAdapter;
            final List<OfferedCourse> offeredCourseList;

            reference = FirebaseDatabase.getInstance().getReference().child("Portals").child("PTL32020").child("courses");
            offeredCourseList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mAdapter = new CourseSelectorAdapter(offeredCourseList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            reference.addValueEventListener(new ValueEventListener() {
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


            mAdapter.setOnItemClickListener(new course_reg_adapter.OnItemClickListener() {
                @Override
                public void onViewClicked(int position) {

                }

                @Override
                public void oncheck(int position) {
                    Toast.makeText(getApplicationContext(),offeredCourseList.get(position)
                            .getCourseId(),Toast.LENGTH_SHORT);
                }
            });

            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                    new IntentFilter("custom-message"));

            *//*Intent actCourseRegistration = new Intent(CourseRegistrationStudentData.this,CourseRegistration.class);
            actCourseRegistration.putExtra("id",strUserId);
            actCourseRegistration.putExtra("name",strUsername);
            actCourseRegistration.putExtra("faculty",strFaculty);
            actCourseRegistration.putExtra("email",strEmail);
            startActivity(actCourseRegistration);*/

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void toCourseSummary(View view) {
        Toast.makeText(getApplicationContext(), selectedCourses,Toast.LENGTH_SHORT).show();

        Intent RegistrationSummary = new Intent(getApplicationContext(), CourseRegistrationSummary.class);
        RegistrationSummary.putExtra("selectedCourses",selectedCourses);
        RegistrationSummary.putExtra("prevActivity","courseRegistration");
        startActivity(RegistrationSummary);
    }
}
