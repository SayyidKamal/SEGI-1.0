package com.example.segi10.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.segi10.Model.Course;
import com.example.segi10.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminAddCourse extends AppCompatActivity {

    DatabaseReference dbref;

    String strUserId, strUsername, strFaculty="";

    TextView txtCourseCode,txtCourseName,txtCoureCredits,txtFaculty,txtSpecialty;
    Button btnAddCourse;
    RadioGroup radGrpCrediHours, radGrpSpecialty;
    RadioButton radbtnCrediHours;
    CheckBox check_bsd, check_cn, check_se;

    ArrayList<String> specialtyList;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_course);


        try {

            radGrpCrediHours =(RadioGroup)  findViewById(R.id.radGrpCrediHours);
            txtCourseCode = (EditText) findViewById(R.id.txtCourseCode);
            txtCourseName = (EditText) findViewById(R.id.txtCourseName);
            btnAddCourse = (Button) findViewById(R.id.btnAddCourse);


            toolbar = findViewById(R.id.myToolBar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Add Course");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });

            dbref = FirebaseDatabase.getInstance().getReference().child("Courses");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickAddCourse(View v){
        try {

            String courseCode = txtCourseCode.getText().toString();
            String CourseName = txtCourseName.getText().toString();
            String creditHours = radbtnCrediHours.getText().toString();
            String specialty = TextUtils.join(", ", specialtyList);


            Course course = new Course();
            course.setCode(courseCode.trim().toUpperCase());
            course.setName(CourseName);
            course.setCreditHours(creditHours);
            course.setSpecialty(specialty);

            dbref.child(courseCode).setValue(course);

            //dbref.push().setValue(student);

            //Toast.makeText(getApplicationContext(),"Successfully inserted",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), courseCode + " is Added!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkCreditRadBtn(View v){
        try {
            hidekeyboard();
            int radioId = radGrpCrediHours.getCheckedRadioButtonId();
            radbtnCrediHours = findViewById(radioId);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
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

    public void check_bsd(View view) {
        if (check_bsd.isChecked())
            specialtyList.add("Business System Designs");
        else
            specialtyList.remove("Business System Designs");
    }

    public void check_cn(View view) {
        if (check_cn.isChecked())
            specialtyList.add("Computer Networks");
        else
            specialtyList.remove("Computer Networks");
    }

    public void check_se(View view) {
        if (check_se.isChecked())
            specialtyList.add("Software Engineering");
        else
            specialtyList.remove("Software Engineering");
    }
}

