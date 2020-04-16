package com.example.segi10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    //PermissionManager permissionManager;
    Button login, register, trial;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null){
            //Intent intent = new Intent(StartActivity.this, MainActivity.class);
            Intent intent = new Intent(StartActivity.this, Linkers.class);
            startActivity(intent);
            finish();
        }

        //permissionManager = new PermissionManager() {};
        //permissionManager.checkAndRequestPermissions(this);


        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
        trial = findViewById(R.id.btn_Trial);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, SignUp.class));
            }
        });

        trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,Trial.class));
            }
        });

    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String> denial = permissionManager.getStatus().get(0).denied;
    }*/
}
