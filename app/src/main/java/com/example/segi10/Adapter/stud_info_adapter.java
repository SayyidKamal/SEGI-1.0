package com.example.segi10.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Model.User;
import com.example.segi10.R;

import java.util.List;


public class stud_info_adapter extends RecyclerView.Adapter<stud_info_adapter.CustomViewHolder> {
    private List<User> users;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView txtUserName,txtIntake,txtSegiId;


        public CustomViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            txtIntake = (TextView) view.findViewById(R.id.txtIntake);
            txtSegiId = (TextView) view.findViewById(R.id.txtSegiId);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public stud_info_adapter(List<User> users) {
        this.users = users;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_edit_users_adapter, parent, false);

        return new CustomViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        User user = users.get(position);
        holder.txtIntake.setText(user.getIntakeDate());
        holder.txtSegiId.setText(user.getSegiID());
        holder.txtUserName.setText(user.getName());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}




/*
package com.example.studentregisteration20.myadapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentregisteration20.R;

import java.util.List;

import com.example.studentregisteration20.myclasses.User;

public class edit_users_adapter extends RecyclerView.Adapter<edit_users_adapter.CustomViewHolder> {
    private List<User> users;
    private AdapterView.OnItemSelectedListener mListener;

    public interface OnItemClickListener {

        //void onDocClick(int position);
        void onItemClick(int position);

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtUserName, txtFaculty, txtUserId, txtUserRole;


        public CustomViewHolder(View view) {
            super(view);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            txtFaculty = (TextView) view.findViewById(R.id.txtFaculty);
            txtUserId = (TextView) view.findViewById(R.id.txtUserId);
           // txtUserRole = (TextView) view.findViewById(R.id.txtUserRole);

        }

        @Override
        public void onClick(View v) {

        }

    }

    public edit_users_adapter(List<User> users) {
        this.users = users;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.portal_dialog.activity_edit_users_adapter, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtUserName.setText(user.getName());
        holder.txtFaculty.setText(user.getFaculty());
        holder.txtUserId.setText(user.getID());
       // holder.txtUserRole.setText(user.());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}*/
