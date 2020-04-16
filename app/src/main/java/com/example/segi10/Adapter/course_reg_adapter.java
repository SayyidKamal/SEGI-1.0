package com.example.segi10.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Model.Course;
import com.example.segi10.Model.OfferedCourse;
import com.example.segi10.R;


import java.util.List;

public class course_reg_adapter extends RecyclerView.Adapter<course_reg_adapter.CustomViewHolder> {
    private List<Course> courses;
    private OnItemClickListener mListener;
    String selectedCourses ="";

    public interface OnItemClickListener {

        void onViewClicked(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCourseName, txtCourseCode, txtCourseCredits, txtCourseSpecialty;
        public CheckBox chkAddCourse;

        public CustomViewHolder(View view,  final OnItemClickListener listener) {
            super(view);
            txtCourseName = (TextView) view.findViewById(R.id.txtCourseName);
            txtCourseCode = (TextView) view.findViewById(R.id.txtCourseCode);
            txtCourseCredits = (TextView) view.findViewById(R.id.txtCourseCredits);
            txtCourseSpecialty = (TextView) view.findViewById(R.id.lblSpecialty);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onViewClicked(position);
                        }
                    }
                }
            });

        }


    }

    public course_reg_adapter(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_course_reg_adapter, parent, false);

        return new CustomViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.txtCourseName.setText(course.getName());
        holder.txtCourseCode.setText(course.getCode());
        holder.txtCourseCredits.setText(course.getCreditHours());
        holder.txtCourseSpecialty.setText(course.getSpecialty());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

}