
package com.org.fhi360.m360wv;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.org.fhi360.m360wv.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;

import static com.org.fhi360.m360wv.main.school_code;

/**
 * Created by Sergio, Pablo y Jorge... jajajaaj on 3/1/2016. cambio de pagina.... uso uso del DEDITO
 */

public class Reach_pg_sm_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_sm_1, container, false);

        lv_show_indicadores = (ListView) mLinearLayout.findViewById(R.id.lv_show_indicator);

        p = PreferenceManager.getDefaultSharedPreferences(getContext());
        school_code = p.getString("schoolcode", "");

        load_indicadores();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), R.layout.row_listview, indicadores);
        lv_show_indicadores.setAdapter(adaptador);


        return mLinearLayout;
    }



    private void load_indicadores () {
        String stringFilter=school_code;
        if (!school_code.equals("")){stringFilter = " AND school_code=\""+school_code+"\""; }
        Conexion cnfhi360 = new Conexion(getActivity(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT \"There were unplanned school closures\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q15\" AND CAST(result AS INTEGER) > 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers not hired on time\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q11\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers not paid on time\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q8\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  || \" % teachers absent this week\", 0) AS indicator   FROM\n" +
                "( SELECT \"1\" as ident, SUM(RESULT)/5 AS a   FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form in  (\"q76\",\"q77\",\"q78\",\"q79\",\"q80\") " + stringFilter + ") AS a\n" +
                "JOIN (SELECT \"1\" as ident,  SUM(RESULT) AS b  FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form in  (\"q45\",\"q46\",\"q47\",\"q48\",\"q49\",\"q50\",\"q51\") " + stringFilter + " )  AS b ON  a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  || \" % of students absent this week\", 0) AS indicator FROM\n" +
                "(SELECT \"1\" as ident, SUM(RESULT)/5 AS a  FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form in  (\"q67\",\"q68\",\"q69\",\"q70\",\"q71\") " + stringFilter + ") AS a\n" +
                "JOIN( SELECT \"1\" as ident,   SUM(RESULT) AS b  FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form in  (\"q65\") " + stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT \"Teacher does not have record book\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q56\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teacher does not have attendance record\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q64\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not have functional PTA\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q143\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There is not adult monitoring the school grounds\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[4].toString() + "\" AND var_form = \"q14\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                ") WHERE indicator <> 0 ORDER BY indicator";

        Cursor cursor_indicadores = dbfhi360.rawQuery(sql,null);
        indicadores = new ArrayList<String>();
        if(cursor_indicadores.moveToFirst()) {
            do {
                indicadores.add(cursor_indicadores.getString(0));
            } while (cursor_indicadores.moveToNext());
        }
        cursor_indicadores.close();
        dbfhi360.close();
        cnfhi360.close();
    }

}