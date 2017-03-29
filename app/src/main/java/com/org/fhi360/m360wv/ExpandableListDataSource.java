package com.org.fhi360.m360wv;

import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jalfaro on 3/29/17.
 */

public class ExpandableListDataSource {
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> opciones = Arrays.asList(context.getResources().getStringArray(R.array.opciones));

        List<String> opcion1 = Arrays.asList(context.getResources().getStringArray(R.array.opcion1));
        List<String> opcion2 = Arrays.asList(context.getResources().getStringArray(R.array.opcion2));
        List<String> opcion3 = Arrays.asList(context.getResources().getStringArray(R.array.opcion3));
        List<String> opcion4 = Arrays.asList(context.getResources().getStringArray(R.array.opcion4));
        List<String> opcion5 = Arrays.asList(context.getResources().getStringArray(R.array.opcion5));

        expandableListData.put(opciones.get(0), opcion1);
        expandableListData.put(opciones.get(1), opcion2);
        expandableListData.put(opciones.get(2), opcion3);
        expandableListData.put(opciones.get(3), opcion4);
        expandableListData.put(opciones.get(4), opcion5);

        return expandableListData;
    }
}
