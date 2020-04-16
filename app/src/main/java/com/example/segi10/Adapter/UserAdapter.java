package com.example.segi10.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.segi10.MessageActivity;
import com.example.segi10.Model.Chat;
import com.example.segi10.Model.User;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {
    private List<User> users;
    private Context mContext;
    private boolean ischat;

    String theLastMessage;

    public UserAdapter(){}

    public UserAdapter(Context mContext, List<User> mUsers, boolean ischat ){

        this.users = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView txtUserName,txtUserId,last_msg;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;


        public CustomViewHolder(View view) {
            super(view);

            txtUserId = (TextView) view.findViewById(R.id.txtUserId);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            last_msg = (TextView) view.findViewById(R.id.last_msg);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.profile_image);
            img_off = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.img_off);
            img_on = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.img_on);

        }
    }



    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        final User user = users.get(position);
        holder.txtUserId.setText(user.getId());
        holder.txtUserName.setText(user.getName());
        if(user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.profile_photo_default);
        }else{
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }

        if (ischat){
            lastMessage(user.getId(),holder.last_msg);
        } else {
            holder.last_msg.setVisibility(View.GONE);
        }

        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    private  void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){

                        theLastMessage = chat.getMessage();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}