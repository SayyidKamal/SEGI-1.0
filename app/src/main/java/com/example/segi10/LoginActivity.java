package com.example.segi10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText emailET, pwdET;
    Button loginButton;

    ProgressDialog pd;
    FirebaseAuth auth;
    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        emailET = findViewById(R.id.email);
        pwdET = findViewById(R.id.password);
        pd = new ProgressDialog(this);
        loginButton = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();

    }


    public void loginButton(View view){
       login();
    }

    public void login(){
        String email = emailET.getText().toString();
        String pwd = pwdET.getText().toString();
        //check whether the fields are empty or not
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"All fields are Required", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.setMessage("Logging in....");
        pd.show();
        auth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    startActivity(new Intent(getApplicationContext(), Linkers.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

}
