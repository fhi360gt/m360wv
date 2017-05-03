package com.org.fhi360.m360wv;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jlgarcia on 03/05/2017.
 */

public class SchoolCodeItemAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> datos;
    private int layout;
    private String defaultValue;


    public SchoolCodeItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, String defaultValue) {
        super(context, resource, objects);
        this.context = context;
        this.datos = objects;
        this.layout = resource;
        this.defaultValue = defaultValue;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(layout, null);
        }
        if (datos.get(position) != null) {
            ImageView checked = (ImageView) v.findViewById(R.id.imgSelected);
            TextView schoolcode = (TextView) v.findViewById(R.id.txtSchoolCode);
            checked.setImageResource(R.drawable.ic_blank);
            if (datos.get(position).equals(defaultValue)) {
                checked.setImageResource(R.drawable.ic_check);
            }
            schoolcode.setText(datos.get(position));
        }
        return v;
    }
}
