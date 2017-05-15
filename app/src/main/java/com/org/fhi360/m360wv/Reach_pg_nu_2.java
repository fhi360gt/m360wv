
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

public class Reach_pg_nu_2 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_nu_2, container, false);

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
                "SELECT \"The food preparation area should be kept clean\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q34\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Eliminate flies near the food in the food preparation area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q35\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Provide a hand washing facility close to the food preparation area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q36\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Toilets should be at least 50 meters away from the food preparation area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q37\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Fuel should be provided for cooking\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q38\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Utensils should be available for food preparation\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q39\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Containers should be used to put the food in before serving\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q40\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There should be separate spaces for preparing raw food and cooked food\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q41\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Eliminate rodents from food storage area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q42\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Clean the food storage area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q43\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Food storage area is too warm and humid\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q44\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Repair broken windows in food storage area\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q45\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Is the food stored above the ground on shelves?\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q46\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Is there a working refrigerator in use?\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q47\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School could start a school garden\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[5].toString() + "\" AND var_form = \"q30\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Foods grown in the school garden could be used for school meals\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q106\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Foods grown in the school garden could be used for class-based cooking demonstrations\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q107\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Foods grown in the school garden could be used for home food baskets\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q108\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
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