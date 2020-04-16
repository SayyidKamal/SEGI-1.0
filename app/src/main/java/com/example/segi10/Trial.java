package com.example.segi10;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.segi10.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Trial extends AppCompatActivity {

    private Toolbar toolbar;
    MaterialEditText txtName,txtIcPassport, txtSegiId,txtPassword,txtConfirmPassword;
    MaterialEditText txtPhone,textEmailAddress,postCode,address,txtIntakeDate;

    private DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    Calendar calendar;
    ProgressDialog pd;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trial);

            //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            auth = FirebaseAuth.getInstance();

            findViews();

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sign-Up");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(Trial.this, Linkers.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });


           txtPhone.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   hidekeyboard();
               }
           });

            address.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_HOVER_ENTER)
                    showAddress();
                    hidekeyboard();
                    return true;
                }
            });

            address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddress();
                    hidekeyboard();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
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


    public void showAddress(){

        final String[][] cityArray = {new String[10-0]};
        final ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line, cityArray[0]);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.address_dialog, null);
        adb.setView(dialogView);


        final Spinner spinnerState, spinnerCity;
        final MaterialEditText homeAddress,postCode;
        final Button btnSet, btnCancel ;


        spinnerState = dialogView.findViewById(R.id.spinnerState);
        spinnerCity  = dialogView.findViewById(R.id.spinnerCity);
        homeAddress  = dialogView.findViewById(R.id.address);
        postCode     = dialogView.findViewById(R.id.postCode);
        btnSet       = dialogView.findViewById(R.id.btnSet);
        btnCancel    = dialogView.findViewById(R.id.btnCancel);


        Toolbar toolbar =  dialogView.findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Address");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/



        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i !=0 ){
                    String selectedState = (String) ((AppCompatTextView) view).getText();
                    Toast.makeText(getApplicationContext(), "State : " + selectedState ,Toast.LENGTH_SHORT).show();
                    spinnerCity.setEnabled(true);

                    if (selectedState.equals("Kelantan")){
                        cityArray[0] = getResources().getStringArray(R.array.Kelantan);
                    } else if (selectedState.equals("Malacca")){
                        cityArray[0] = getResources().getStringArray(R.array.Malacca);
                    }else if (selectedState.equals("Johor")){
                        cityArray[0] = getResources().getStringArray(R.array.Johor);
                    } else if (selectedState.equals("Negeri Sembilan")){
                        cityArray[0] = getResources().getStringArray(R.array.Negeri_Sembilan);
                    } else if (selectedState.equals("Pahang")){
                        cityArray[0] = getResources().getStringArray(R.array.Pahang);
                    } else if (selectedState.equals("Penang")){
                        cityArray[0] = getResources().getStringArray(R.array.Penang);
                    } else if (selectedState.equals("Perak")){
                        cityArray[0] = getResources().getStringArray(R.array.Perak);
                    } else if (selectedState.equals("Perlis")){
                        cityArray[0] = getResources().getStringArray(R.array.Perlis);
                    } else if (selectedState.equals("Sabah")){
                        cityArray[0] = getResources().getStringArray(R.array.Sabah);
                    } else if (selectedState.equals("Sarawak")){
                        cityArray[0] = getResources().getStringArray(R.array.Sarawak);
                    } else if (selectedState.equals("Selangor")){
                        cityArray[0] = getResources().getStringArray(R.array.Selangor);
                    } else if (selectedState.equals("Terengganu")){
                        cityArray[0] = getResources().getStringArray(R.array.Terengganu);
                    }
                    //spinnerCity.setAdapter(cityArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final AlertDialog alert = adb.create();
        alert.setCanceledOnTouchOutside(false);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String strHomeAdd = homeAddress.getText().toString();
                final String strPostcode = postCode.getText().toString();
                final String strState = spinnerState.getSelectedItem().toString();
                final String strCity = spinnerCity.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), strHomeAdd + ", " + strPostcode + ", "
                        + strCity + ", " + strState + "." ,Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(strHomeAdd) || TextUtils.isEmpty(strPostcode) ||
                        spinnerState.getSelectedItemPosition()==0 /*|| spinnerCity.getSelectedItemPosition()==0*/) {
                    if (TextUtils.isEmpty(strHomeAdd)) {
                        homeAddress.setError("Address Required (e.g B1-16-06 Emporis...)");
                    }
                    if (TextUtils.isEmpty(strPostcode)) {
                        postCode.setError("PostCode required");
                    }
                    if (spinnerState.getSelectedItemPosition() == 0) {
                        TextView errorText = (TextView) spinnerState.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("");//c
                    }
                    /*if (spinnerCity.getSelectedItemPosition() == 0) {
                        TextView errorText = (TextView) spinnerCity.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("");//c

                    }*/
                    return;
                }

                final String strAddress = strHomeAdd + ", " + strPostcode + ", " + strCity + ", " + strState + ".";
                Toast.makeText(getApplicationContext(),strAddress,Toast.LENGTH_SHORT).show();
                address.setText(strAddress);
                alert.cancel();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });

        alert.show();

    }

    private void findViews() {

        txtName            = (MaterialEditText) findViewById(R.id.txtName);
        txtIcPassport      = (MaterialEditText) findViewById(R.id.txtIcPassport);
        txtSegiId          = (MaterialEditText) findViewById(R.id.txtStudentId);
        txtPassword        = (MaterialEditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (MaterialEditText) findViewById(R.id.txtConfirmPassword);
        textEmailAddress   = (MaterialEditText) findViewById(R.id.textEmailAddress);
        txtPhone           = (MaterialEditText) findViewById(R.id.txtPhone);
        postCode           = (MaterialEditText) findViewById(R.id.postCode);
        address            = (MaterialEditText) findViewById(R.id.address);
        txtIntakeDate      = (MaterialEditText) findViewById(R.id.txtDate);


        toolbar = findViewById(R.id.myToolBar);
    }

    private void setCities (String selectedState){

        String[] cityArray = new String[100];
        if (selectedState.equals("Kelantan")){
            cityArray = getResources().getStringArray(R.array.Kelantan);
        } else if (selectedState.equals("Malacca")){
            cityArray = getResources().getStringArray(R.array.Malacca);
        }else if (selectedState.equals("Johor")){
            cityArray = getResources().getStringArray(R.array.Johor);
        } else if (selectedState.equals("Negeri Sembilan")){
            cityArray = getResources().getStringArray(R.array.Negeri_Sembilan);
        } else if (selectedState.equals("Pahang")){
            cityArray = getResources().getStringArray(R.array.Pahang);
        } else if (selectedState.equals("Penang")){
            cityArray = getResources().getStringArray(R.array.Penang);
        } else if (selectedState.equals("Perak")){
            cityArray = getResources().getStringArray(R.array.Perak);
        } else if (selectedState.equals("Perlis")){
            cityArray = getResources().getStringArray(R.array.Perlis);
        } else if (selectedState.equals("Sabah")){
            cityArray = getResources().getStringArray(R.array.Sabah);
        } else if (selectedState.equals("Sarawak")){
            cityArray = getResources().getStringArray(R.array.Sarawak);
        } else if (selectedState.equals("Selangor")){
            cityArray = getResources().getStringArray(R.array.Selangor);
        } else if (selectedState.equals("Terengganu")){
            cityArray = getResources().getStringArray(R.array.Terengganu);
        }

        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, cityArray );
        //spinnerCity.setAdapter(cityArrayAdapter);
    }

    public void toRegister(final View view) {

        final String strName = txtName.getText().toString();
        final String strIcPassport = txtIcPassport.getText().toString();
        final String strSegiId = txtSegiId.getText().toString();
        final String strPassword = txtPassword.getText().toString();
        final String strConfirmPassword = txtConfirmPassword.getText().toString();
        final String strEmail = textEmailAddress.getText().toString();
        final String strPhone = txtPhone.getText().toString();
        final String strAddress = address.getText().toString();
        final String strIntakeDate = txtIntakeDate.getText().toString();


        if( TextUtils.isEmpty(strName) || TextUtils.isEmpty(strIcPassport) || TextUtils.isEmpty(strSegiId) ||
                TextUtils.isEmpty(strPassword)|| TextUtils.isEmpty(strConfirmPassword) || TextUtils.isEmpty(strEmail) ||
                TextUtils.isEmpty(strPhone) || TextUtils.isEmpty(strAddress) || TextUtils.isEmpty(strIntakeDate) ) {

            if (TextUtils.isEmpty(strName)) {
                txtName.setError("Name required");
            }
            if (TextUtils.isEmpty(strIcPassport)) {
                txtIcPassport.setError("Passport No required");
            }
            if (TextUtils.isEmpty(strSegiId)) {
                txtSegiId.setError("Segi Id required");
            }
            if (TextUtils.isEmpty(strPassword)) {
                txtPassword.setError("Password required");
            }
            if (TextUtils.isEmpty(strConfirmPassword)) {
                txtConfirmPassword.setError("Confirm Password required");
            }
            if (TextUtils.isEmpty(strEmail)) {
                textEmailAddress.setError("Email required");
            }
            if (TextUtils.isEmpty(strPhone)) {
                txtPhone.setError("Phone Number required");
            }
            if (TextUtils.isEmpty(strAddress)) {
                address.setError("Address required");
            }
            if (TextUtils.isEmpty(strIntakeDate)) {
                txtIntakeDate.setError("Intake date required");
            }
            return;
        }

        Toast.makeText(getApplicationContext(),
                "Name:" + strName + "\nIC:" + strIcPassport + "\nSegiID:" + strSegiId
                        + "Password:" + strPassword + "\nRole:" + "student" + "\nEmail:" + strEmail +
                        "\nPhone:" + strPhone + "\nAddress:" + strAddress + "\nIntake:" + strIntakeDate, Toast.LENGTH_SHORT).show();

        /*register(strName,strIcPassport,strSegiId,strPassword,"student",strEmail,strPhone,address,
                 strIntakeDate);*/


        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                        Toast.makeText(getApplicationContext(), "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        String userid = firebaseUser.getUid();

                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                        User user = new User(
                                                userid, strName, strIcPassport, strSegiId, "student",
                                                strEmail, strPhone, strAddress, "default",
                                                "offline", strName.toLowerCase(), strIntakeDate);

                                        reference.setValue(user);
                                    }




                                }else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(),
                                            "Failed", Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
    }

    /*public void register (final String name, final String ic_Passport, final String segiID, String password,
                           final String role, final String email, final String phoneNo,
                           final String address, final String intakeDate){

        pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Registering the user....");
        pd.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    String userid = firebaseUser.getUid();

                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                    User user = new User("default", userid, strName, strIcPassport, strSegiId, "student", strEmail, strPhone, address, "offline", strName.toLowerCase(), strIntakeDate);
                                    user.setId(userid);
                                    user.setIc_Passport(ic_Passport);
                                    user.setSegiID(segiID);
                                    user.setRole(role);
                                    user.setEmail(email);
                                    user.setPhoneNo(phoneNo);
                                    user.setAddress(address);
                                    user.setIntakeDate(intakeDate);
                                    user.setImageURL("default");
                                    user.setStatus("offline");
                                    user.setStatus(name.toLowerCase());

                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

                                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent = new Intent(Trial.this, Linkers.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });


                                }else{
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(),
                                            "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

    }*/

    public void getDate(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Trial.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        try {
                            String strIntakeTemp;
                            month += 1;

                            if (month <10)
                                strIntakeTemp = "0" + month + "/" + year;
                            else
                                strIntakeTemp = month + "/" + year;

                            txtIntakeDate.setText(strIntakeTemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, year, month, dayOfMonth) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
            }
        };
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void restrict(){
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtIcPassport      = (MaterialEditText) findViewById(R.id.txtIcPassport);
        txtSegiId          = (MaterialEditText) findViewById(R.id.txtStudentId);
        txtPassword        = (MaterialEditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (MaterialEditText) findViewById(R.id.txtConfirmPassword);
        textEmailAddress   = (MaterialEditText) findViewById(R.id.textEmailAddress);
        txtPhone           = (MaterialEditText) findViewById(R.id.txtPhone);
        postCode           = (MaterialEditText) findViewById(R.id.postCode);
        address            = (MaterialEditText) findViewById(R.id.address);
        txtIntakeDate      = (MaterialEditText) findViewById(R.id.txtDate);
    }

}
