
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

public class Camp_pg_bl_3 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.camp_pg_bl_3, container, false);

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
                "SELECT \"Gaps in content knowledge of Reading Camp activities:  Observer should provide coaching/guidance, referring to the main points from the training manual related to the area the facilitator is demonstrating gaps in knowledge\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Wasted time (Periods of zero time on task / complete non-engagement of children): Ask the facilitator about the times when children weren't engaged and why they thought that happened. Move discussion to identifying positive examples to enhance engagement of children. Refer to the checklist for teaching and learning strategies\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q84\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Many children in attendance (greater than 35 children):  discuss reasons so many children attending and engage the facilitator to identify solutions including adding another session each week, splitting off ECCD children to a separate group, and get the SMC/school director support\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q80\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teaching/learning Materials – Chalk/chalkboard, posters,  journals, etc: refer back to training on locally developed teaching and learning materials and strategies the facilitator can employ to replenish or access materials for the reading club sessions\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q70\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teaching/learning Materials – Chalk/chalkboard, posters,  journals, etc: refer back to training on locally developed teaching and learning materials and strategies the facilitator can employ to replenish or access materials for the reading club sessions\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q76\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teaching/learning Materials – Chalk/chalkboard, posters,  journals, etc: refer back to training on locally developed teaching and learning materials and strategies the facilitator can employ to replenish or access materials for the reading club sessions\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q77\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teaching/learning Materials – Chalk/chalkboard, posters,  journals, etc: refer back to training on locally developed teaching and learning materials and strategies the facilitator can employ to replenish or access materials for the reading club sessions\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q78\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Inadequate preparation for the reading club session: Focus on sequencing of session stages, timing, connecting theme around topic and core literacy skills and materials required\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q61\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Inadequate preparation for the reading club session: Focus on sequencing of session stages, timing, connecting theme around topic and core literacy skills and materials required\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q61\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Access to books –  Ask an open question about the lack of access to books in the session. Issues could be no presence of the book bank, books are damaged, facilitator not enabling access to available titles or books are not usable or children have read all the titles. Recommend strategies based upon the problem\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q75\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Suggestions typed by observer are displayed here\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[1].toString() + "\" AND var_form = \"q58\"  "+  stringFilter + "\n" +
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