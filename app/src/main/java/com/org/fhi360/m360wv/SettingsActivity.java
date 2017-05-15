package com.org.fhi360.m360wv;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.utils.XMLParserInsertInformation;
import com.org.fhi360.m360wv.utils.XMLParserschoolcode;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlgarcia on 02/05/2017.
 */

public class SettingsActivity extends AppCompatActivity {
    private ListView lstOpciones;
    private DBAnalyticsUtils conn;
    private SharedPreferences p;
    private SharedPreferences.Editor edit;
    private List<String> schoolCodes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        conn = new DBAnalyticsUtils(this);

        p = PreferenceManager.getDefaultSharedPreferences(this);
        lstOpciones = (ListView) findViewById(R.id.lstSchoolCode);
        schoolCodes =  conn.getAllSchoolCodes();
        lstOpciones.setAdapter(new SchoolCodeItemAdapter(this, R.layout.row_school_code_layout,schoolCodes, p.getString("schoolcode","")));
        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edit = p.edit();
                edit.putString("schoolcode", schoolCodes.get(i));
                edit.commit();
                lstOpciones.setAdapter(new SchoolCodeItemAdapter(SettingsActivity.this, R.layout.row_school_code_layout,schoolCodes, schoolCodes.get(i)));
            }
        });
    }



}
