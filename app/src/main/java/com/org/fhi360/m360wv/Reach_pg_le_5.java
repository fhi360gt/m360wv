
package com.org.fhi360.m360wv;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.org.fhi360.m360wv.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Sergio, Pablo y Jorge... jajajaaj on 3/1/2016. cambio de pagina.... uso uso del DEDITO
 */

public class Reach_pg_le_5 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_le_5, container, false);

        lv_show_indicadores = (ListView) mLinearLayout.findViewById(R.id.lv_show_indicator);

        p = PreferenceManager.getDefaultSharedPreferences(getContext());
        school_code = p.getString("schoolcode", "");

        load_indicadores();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), R.layout.row_listview, indicadores);
        lv_show_indicadores.setAdapter(adaptador);


        return mLinearLayout;
    }



    private void load_indicadores () {
        String stringFilter="";
        if (!school_code.equals("")){stringFilter = " AND school_code=\""+school_code+"\""; }
        Conexion cnfhi360 = new Conexion(getActivity(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT \"Teachers do not feel aware of student progress in the classroom throughout the school year\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not keep mental track of student progress (through circulating the room, listening to students' answers in large groups, small groups or individually)\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q53\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not grade classroom exercises as a whole group (e.g. students exchange papers and review one another's work)\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q54\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not collect students' work for later review\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q55\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not record student progress in a notebook or gradebook\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q56\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not provide extra support to students who appear to be struggling\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q57\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers have not received any training related to student assessment\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q60\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students are not given formal exams to assess their reading abilities\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q59\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Student progress is not reported to parents\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q58\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Parents do not have access to the results of reading assessments\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q148\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                ")   GROUP BY indicator ORDER BY indicator";

        Cursor cursor_indicadores = dbfhi360.rawQuery(sql,null);
        indicadores = new ArrayList<String>();
        if(cursor_indicadores.moveToFirst()) {
            do {
                //if (cursor_indicadores.getString(0).length()>0) {indicadores.add(cursor_indicadores.getString(0));}
                indicadores.add(cursor_indicadores.getString(0));
            } while (cursor_indicadores.moveToNext());
        }
        cursor_indicadores.close();
        dbfhi360.close();
        cnfhi360.close();
    }

}