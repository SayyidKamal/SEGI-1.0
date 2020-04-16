package com.example.segi10.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.segi10.MessageActivity;
import com.example.segi10.Model.User;
import com.example.segi10.R;

import java.util.List;


public class edit_users_adapter extends RecyclerView.Adapter<edit_users_adapter.CustomViewHolder> {
    private List<User> users;
    private Context mContext;

    public edit_users_adapter(){}

    public edit_users_adapter(Context mContext, List<User> mUsers ){

        this.users = mUsers;
        this.mContext = mContext;

    }




    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView txtUserName,txtUserId;
        public ImageView profile_image;


        public CustomViewHolder(View view) {
            super(view);


            txtUserId = (TextView) view.findViewById(R.id.txtUserId);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            profile_image = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.profile_image);



        }
    }

    public edit_users_adapter(List<User> users) {
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


}