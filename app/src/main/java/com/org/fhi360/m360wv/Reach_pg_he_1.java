
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

public class Reach_pg_he_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_he_1, container, false);

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
                "SELECT \"School does not have a budget line allocated to health and nutrition services\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q118\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: First aid\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q120\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Routine/mass deworming\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q121\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Deworming based on identified need\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q122\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Intermittent malaria prevention\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q123\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Malaria treatment based on identified need\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q124\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: ORS for diarrhea\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q125\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Iron supplements\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q126\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Iodine supplements\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q127\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Vitamin A\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q128\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Vision screening & referrals as needed\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q129\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Measurements of height & weight\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q130\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School does not provide: Referrals to community services for students who are identified as in need of: nutrition support (malnutrition AND obesity), vision correction, or other interventions\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[2].toString() + "\" AND var_form = \"q131\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
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