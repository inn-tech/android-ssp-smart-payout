package com.system.itl.ssp_spo;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtForth;


    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.listview_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.FirstText);
            txtSecond=(TextView) convertView.findViewById(R.id.SecondText);
            txtThird=(TextView) convertView.findViewById(R.id.ThirdText);
            txtForth = (TextView)convertView.findViewById(R.id.ForthText);
        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(Constants.FIRST_COLUMN));
        txtSecond.setText(map.get(Constants.SECOND_COLUMN));
        txtThird.setText(map.get(Constants.THIRD_COLUMN));
        txtForth.setText(map.get(Constants.FOURTH_COLUMN));

        return convertView;
    }

}

