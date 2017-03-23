package com.org.fhi360.m360wv.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.org.fhi360.m360wv.R;

import java.util.List;

/**
 * Created by George on 08/04/2015.
 */
public class ResultAdapter extends ArrayAdapter<String[]>{
    Context context;
    int layout;
    List<String[]> datos;
    public ResultAdapter(Context context, int resource, List<String[]> objects) {
        super(context, resource, objects);
        this.context = context;
        layout = resource;
        datos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView txt1, txt2, txt3;

        if (v == null) {
            LayoutInflater lf =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(layout,null);
        }
        txt1 = (TextView)v.findViewById(R.id.txt1);
        txt2 = (TextView)v.findViewById(R.id.txt2);
        txt3 = (TextView)v.findViewById(R.id.txt3);
        if (position == 0) {
            txt1.setText("Category");
            txt2.setText(datos.get(3)[0]);
            txt3.setText(datos.get(3)[1]);
            txt1.setBackgroundColor(Color.parseColor("#F47321"));
            txt1.setTextColor(Color.WHITE);
            txt2.setBackgroundColor(Color.parseColor("#F47321"));
            txt2.setTextColor(Color.WHITE);
            txt3.setBackgroundColor(Color.parseColor("#F47321"));
            txt3.setTextColor(Color.WHITE);
            /*
            txt2.setGravity(Gravity.CENTER_HORIZONTAL);
            txt3.setGravity(Gravity.CENTER_HORIZONTAL);
            */
        }
        else if (position <= Integer.parseInt(datos.get(0)[0])){
            txt1.setText(datos.get(1)[position-1]);
            txt2.setText(datos.get(2)[position-1]);
            txt3.setText(datos.get(4)[position-1]);
            txt1.setBackgroundColor(Color.WHITE);
            txt1.setTextColor(Color.BLACK);
            txt2.setBackgroundColor(Color.WHITE);
            txt2.setTextColor(Color.BLACK);
            txt3.setBackgroundColor(Color.WHITE);
            txt3.setTextColor(Color.BLACK);
            /*txt1.setGravity(Gravity.LEFT);
            txt2.setGravity(Gravity.RIGHT);
            txt3.setGravity(Gravity.RIGHT);*/
        }
        return v;
    }
}
