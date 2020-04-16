package com.example.segi10.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.segi10.Model.User;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminStudentInfo extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    EditText txtName,txtIcPassport,txtStudentId,txtEmailAddress,txtPhone,txtAddress,txtMentor;
    Toolbar toolbar;

    String tempId;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_info);

        try {

            tempId = getIntent().getStringExtra("tempId");

            findView();
            getInfo(tempId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findView (){
        profile_image   = findViewById(R.id.profile_image);
        username        = findViewById(R.id.username);
        txtName         = findViewById(R.id.txtName);
        txtIcPassport   = findViewById(R.id.txtIcPassport);
        txtStudentId    = findViewById(R.id.txtStudentId);
        txtEmailAddress = findViewById(R.id.txtEmailAddress);
        txtPhone        = findViewById(R.id.txtPhone);
        txtAddress      = findViewById(R.id.txtAddress);
        txtMentor       = findViewById(R.id.txtMentor);
    }


    private void getInfo (String tempId) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(tempId);

        reference.addValueEventListener(new ValueEventListener() {
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
                txtEmailAddress.setText(user.getEmail());
                txtAddress.setText(user.getAddress());
                //txtMentor.setText(user.);

                setToolbar(user.getName());


                try {
                    if (user.getImageURL().equals("default")){
                        profile_image.setImageResource(R.drawable.profile_photo_default);
                        //getSupportActionBar().setLogo(getDrawable(R.mipmap.ic_launcher));
                    } else{
                        Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void OnClickUpdate(View view) {

        final String strName = txtName.getText().toString();
        final String strIcPassport = txtIcPassport.getText().toString();
        final String strSegiId = txtStudentId.getText().toString();
        final String strEmail = txtEmailAddress.getText().toString();
        final String strPhone = txtPhone.getText().toString();
        final String strAddress = txtAddress.getText().toString();

        if(TextUtils.isEmpty(strName)){
            txtName.setError("Name required!!");
            return;
        }
        if(TextUtils.isEmpty(strIcPassport)){
            txtIcPassport.setError("Passport No required!!");
            return;
        }if(TextUtils.isEmpty(strSegiId)){
            txtStudentId.setError("Segi Id required!!");
            return;
        }if(TextUtils.isEmpty(strEmail)){
            txtEmailAddress.setError("Email required!!");
            return;
        }if(TextUtils.isEmpty(strPhone)){
            txtPhone.setError("Phone Number required!!");
            return;
        }

        if(TextUtils.isEmpty(strAddress)){
            txtAddress.setError("Address required!!");
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("Users").child(tempId);

        User userTemp = new User();
        userTemp.setId(tempId);
        userTemp.setName(strName);
        userTemp.setIc_Passport(strIcPassport);
        userTemp.setSegiID(strSegiId);
        userTemp.setEmail(strEmail);
        userTemp.setPhoneNo(strPhone);
        userTemp.setAddress(strAddress);
        userTemp.setSearch(strName.toLowerCase());

        ref.setValue(userTemp); //update value in firebase
        Toast.makeText(getApplicationContext(), "User updated Successfully"+ tempId,
                Toast.LENGTH_SHORT).show();
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

                Intent AdminMain = new Intent(getApplicationContext(), EditUsers.class);
                startActivity(AdminMain);
            }
        });
    }
}
