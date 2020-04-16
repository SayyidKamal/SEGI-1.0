package com.example.segi10.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.Toolbar;

import com.example.segi10.Model.Portal;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PortalLayout extends AppCompatDialogFragment {

    private TextView txtPortalId;
    private MaterialEditText txtOpenDate;
    private Button btnCreate;
    RadioGroup radGrpType;
    RadioButton radio_long,radio_short;
    String regType, firstDate, lastEarlyDate, lateReg="";


    private RegisterLayoutListener listener;
    Toolbar toolbar;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;


    private DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

       // final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.portal_dialog, null);

        txtPortalId = view.findViewById(R.id.txtPortalId);
        txtOpenDate       = view.findViewById(R.id.txtOpenDate      );
        btnCreate         = view.findViewById(R.id.btnCreate        );
        radGrpType         = view.findViewById(R.id.radGrpType      );
        radio_long         = view.findViewById(R.id.radio_long      );
        radio_short         = view.findViewById(R.id.radio_short      );

        toolbar           = view.findViewById(R.id.myToolBar);

        radGrpType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_long:
                        regType="Long Sem";
                        lastEarlyDate = addDays(txtOpenDate.getText().toString(),14);
                        lateReg       = addDays(lastEarlyDate,28);

                        break;

                    case R.id.radio_short:
                        regType="Short Sem";
                        lastEarlyDate = addDays(txtOpenDate.getText().toString(),7);
                        lateReg       = addDays(lastEarlyDate,14);
                        break;

                }
            }
        });


        toolbar.setTitle("Create Portal");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        txtOpenDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                long now = System.currentTimeMillis() - 1000;

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if(day<10)
                                    day = Integer.parseInt("0" + day);

                                if ((month+1)<10) {
                                    month = Integer.parseInt("0" + (month + 1));
                                }else
                                    month +=1;

                                firstDate = day + "/" + month + "/" + year;

                                txtOpenDate.setText(firstDate);
                                txtPortalId.setText("PTL" + month + year);

                                radio_long.setEnabled(true);
                                radio_short.setEnabled(true);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(now);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtOpenDate.getText().toString().equals(null)||txtOpenDate.getText().toString().equals("")){
                    txtOpenDate.setError("Please select a date");
                } else {

                    String RegistrationId = txtPortalId.getText().toString();
                    String OpenDate       = txtOpenDate      .getText().toString();

                    reference = FirebaseDatabase.getInstance().getReference("Portals").child(RegistrationId);
                    Portal portal = new Portal(RegistrationId, OpenDate,lastEarlyDate,lateReg,regType);
                    reference.setValue(portal);

                    listener.applyTexts(RegistrationId, OpenDate,lastEarlyDate,lateReg,regType);
                }
            }
        });


        builder.setView(view)
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });




        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RegisterLayoutListener) context;
            hideKeyboard(context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }



    public static void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addDays(String dateInString,int noOfDays){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Calendar c = Calendar.getInstance();

            c.setTime(sdf.parse(dateInString));
            c.add(Calendar.DATE, noOfDays);

            sdf = new SimpleDateFormat("dd/mm/yyyy");

            Date resultdate = new Date(c.getTimeInMillis());
            dateInString = sdf.format(resultdate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateInString;
    }

    public interface RegisterLayoutListener {
        void applyTexts(String RegistrationId, String OpenDate , String lastEarlyDate , String lateReg , String RegType);
    }
}
