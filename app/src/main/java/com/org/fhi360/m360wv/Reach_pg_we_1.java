
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

public class Reach_pg_we_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_we_1, container, false);

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
                "SELECT \"No latrines for students\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q15\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"No separate latrines for teachers and students\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q17\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"No separate latrines for boys and girls\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q18\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"No door or curtain for privacy\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q19\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Latrine door can't be locked for privacy\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q20\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"No wiping material available in latrine\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q21\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Latrine smells bad\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q22\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Feces visible around the school compound\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q23\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There is no person assigned to maintaining the toilet facility\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q83\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There is no person assigned to cleaning the toilet facility\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q84\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There is no budget for the maintenance of the latrine\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q85\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students should be allowed to use the latrine when they need to\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q86\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Latrines need to be cleaned more frequently\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q87\" AND CAST(result AS INTEGER) > 4 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Parents can be asked to help clean the latrine\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q146\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students are not allowed to use the toilets during class\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q80\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not use available toilets\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q81\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students defecate or pee in the open\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[6].toString() + "\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
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