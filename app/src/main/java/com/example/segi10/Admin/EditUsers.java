package com.example.segi10.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Adapter.stud_info_adapter;
import com.example.segi10.Model.User;
import com.example.segi10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUsers extends AppCompatActivity {


    String tempId, strUsername, strFaculty = "";
    Spinner spinner;
    private RecyclerView recyclerView;
    private stud_info_adapter mAdapter;
    List<User> users;
    Toolbar toolbar;

   /* FragmentMentor fragmentMentor;
    FragmentStudent fragmentStudent;*/

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_stud_info);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        spinner = findViewById(R.id.spinner);

        setToolbar("Edit Users");

        /*fragmentMentor = new FragmentMentor();
        fragmentStudent = new FragmentStudent();*/

        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditUsers.this,
                R.layout.custom_spinner,getResources().getStringArray(R.array.fragments));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        loadUsers("mentor");
                        //setFragment(fragmentMentor,"Mentor");
                        break;
                    case 0:
                        loadUsers("student");
                        //setFragment(fragmentStudent, "Student");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    public void loadUsers(String role) {
        dbref = FirebaseDatabase.getInstance().getReference().child("Users");
        users = new ArrayList<>();

        try {
            mAdapter = new stud_info_adapter(users);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .orderByChild("role")
                .equalTo(role);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User userTemp = ds.getValue(User.class);
                    users.add(userTemp);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAdapter.setOnItemClickListener(new stud_info_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String tempId = users.get(position).getId();
                Toast.makeText(getApplicationContext(), tempId , Toast.LENGTH_SHORT).show();
                //User user = users.get(position);
                //showUpdateDelete(user,tempId);
                try {
                    Intent actViewUser = new Intent(getApplicationContext(),AdminStudentInfo.class);
                    actViewUser.putExtra("tempId",tempId);
                    startActivity(actViewUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });


    }

    public void setToolbar(String title){
        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent AdminMain = new Intent(getApplicationContext(), AdminMainActivity.class);
                startActivity(AdminMain);
            }
        });

    }

    public void showUpdateDelete(final User user,String tempId){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_admin_student_info, null);

        adb.setView(dialogView);

        final CircleImageView profile_image;
        TextView username;
        final EditText txtName,txtIcPassport,txtStudentId,txtEmailAddress,txtPhoneNumber,txtAddress,txtMentor;

        /*Finding View*/

        profile_image   = dialogView.findViewById(R.id.profile_image);
        username        = dialogView.findViewById(R.id.username);
        txtName         = dialogView.findViewById(R.id.txtName);
        txtIcPassport   = dialogView.findViewById(R.id.txtIcPassport);
        txtStudentId    = dialogView.findViewById(R.id.txtStudentId);
        txtEmailAddress = dialogView.findViewById(R.id.txtEmailAddress);
        txtPhoneNumber = dialogView.findViewById(R.id.txtPhone);
        txtAddress      = dialogView.findViewById(R.id.txtAddress);
        txtMentor       = dialogView.findViewById(R.id.txtMentor);
        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        /*Getting Information*/
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("segiID")
                .equalTo(tempId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getApplicationContext() == null) {
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
                //username.setText(user.getName());
                txtName.setText(user.getName());
                txtIcPassport.setText(user.getIc_Passport());
                txtStudentId.setText(user.getSegiID());
                txtPhoneNumber.setText(user.getPhoneNo());
                txtEmailAddress.setText(user.getEmail());
                txtAddress.setText(user.getAddress());
                //txtMentor.setText(user.);


              /*  if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.drawable.profile_photo_default);
                    //getSupportActionBar().setLogo(getDrawable(R.mipmap.ic_launcher));
                } else{
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbar= findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(user.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final AlertDialog alert = adb.create();
        alert.show();



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strName = txtName.getText().toString();
                final String strIcPassport = txtIcPassport.getText().toString();
                final String strSegiId = txtStudentId.getText().toString();
                final String strEmail = txtEmailAddress.getText().toString();
                final String strPhone = txtPhoneNumber.getText().toString();
                final String strAddress = txtEmailAddress.getText().toString();


                /*if(TextUtils.isEmpty(foodName)){
                    edFname.setError("Name required!!");
                    return;
                }

                if(TextUtils.isEmpty(foodOrigin)){
                    edForigin.setError("Origin required!!");
                    return;
                }*/

                DatabaseReference ref = FirebaseDatabase.getInstance().
                        getReference("Users").child(user.getId());

                User userTemp = new User(user.getId(), strName, strIcPassport, strSegiId,
                        strEmail, strPhone, strAddress, strName.toLowerCase());
                ref.setValue(userTemp); //update value in firebase
                Toast.makeText(getApplicationContext(), "Food updated Successfully"+user.getId(),
                        Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });

        /*btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference("foods").child(user.getId());
                ref.removeValue();
                alert.dismiss();
            }
        });*/
    }

    private void getInfo (String tempId) {

    }



}

