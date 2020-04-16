package com.example.segi10.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.segi10.Adapter.UserAdapter;
import com.example.segi10.Model.Chatlist;
import com.example.segi10.Model.User;
import com.example.segi10.Notification.Token;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    List<User> mUsers;

    private List<Chatlist> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        usersList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void  updateToken (String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        databaseReference.child(firebaseUser.getUid()).setValue(token);
    }

    private void chatList() {
        mUsers =  new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for(Chatlist chatlist : usersList){
                       if (user.getId().equals(chatlist.getId()))
                        mUsers.add(user);
                    }
                }
                userAdapter = new UserAdapter(getContext(),mUsers,true);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

/*
firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        usersList.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
        Chat chat = snapshot.getValue(Chat.class);

        if (chat.getSender().equals(firebaseUser.getUid())){
        usersList.add(chat.getReceiver());
        }

        if (chat.getReceiver().equals(firebaseUser.getUid())){
        usersList.add(chat.getSender());
        }

        readchats();

        }
        }

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });
*/



   /* private void readchats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    mUsers.clear();

                    //Display 1 user from chats
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);

                        //for(String id : usersList){
                        for(ListIterator<String> id = usersList.listIterator();id.hasNext();){
                            //if (user.getId().equals(id)){
                            if (id.next().equals(user.getId())){
                                if(mUsers.size() != 0){
                                    for (ListIterator<User> user1 =mUsers.listIterator();user1.hasNext();){
                                        if(!user1.next().getId().equals(user.getId())){
                                            user1.add(user);
                                        }
                                    }
                                } else{
                                    mUsers.add(user);
                                }
                            }
                        }
                    }

                    userAdapter = new UserAdapter(getContext(), mUsers,true);
                    recyclerView.setAdapter(userAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Err:ChatFragment | val evnt lisner : " + e , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/