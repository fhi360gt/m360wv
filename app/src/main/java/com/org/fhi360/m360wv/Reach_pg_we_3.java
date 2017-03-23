
package com.org.fhi360.m360wv;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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

public class Reach_pg_we_3 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_we_3, container, false);

        lv_show_indicadores = (ListView) mLinearLayout.findViewById(R.id.lv_show_indicator);

        load_indicadores();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), R.layout.row_listview, indicadores);
        lv_show_indicadores.setAdapter(adaptador);


        return mLinearLayout;
    }



    private void load_indicadores () {
        String stringFilter="";
        if (!main.school_selected.equals("")){stringFilter = " AND school_code=\""+main.school_selected+"\""; }
        Conexion cnfhi360 = new Conexion(getActivity(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT \"School does not have drinking water for students\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q10\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Drinking water is not available in classrooms\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q14\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Drinking water is not treated\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q91\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not bring drinking water from home\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q87\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
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