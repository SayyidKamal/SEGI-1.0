package com.example.segi10.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.segi10.Model.Chat;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    public  static final int MSG_TYPE_LEFT =0;
    public  static final int MSG_TYPE_RIGHT =1;

    private List<Chat> mchat;
    private Context mContext;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessageAdapter() {
    }

    public MessageAdapter(Context mContext, List<Chat> mchat, String imageurl) {

        this.mchat = mchat;
        this.mContext = mContext;
        this.imageurl = imageurl;

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public TextView txt_seen;
        public ImageView profile_image;


        public CustomViewHolder(View view) {
            super(view);

            show_message = (TextView) view.findViewById(R.id.show_message);
            txt_seen = (TextView) view.findViewById(R.id.txt_seen);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);


        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }

    }

    public MessageAdapter(List<Chat> mchat) {
        this.mchat = mchat;
    }

    @Override
    public MessageAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right, parent, false);
            return new MessageAdapter.CustomViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left, parent, false);
            return new MessageAdapter.CustomViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(MessageAdapter.CustomViewHolder holder, int position) {

        final Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.drawable.profile_photo_default);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if(position == mchat.size()-1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        }else {
            holder.txt_seen.setVisibility(View.GONE);
        }


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);

            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }
}

/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.segi10.Model.Chat;
import com.example.segi10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    public  static final int MSG_TYPE_LEFT =0;
    public  static final int MSG_TYPE_RIGHT =1;

    private List<Chat> mchat;
    private Context mContext;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessageAdapter() {
    }

    public MessageAdapter(Context mContext, List<Chat> mchat, String imageurl) {

        this.mchat = mchat;
        this.mContext = mContext;
        this.imageurl = imageurl;

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public TextView txt_seen;
        public ImageView profile_image;


        public CustomViewHolder(View view) {
            super(view);

            show_message = (TextView) view.findViewById(R.id.show_message);
            txt_seen = (TextView) view.findViewById(R.id.txt_seen);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);


        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }

    }

    public MessageAdapter(List<Chat> mchat) {
        this.mchat = mchat;
    }

    @Override
    public MessageAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_right, parent, false);
            return new MessageAdapter.CustomViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_left, parent, false);
            return new MessageAdapter.CustomViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(MessageAdapter.CustomViewHolder holder, int position) {

        final Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());
        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.drawable.profile_photo_default);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if(position == mchat.size()-1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        }else {
            holder.txt_seen.setVisibility(View.GONE);
        }


        *//*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);

            }
        });*//*


    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }
}*/


