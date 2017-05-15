
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

public class Literacy_pg_bl_3 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.literacy_pg_bl_3, container, false);

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
                "SELECT \"No lesson plan: Have a prepared lesson plan that is written up in the agreed upon structure (either MoE or Teacher Training) that is accessible during the class\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q70\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Inadequate preparation for the lesson: Focus on sequencing of lesson stages and materials required, as articulated in the lesson plan\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q71\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Gaps in content knowledge of LB topics: Observer should provide coaching/guidance to address and knowledge gaps based on relevant teacher training topics\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q77\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Student Learning Materials – textbooks, notebooks, pens/paper, books: for textbooks – raise issue with school director or district education office; pens/paper,books – recommend strategies for engaging parents to ensure children have required materials\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q73\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teaching Materials – Chalk/chalkboard, posters, charts, props, books: refer back to training on locally developed teaching and learning materials and strategies the teacher can employ in the classroom\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q74\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Misalignment of teaching strategy for topic:  ask teacher to reflect upon the component of the lesson with misaligned strategy/approach to topic. Encourage teacher to explore other methods of delivering that topic, providing suggestions as appropriate\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q79\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Poor classroom management:  established classroom rules/code of conduct visible and know by students; student awareness of consequences; consistent enforcement of classroom rules/code of conduct; ensure that lessons are keep child engaged (provide positive examples to enhance engagement)\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q81\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Wasted time (Periods of zero time on task / complete non-engagement): Question teaching and learning strategies that are not effectively engaging children – provide positive examples to enhance engagement \" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q82\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Unplanned multigrade classes (due to teacher absenteeism): Check with teacher if this is regular; if so work with teacher to identify strategies to overcome\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q76\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Large class sizes:  discuss reason behind large class size – a few options – (1) high enrollment due to high levels of repetition or late enrollment; (2) teacher absenteeism that leads to multigrade classes (Discuss with teacher if there are any ideas for how to overcome these issues) Issue should be raised with school director or district education office\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q75\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Desks/Chairs/Floor Mats:  issue should be raised with school director or district education office\" AS indicator FROM  tblresults WHERE source_form =\""+ main_v3.listForms[0].toString() + "\" AND var_form = \"q72\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
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