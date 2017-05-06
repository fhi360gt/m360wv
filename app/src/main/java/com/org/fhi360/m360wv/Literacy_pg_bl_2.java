
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

public class Literacy_pg_bl_2 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.literacy_pg_bl_2, container, false);

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
                "SELECT \"The teacher did not use a lesson plan\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q70\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not encourage individual participation\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q65\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not have good content knowledge of Literacy Boost topics\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q77\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not use good time management\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not engage boys and girls equally\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q83\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not use a variety of teaching methods\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q64\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not use good classroom management\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q81\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not provide verbal reinforcement to learners\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q66\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher was not well prepared for the lesson\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q71\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not give students an opportunity to practice the new skill\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q68\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not monitor students' performance\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q69\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"The teacher did not ask varied questions\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q67\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Text typed by observer on challenges observed is displayed here\" AS indicator FROM  tblresults WHERE source_form =\"WV_Boost_Lesson\" AND var_form = \"q84\"  "+  stringFilter + "\n" +
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