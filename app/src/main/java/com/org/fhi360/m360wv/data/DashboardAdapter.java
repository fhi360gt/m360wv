package com.org.fhi360.m360wv.data;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.org.fhi360.m360wv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 5/6/15.
 */
public class DashboardAdapter extends ArrayAdapter<ArrayList<String>> {
    private Context context;
    private int layout;
    private List<ArrayList<String>> data;
    private TextView primero, segundo, tercero;

    public DashboardAdapter(Context context, int resource, List<ArrayList<String>> objects) {
        super(context, resource, objects);
        this.context = context;
        layout = resource;
        data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(layout,null);
        }
        if (data.get(position) != null) {
            primero = (TextView)v.findViewById(R.id.txt1);
            segundo = (TextView)v.findViewById(R.id.txt2);
            tercero = (TextView)v.findViewById(R.id.txt3);
            setRowColor(Color.WHITE);

            if (position == 0) {
                setRowColor(Color.parseColor("#F47321"));
                primero.setTextColor(Color.WHITE);
                segundo.setTextColor(Color.WHITE);
                tercero.setTextColor(Color.WHITE);
            }
            if (data.get(position).get(2).equals("Excellent")) {
                setRowColor(Color.rgb(6,173,85));
            } else if (data.get(position).get(2).equals("Good")) {
                setRowColor(Color.rgb(255,169,9));
            } else if (data.get(position).get(2).equals("Bad")) {
                setRowColor(Color.parseColor("#FF3300"));
            } else if (data.get(position).get(2).equals("Regular")) {
                setRowColor(Color.rgb(255,234,160));
            }
            primero.setText(data.get(position).get(0));
            segundo.setText(data.get(position).get(1));
            tercero.setText(data.get(position).get(2));
        }
        return v;
    }

    private void setRowColor(int color) {
        primero.setBackgroundColor(color);

        segundo.setBackgroundColor(color);

        tercero.setBackgroundColor(color);

    }
}
