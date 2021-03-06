
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

public class Camp_pg_bl_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.camp_pg_bl_1, container, false);

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
        String strddd = main_v3.listForms[1].toString();
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT \"Encouraged children's higher order reading comprehension skills (provide example)\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator used fun, interactive activities\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q83\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"All children have access to books from the book banks during the reading camp\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q73\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator had children sitting in a way that promoted children's participation\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q59\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator had children sitting in a way that promoted children's participation\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q59\" AND CAST(result AS INTEGER) = 3 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator had good time management\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q84\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator engaged boys and girls equally\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q85\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Materials with print were displayed\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q69\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator has book borrowing registers up to date\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q74\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator was well prepared for the session\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q61\" AND CAST(result AS INTEGER) = 4 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator was well prepared for the session\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q61\" AND CAST(result AS INTEGER) = 5 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The facilitator completed all recommended components for a reading club session\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q60\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Text typed by observer on strengths observed is displayed here\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q87\"  "+  stringFilter + "\n" +
                ")   GROUP BY indicator ORDER BY indicator\n";

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