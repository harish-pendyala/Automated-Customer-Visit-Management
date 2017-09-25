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
public class BanksAdapter extends ArrayAdapter<Bank> {
    Context mContext;
    int mResource;
    List<Bank> mData;

    public BanksAdapter(Context mContext, int mResource, List<Bank> mData){
        super(mContext,mResource,mData);
        this.mContext = mContext;
        this.mResource = mResource;
        this.mData = mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(mResource,parent,false);

        TextView name = (TextView)convertView.findViewById(R.id.biName);
        TextView address = (TextView)convertView.findViewById(R.id.biAddress);
        Bank bank = mData.get(position);

        String addressText = bank.getBranchaddress1()+", "+bank.getBranchaddress2()+", "+bank.getBranchcity();

        name.setText(bank.getBranchname());
        address.setText(addressText);

        return convertView;
    }
}
