package com.example.segi10.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.segi10.Adapter.course_reg_adapter;
import com.example.segi10.Model.Course;
import com.example.segi10.Model.OfferedCourse;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminCourse extends AppCompatActivity {

    String portalId,selectedCourses,amPm= "";
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour, currentMinute;

    LinearLayout selectCourseLayout, registrationInfo,slot1Layout,slot2Layout;
    TextView lblCourseID,lblCourseName,lblCreditHours,lblSpecialty;
    EditText txtLecName,txtS1StartTime,txtS1EndTime,txtS2StartTime,txtS2EndTime, txtS1Class,txtS2Class;
    Button btn_add;
    Spinner s1SpinnerDay, s2SpinnerDay;

    List<Course> courseList;
    Toolbar toolbar;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private course_reg_adapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course);
        portalId = getIntent().getStringExtra("portalID");

        findViews();

        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Offer Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), lblCourseID.getText().toString(),Toast.LENGTH_SHORT).show();

                if(lblCourseID.getText().toString().equals("")){
                    finish();
                    //startActivity(new Intent(getApplicationContext(), AdminPortalInfo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                if(!lblCourseID.getText().toString().equals("")){
                    setAllToNull();
                    selectCourseLayout.setVisibility(View.VISIBLE);
                    registrationInfo.setVisibility(View.GONE);
                }
            }
        });

        populateRecyclerView();
    }

    public void onClickProceed(View view) {

    }

    public  void populateRecyclerView(){
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


        mAdapter.setOnItemClickListener(new course_reg_adapter.OnItemClickListener() {
            @Override
            public void onViewClicked(int position) {
                String courseId= courseList.get(position).getCode();
                slot2Layout.setVisibility(View.GONE);

                lblCourseID.setText(courseId);
                lblCourseName.setText(courseList.get(position).getName());
                lblCreditHours.setText(courseList.get(position).getCreditHours());
                lblSpecialty.setText(courseList.get(position).getSpecialty());

                selectCourseLayout.setVisibility(View.GONE);
                registrationInfo.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(),courseList.get(position).getCreditHours().toString(),Toast.LENGTH_SHORT).show();

                if (courseList.get(position).getCreditHours().equals("4.0")){
                    slot2Layout.setVisibility(View.VISIBLE);
                }


            }

        });

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

    }

    public void findViews(){

        selectCourseLayout = findViewById(R.id.selectCourseLayout);
        registrationInfo = findViewById(R.id.RegistrationInfo);
        slot1Layout = findViewById(R.id.lay_slot1);
        slot2Layout = findViewById(R.id.lay_slot2);

        lblCourseID    = findViewById(R.id.lblCourseID);
        lblCourseName  = findViewById(R.id.lblCourseName);
        lblCreditHours = findViewById(R.id.lblCreditHours);
        lblSpecialty   = findViewById(R.id.lblSpecialty);

        txtLecName    = findViewById(R.id.txtLecName);
        txtS1Class = findViewById(R.id.txtS1Class);
        txtS2Class = findViewById(R.id.txtS2Class);

        txtS1StartTime = findViewById(R.id.txtS1StartTime);
        txtS1EndTime   = findViewById(R.id.txtS1EndTime);
        txtS2StartTime = findViewById(R.id.txtS2StartTime);
        txtS2EndTime   = findViewById(R.id.txtS2EndTime);

        s1SpinnerDay = findViewById(R.id.spinnerDayS1);
        s2SpinnerDay = findViewById(R.id.spinnerDayS2);

    }

    public void txtS1StartTime(View view) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        hidekeyboard();

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String strMinutes;
                        if(minute < 10)
                            strMinutes = "0" + minute;
                        else
                            strMinutes = "" + minute;

                        int hourOfDayPlus ;
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            amPm = "AM";
                        } else if (hourOfDay == 12) {
                            amPm = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        txtS1StartTime.setText(hourOfDay + ":" +strMinutes + " " + amPm);

                        int noOfHours;

                        if (lblCreditHours.getText().equals("4.0"))
                            noOfHours = 2;
                        else
                            noOfHours = 3;

                        hourOfDayPlus = hourOfDay + noOfHours;
                        String amPm2="";

                        if (hourOfDayPlus == 0) {
                            hourOfDayPlus += 12;
                            amPm2 = "AM";
                        } else if (hourOfDayPlus == 12) {
                            amPm2 = "PM";
                        } else if (hourOfDayPlus > 12) {
                            hourOfDayPlus -= 12;
                            amPm2 = "PM";
                        } else {
                            amPm2 = "AM";
                        }
                        txtS1EndTime.setText(hourOfDayPlus + ":" +strMinutes + " " + amPm2);
                    }
                }, currentHour, currentMinute, false);
        timePickerDialog.show();
    }

    private void hidekeyboard() {
        try
        {
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            View viewKey=this.getCurrentFocus();
            if(viewKey !=null)
            {
                InputMethodManager inputMana=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMana.hideSoftInputFromWindow(viewKey.getWindowToken(),0);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void txtS2StartTime(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        hidekeyboard();

        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String strMinutes;
                        if(minute < 10)
                            strMinutes = "0" + minute;
                        else
                            strMinutes = "" + minute;

                        int hourOfDayPlus ;
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            amPm = "AM";
                        } else if (hourOfDay == 12) {
                            amPm = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        txtS2StartTime.setText(hourOfDay + ":" +strMinutes + " " + amPm);

                        int noOfHours;

                        hourOfDayPlus = hourOfDay + 2;
                        String amPm2="";

                        if (hourOfDayPlus == 0) {
                            hourOfDayPlus += 12;
                            amPm2 = "AM";
                        } else if (hourOfDayPlus == 12) {
                            amPm2 = "PM";
                        } else if (hourOfDayPlus > 12) {
                            hourOfDayPlus -= 12;
                            amPm2 = "PM";
                        } else {
                            amPm2 = "AM";
                        }
                        txtS2EndTime.setText(hourOfDayPlus + ":" +strMinutes + " " + amPm2);
                    }
                }, currentHour, currentMinute, false);
        timePickerDialog.show();
    }

    public void btn_add(View view) {

        int s1SpinnerLoc = s1SpinnerDay.getSelectedItemPosition();
        int s2SpinnerLoc = s2SpinnerDay.getSelectedItemPosition();

        String slot1 = null;


        if (txtLecName.length() == 0 || txtS1StartTime.length() == 0 || txtS1EndTime.length() == 0 || s1SpinnerLoc == 0){

            if(s1SpinnerLoc == 0){
                TextView errorText = (TextView)s1SpinnerDay.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("");//changes the selected item text to th
            }

            if (txtLecName.length() == 0) {
                txtLecName.setError("Enter Name");
            }

            if (txtS1Class.length() == 0) {
                txtS1Class.setError("Enter Class");
            }

            if (txtS1StartTime.length() == 0) {
                txtS1StartTime.setError("Enter time");
            }
        }else {

            slot1 = s1SpinnerDay.getSelectedItem() +
                    " [" + txtS1StartTime.getText().toString() +
                    " - " + txtS1EndTime.getText().toString() + "]" +
                    " < " + txtS1Class.getText().toString() + ">";

        }


        if (slot2Layout.getVisibility() == View.GONE){

            String courseId = lblCourseID .getText().toString();
            reference = FirebaseDatabase.getInstance().getReference("Portals").child(portalId)
                    .child("Offered Courses").child(courseId);

            OfferedCourse offeredCourse = new OfferedCourse(courseId, txtLecName.getText().toString(),slot1);
            reference.setValue(offeredCourse);
            Toast.makeText(this, slot1 + "", Toast.LENGTH_SHORT).show();

            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Successfully Added!");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("Do you want to Add another Course?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setAllToNull();
                            selectCourseLayout.setVisibility(View.VISIBLE);
                            registrationInfo.setVisibility(View.GONE);
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

            alertDialog.show();

        } else if (slot2Layout.getVisibility() == View.VISIBLE && (txtS2StartTime.length() == 0 || txtS2EndTime.length() == 0 || s1SpinnerLoc == 0)) {

            if(s2SpinnerLoc == 0){
                TextView errorText = (TextView)s1SpinnerDay.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("");//changes the selected item text to th
            }

            if (txtS2Class.length() == 0) {
                txtS2Class.setError("Enter Class");
            }

            if (txtS2StartTime.length() == 0) {
                txtS2StartTime.setError("Enter time");
            }
        }else {
            String slot2 = s2SpinnerDay.getSelectedItem() +
                    " [" + txtS2StartTime.getText().toString() +
                    " - " + txtS2EndTime.getText().toString() + "]" +
                    " < " + txtS2Class.getText().toString() + ">";

            String courseId = lblCourseID .getText().toString();
            reference = FirebaseDatabase.getInstance().getReference("Portals").child(portalId)
                    .child("Offered Courses").child(courseId);

            OfferedCourse offeredCourse = new OfferedCourse(courseId, lblCourseName.getText().toString() ,txtLecName.getText().toString(),slot1,slot2);
            reference.setValue(offeredCourse);

            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Successfully Added!");
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("Do you want to Add another Course?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setAllToNull();
                            selectCourseLayout.setVisibility(View.VISIBLE);
                            registrationInfo.setVisibility(View.GONE);
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

            alertDialog.show();


        }

    }

    public void setAllToNull (){

        try {
            lblCourseID   .setText("");
            txtLecName    .setText("");
            txtS1StartTime.setText("");
            txtS1EndTime  .setText("");
            txtS1Class    .setText("");
            txtS2StartTime.setText("");
            txtS2EndTime  .setText("");
            txtS2Class    .setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

}
