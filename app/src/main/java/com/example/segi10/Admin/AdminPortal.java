package com.example.segi10.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.segi10.Adapter.PortalAdapter;
import com.example.segi10.Dialogs.PortalLayout;
import com.example.segi10.Linkers;
import com.example.segi10.MainActivity;
import com.example.segi10.Model.Portal;
import com.example.segi10.R;
import com.example.segi10.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPortal extends AppCompatActivity implements PortalLayout.RegisterLayoutListener {

    PortalLayout portalLayout;

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private PortalAdapter mAdapter;
    List<Portal> portalList;

    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portals);


        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Portals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        populateRecyclerView();
    }

    public void newRegistration(View view) {
        openDialog();
    }

    public void openDialog() {
        portalLayout = new PortalLayout();
        portalLayout.show(getSupportFragmentManager(),"");
    }

    @Override
    public void applyTexts(String RegistrationId, String OpenDate , String lastEarlyDate , String lateReg , String RegType) {
        portalLayout.dismiss();
        Toast.makeText(getApplicationContext(),RegistrationId + "\n" + OpenDate + "\n" +  RegType
                + "\n" + lastEarlyDate + "\n" +  lateReg + "\n" +  RegType,Toast.LENGTH_SHORT).show();

        //reference = FirebaseDatabase.getInstance().getReference("Registrations").child(RegistrationId);
    }

    public  void populateRecyclerView(){
        reference = FirebaseDatabase.getInstance().getReference().child("Portals");
        portalList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new PortalAdapter(portalList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                portalList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Portal portalTemp = ds.getValue(Portal.class);
                    portalList.add(portalTemp);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAdapter.setOnItemClickListener(new PortalAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                String portalID= portalList.get(position).getPortalId();

                Toast.makeText(getApplicationContext(), portalID,Toast.LENGTH_SHORT).show();
                Intent AdminPortalInfo = new Intent(getApplicationContext(), AdminPortalInfo.class);
                AdminPortalInfo.putExtra("portalID", portalID);
                startActivity(AdminPortalInfo);
            }
        });

    }

}
