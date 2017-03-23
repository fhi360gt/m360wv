package com.org.fhi360.m360wv.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.org.fhi360.m360wv.R;

import java.util.List;

/**
 * Created by jalfaro on 5/18/15.
 */
public class ODKFormsAdapter extends ArrayAdapter<ODKForms> {
    private Context context;
    private int layout;
    private List<ODKForms> data;
    public ODKFormsAdapter(Context context, int resource, List<ODKForms> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v==null) {
            LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = lf.inflate(layout, null);
        }
        if (data.get(position) != null) {
            TextView text = (TextView) v.findViewById(R.id.text1);
            text.setText(data.get(position).getDisplayName());
        }
        return v;
    }
}
