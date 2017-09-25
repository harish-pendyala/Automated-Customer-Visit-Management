package com.example.naveenkumar.ssdiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by naveenkumar on 11/29/16.
 */
public class AppointmentsAdapters extends ArrayAdapter<Appointment> {
    Context mContext;
    int mResource;
    List<Appointment> mData;

    public AppointmentsAdapters(Context mContext, int mResource, List<Appointment> mData){
        super(mContext,mResource,mData);
        this.mContext = mContext;
        this.mResource = mResource;
        this.mData = mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResource,parent,false);

        TextView timeAndDate = (TextView)convertView.findViewById(R.id.aiTime);
        TextView location = (TextView)convertView.findViewById(R.id.aiLocation);

        Appointment appointment = mData.get(position);
        timeAndDate.setText("Your appointment on "+appointment.getAppointment_Date()+" at "+appointment.getAppointment_Time());
        location.setText("Branch is : "+appointment.getBranch_Name());

        return convertView;
    }
}
