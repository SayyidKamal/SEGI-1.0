/*
package com.example.segi10;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.segi10.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {

    private Toolbar toolbar;
    CircleImageView profile_image;
    TextView username;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    username.setText(user.getName());
                    //getSupportActionBar().setTitle(user.getUsername());

                    String x = user.getImageURL();
                    String y = user.getName();
                    String z = user.getId();

                    Toast.makeText(getApplicationContext(),z + "\n" + y + "\n" + x + "\n"  , Toast.LENGTH_LONG).show();

                    if (x.equals("default")){
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                        //getSupportActionBar().setLogo(getDrawable(R.mipmap.ic_launcher));
                    } else{
                        Glide.with(UsersActivity.this).load(user.getImageURL()).into(profile_image);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Err:Main Activity | AdValEvnListn : " + e , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUsers = new ArrayList<>();

        readUsers();
    }

    private void  readUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        // mUsers = new ArrayList<>();


*/
/*
        testerAdapter = new TrialAdapter(getContext(), mUsers);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(testerAdapter);

*//*



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    //mUsers.add(user);
                    //testerAdapter.notifyDataSetChanged();

                    assert user != null;
                    assert firebaseUser != null;
                    if    (!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                }

                  userAdapter = new UserAdapter(getApplicationContext(),mUsers,false);
                  recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UsersActivity.this, StartActivity.class));
                finish();
                return true;
        }
        return false;
    }

}
*/
