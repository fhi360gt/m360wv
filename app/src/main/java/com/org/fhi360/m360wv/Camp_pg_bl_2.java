
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

public class Camp_pg_bl_2 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.camp_pg_bl_2, container, false);

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
                "SELECT \"Not all steps of the reading camp curriculum were not followed\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q60\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Low attendance  (could be overall, for girls / boys or a particular grade)\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q79\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"High attendance\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q80\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Space for the session was not safe\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q66\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Children had insufficient access to books \" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q75\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Poor time management\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q84\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lack of parental/ community support for Reading Camps\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q81\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Space for the session was not comfortable  (eg very hot)\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q68\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Materials with print were not displayed during the session\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q69\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Not enough *fun* activities were used\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q83\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Facilitator did not engage boys and girls equally\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q85\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Space for the session had too many distractions from the surrounding environment\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q93\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Some or all children did not have materials for journalling\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q76\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Facilitator needs utilise different strategies / approaches to encourage children's higher order reading comprehension skills\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lack of materials for make & take\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q78\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There was no record of books being borrowed or returned\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q74\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lack of pens/pencils\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q77\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Text typed by observer on challenges observed is displayed here\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Camp\" AND var_form = \"q86\"  "+  stringFilter + "\n" +
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