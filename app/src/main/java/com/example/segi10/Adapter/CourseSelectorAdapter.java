package com.example.segi10.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Model.OfferedCourse;
import com.example.segi10.R;

import java.util.List;

public class CourseSelectorAdapter extends RecyclerView.Adapter<CourseSelectorAdapter.CustomViewHolder> {
    private List<OfferedCourse> offeredCourses;
    private CourseSelectorAdapter.OnItemClickListener mListener;
    String selectedCourses ="";

    public void setOnItemClickListener(course_reg_adapter.OnItemClickListener onItemClickListener) {
    }

    public interface OnItemClickListener {

        void onViewClicked(int position);
        void oncheck(int position);
    }


    public void setOnItemClickListener(CourseSelectorAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView lblCourseID, lblCourseName, lblLecturerName, lblTimeSlot1, lblTimeSlot2;
        public CheckBox chkAddCourse;

        public CustomViewHolder(View view, final OnItemClickListener listener ) {
            super(view);
            lblCourseID = (TextView) view.findViewById(R.id.lblCourseID);
            lblCourseName = (TextView) view.findViewById(R.id.lblCourseName);
            lblLecturerName = (TextView) view.findViewById(R.id.lblLecturerName);
            lblTimeSlot1 = (TextView) view.findViewById(R.id.lblTimeSlot1);
            lblTimeSlot2 = (TextView) view.findViewById(R.id.lblTimeSlot2);
            chkAddCourse = (CheckBox) view.findViewById(R.id.chkAddCourse);

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

            chkAddCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.oncheck(position);
                            // Toast.makeText(v.getContext(), courses.get(position).getName() + " is " + chkAddCourse.isChecked(),Toast.LENGTH_SHORT).show();

                            String isChecked = offeredCourses.get(position).getCourseId() + ",";
                            if(selectedCourses.contains(isChecked)){
                                selectedCourses = selectedCourses.replaceAll(isChecked, "");
                            }else {
                                selectedCourses += isChecked;
                            }

                            Intent intent = new Intent("custom-message");
                            //intent.putExtra("isChecked",isChecked);
                            intent.putExtra("isChecked",selectedCourses);
                            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                        }
                    }
                }
            });


        }

    }

    public CourseSelectorAdapter(List<OfferedCourse> offeredCourses) {
        this.offeredCourses = offeredCourses;
    }

    @Override
    public CourseSelectorAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_selector_item, parent, false);

        return new CourseSelectorAdapter.CustomViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(CourseSelectorAdapter.CustomViewHolder holder, int position) {
        OfferedCourse offeredCourse = offeredCourses.get(position);
        holder.lblCourseID.setText(offeredCourse.getCourseId());
        holder.lblCourseName.setText(offeredCourse.getCourseName());
        holder.lblLecturerName.setText(offeredCourse.getLecturerName());
        holder.lblTimeSlot1.setText(offeredCourse.getSlot1());
        holder.lblTimeSlot2.setText(offeredCourse.getSlot2());

        //String slot1 = offeredCourse.

    }

    @Override
    public int getItemCount() {
        return offeredCourses.size();
    }


}
