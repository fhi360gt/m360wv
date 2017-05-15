
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

public class Reach_pg_le_4 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_le_4, container, false);

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
                "SELECT \"No one observes the teacher's reading lessons\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q14\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"No one formally evaluates the teacher's performance\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q15\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not receive ongoing professional support\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q16\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not have a designated time set aside to engage in professional development activities\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q17\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers have not attended any inservice training or professional development workshops or seminars in the past year\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q18\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers have not received training from World Vision\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q19\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers attended professional development workshops, but the content was not about reading instruction\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q23\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers would like more training on teaching techniques\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q24\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers would like more training on classroom management\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q24\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers would like more training on language skills\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q24\" AND CAST(result AS INTEGER) = 3 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers would like more training on reading skills\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q24\" AND CAST(result AS INTEGER) = 4 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not have time during the school week to work on lesson planning and preparation\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q33\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not work with other teachers on lesson plans\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q34\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers have not received any training related to student assessment\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q60\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
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