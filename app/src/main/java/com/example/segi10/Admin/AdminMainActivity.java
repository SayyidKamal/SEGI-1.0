package com.example.segi10.Admin;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.segi10.Fragments.ChatFragment;
import com.example.segi10.Fragments.ProfileFragment;
import com.example.segi10.Model.User;
import com.example.segi10.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Uri profile_uri;
    Boolean isDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (getApplicationContext() == null) { return; }

                    User user = dataSnapshot.getValue(User.class);

                    if (user.getImageURL().equals("default")){
                        isDp = false;
                    } else{

                        isDp = true;


                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Err:Main Activity | AdValEvnListn : " + e , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminMainActivity.ViewPagerAdapter viewPagerAdapter = new AdminMainActivity.ViewPagerAdapter(getSupportFragmentManager());

                viewPagerAdapter.addFragment(new AdminHomeFragment(),"Home");
                viewPagerAdapter.addFragment(new ChatFragment(),"Chats");
                viewPagerAdapter.addFragment(new ProfileFragment(),"Profile");
                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(R.drawable.home);
                tabLayout.getTabAt(1).setIcon(R.drawable.chat);
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

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                //finish();
                return true;
        }
        return false;
    }
*/
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


    private void status (String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }


    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}
