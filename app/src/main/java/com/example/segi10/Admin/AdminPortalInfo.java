package com.example.segi10.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.segi10.Fragments.ProfileFragment;
import com.example.segi10.Model.Portal;
import com.example.segi10.R;
import com.example.segi10.Student.StudHomeFragment;
import com.example.segi10.Student.StudentMainActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class AdminPortalInfo extends AppCompatActivity {

    String selectedCourses,portalID;

    LinearLayout selectCourseLayout,RegistrationInfo;
    TextView lblportalID,lblType,lblEarlyReg,lblLateReg;
    EditText txtLecName,txtS1StartTime,txtS1EndTime,txtS2StartTime,txtS2EndTime;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal_info);

        portalID = getIntent().getStringExtra("portalID");
        findViews();

        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(portalID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), AdminPortal.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        reference = FirebaseDatabase.getInstance().getReference("Portals").child(portalID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Portal portal = dataSnapshot.getValue(Portal.class);


                lblportalID.setText(portal.getPortalId());
                lblType      .setText(portal.getType());
                lblEarlyReg .setText(portal.getStartDate()+" - "+portal.getLastEarlyDate());
                lblLateReg .setText(portal.getLastEarlyDate()+" - "+portal.getLateDate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Portals").child(portalID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                viewPagerAdapter.addFragment(new AdminOfferCourseFragment(),"Offered Courses");
                viewPagerAdapter.addFragment(new AdminRegistrationFragment(),"Student Registrations");
                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
                //tabLayout.getTabAt(0).setIcon(R.drawable.new_registration_icon);
                //tabLayout.getTabAt(1).setIcon(R.drawable.users_icon);
               /* if (isDp==false)
                    tabLayout.getTabAt(2).setIcon(R.drawable.profile_white);
                else{ //tabLayout.getTabAt(2).setCustomView(tabOne);
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void findViews(){

        selectCourseLayout = findViewById(R.id.selectCourseLayout);
        RegistrationInfo = findViewById(R.id.RegistrationInfo);

        lblportalID = (TextView) findViewById(R.id.lblPortalID);
        lblType     = (TextView) findViewById(R.id.lblType);
        lblEarlyReg = (TextView) findViewById(R.id.lblEarlyReg);
        lblLateReg  = (TextView) findViewById(R.id.lblLateReg);

        txtLecName  = (MaterialEditText) findViewById(R.id.txtLecName);

        txtS1StartTime = findViewById(R.id.txtS1StartTime);
        txtS1EndTime = findViewById(R.id.txtS1EndTime);
        txtS2StartTime = findViewById(R.id.txtS2StartTime);
        txtS2EndTime = findViewById(R.id.txtS2EndTime);
    }

    public String getportalID() {
        return portalID;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        public void addFragment (Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
