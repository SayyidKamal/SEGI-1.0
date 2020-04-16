package com.example.segi10.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.segi10.Adapter.OfferedCourseAdapter;
import com.example.segi10.Model.OfferedCourse;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminOfferCourseFragment extends Fragment {

    String portalID;
    Button btnAddCourse;

    RecyclerView recyclerView;
    private OfferedCourseAdapter mAdapter;
    List<OfferedCourse> offeredCourseList;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_admin_offer_course, container, false);

        //Getting portal ID
        AdminPortalInfo activity = (AdminPortalInfo) getActivity();
        portalID = activity.getportalID();

        //my recycler
        recyclerView = view.findViewById(R.id.recycler_view);
        try {
            populateRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnAddCourse = view.findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminCourse = new Intent(getContext(),AdminCourse.class);
                adminCourse.putExtra("portalID",portalID);
                startActivity(adminCourse);
            }
        });
        return view;
    }

    public  void populateRecyclerView(){
        reference = FirebaseDatabase.getInstance().getReference("Portals").child(portalID)
                .child("Offered Courses");

        offeredCourseList = new ArrayList<>();

        mAdapter = new OfferedCourseAdapter(offeredCourseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offeredCourseList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    OfferedCourse offeredCourse = ds.getValue(OfferedCourse.class);
                    offeredCourseList.add(offeredCourse);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAdapter.setOnItemClickListener(new OfferedCourseAdapter.OnItemClickListener() {
            @Override
            public void onViewClicked(int position) {
                Toast.makeText(getContext(),offeredCourseList.get(position).getCourseId(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void oncheck(int position) {

            }
        });

    }

}
