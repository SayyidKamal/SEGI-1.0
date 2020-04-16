package com.example.segi10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.load.engine.Resource;
import com.example.segi10.Admin.AdminHomeFragment;
import com.example.segi10.Admin.AdminMainActivity;
import com.example.segi10.Student.StudentMainActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Linkers extends AppCompatActivity {

    Button chat, studenMenu, trial;
    FirebaseUser firebaseUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkers);

        setAppLocale("ms");

    }

    public void toChat(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void toStudentMenu(View view) {
        startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));
    }

    public void toTrial(View view) {
        startActivity(new Intent(getApplicationContext(), Trial.class));
    }

    public void toAdminMenu(View view) {
        startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
    }

    private void setAppLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration conf = new Configuration();
        conf.locale = locale;
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings" , MODE_PRIVATE).edit();
        editor.putString("My_Lang",localeCode);
        editor.apply();
    }

    public  void  LoadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings" , MODE_PRIVATE);
        String localeCode = preferences.getString("My_Lang","");
        setAppLocale(localeCode);
    }
}
