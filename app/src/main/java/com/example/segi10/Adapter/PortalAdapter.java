package com.example.segi10.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.segi10.Model.Portal;
import com.example.segi10.R;

import java.util.List;

public class PortalAdapter extends RecyclerView.Adapter<PortalAdapter.CustomViewHolder>{
    private List<Portal> portals;
    private PortalAdapter.OnItemClickListener mListener;
    //String selectedCourses ="";

    public interface OnItemClickListener {

        void itemClicked(int position);

    }


    public void setOnItemClickListener(PortalAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView lblportalID, lblType, lblEarlyReg, lblLateReg;

        public CustomViewHolder(View view, final PortalAdapter.OnItemClickListener listener) {
            super(view);
            lblportalID = (TextView) view.findViewById(R.id.lblRegID);
            lblType    = (TextView) view.findViewById(R.id.lblType);
            lblEarlyReg = (TextView) view.findViewById(R.id.lblEarlyReg);
            lblLateReg  = (TextView) view.findViewById(R.id.lblLateReg);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.itemClicked(position);
                        }
                    }
                }
            });

        }


    }

    public PortalAdapter(List<Portal> portals) {
        this.portals = portals;
    }

    @Override
    public PortalAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.portal_item, parent, false);

        return new PortalAdapter.CustomViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(PortalAdapter.CustomViewHolder holder, int position) {
        Portal portal = portals.get(position);
        holder.lblportalID.setText(portal.getPortalId());
        holder.lblType      .setText(portal.getType());
        holder.lblEarlyReg .setText(portal.getStartDate()+" - "+portal.getLastEarlyDate());
        holder.lblLateReg .setText(portal.getLastEarlyDate()+" - "+portal.getLateDate());

    }

    @Override
    public int getItemCount() {
        return portals.size();
    }

}