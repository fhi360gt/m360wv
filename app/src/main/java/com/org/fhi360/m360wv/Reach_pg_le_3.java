
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

public class Reach_pg_le_3 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_le_3, container, false);

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
                "SELECT \"Teachers are not confident in their teaching ability\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q13\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not have a lesson plan\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q31\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The lesson observed did not follow the plan\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q32\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Reading is only taught one day a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q35\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Reading is only taught twice a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q35\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not teach the letters of the alphabet\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q40\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not help students sound out words\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q41\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not read texts aloud to students\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q42\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not ask students to read texts aloud\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q43\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not ask students to read text independently that they have not seen or heard previously\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q44\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers do not ask students to write about something they have read\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q45\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not have the opportunity to read outside of school\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q46\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers only assign reading homework once a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q49\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers only assign reading homework twice a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q49\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers only assign writing homework once a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q50\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers only assign writing homework twice a week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q50\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
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