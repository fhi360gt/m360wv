package com.org.fhi360.m360wv.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.org.fhi360.m360wv.mysql.Conexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jalfaro on 2/27/15.
 */
public class JSONParser2DB {
    private static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ArrayList<String> tables;
    ArrayList<ArrayList<String>> columns;
    ArrayList<ArrayList<String>> values;
    ArrayList<Integer> nofilas;

    public JSONParser2DB(String jsonString) {
        JSONObject json, rows;
        JSONArray table;
        ArrayList<String> tempRow;
        ArrayList<String> tempColumns;
        tables = new ArrayList<String>();
        nofilas = new ArrayList<Integer>();
        columns = new ArrayList<ArrayList<String>>();
        values = new ArrayList<ArrayList<String>>();
        try {
            json= new JSONObject(jsonString);
            Iterator<String> tablesIterator = json.keys();
            while (tablesIterator.hasNext()) {
                tables.add(tablesIterator.next());
            }
            for (int i = 0; i < tables.size();i ++) {
                table = json.getJSONArray(tables.get(i));
                Iterator<String> iteratorColumns = table.getJSONObject(0).keys();
                tempColumns = new ArrayList<String>();
                while(iteratorColumns.hasNext()) {
                    tempColumns.add(iteratorColumns.next());
                }
                columns.add(tempColumns);
                nofilas.add(table.length());
                for (int k =0; k < table.length();k++) {
                    tempRow = new ArrayList<String>();
                    for (int j = 0; j < tempColumns.size(); j++) {
                        tempRow.add(table.getJSONObject(k).getString(tempColumns.get(j)));
                    }
                    values.add(tempRow);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void createAndSaveDB(Context ctx) {
        Conexion cnAnalytics = new Conexion(ctx, STATICS_ROOT + File.separator + "analytics.db",null,1);
        SQLiteDatabase dbAnalytics = cnAnalytics.getWritableDatabase();
        String query;
        //Creacion de tablas
        for (int i = 0; i< tables.size(); i++) {
            query = "CREATE TABLE IF NOT EXISTS " + tables.get(i) + " ( ";
            for (int j = 0; j < columns.get(i).size(); j ++) {
                query = query + columns.get(i).get(j) + " TEXT";
                if (j< columns.get(i).size()-1) {
                    query = query +",";
                }
            }
            query = query + ");";
            Log.d("CREATE",query);
            dbAnalytics.execSQL(query);
        }
        //Insercion de datos
        int t = 0;
        int suma = nofilas.get(t);
        for (int i = 0; i < values.size(); i ++) {
            query = "INSERT INTO " + tables.get(t) + " (";
            for (int j = 0; j < columns.get(t).size(); j++) {
                query = query + columns.get(t).get(j);
                if (j < columns.get(t).size()-1) {
                    query = query + ",";
                }
            }
            query = query + ") VALUES (";
            for (int j = 0; j <  values.get(i).size(); j++) {
                query = query +"\""+ values.get(i).get(j) +"\"";
                if (j < values.get(i).size() - 1) {
                    query = query + ",";
                }
            }
            query=query + ")";
            Log.d("INSERT",query);
            dbAnalytics.execSQL(query);
            if (i == suma -1 ) {
                if (suma  != values.size() ) {
                    t++;
                    suma += nofilas.get(t);
                }
            }
        }


        dbAnalytics.close();
    }

    public void print() {
        int a = 1;
        Log.d("COLS",columns.toString());
        Log.d("VALUES",values.toString());
        Log.d("Tables",tables.toString());
        Log.d("No filas",nofilas.toString());
    }


}
