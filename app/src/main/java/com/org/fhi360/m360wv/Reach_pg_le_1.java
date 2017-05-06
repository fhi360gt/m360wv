
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

public class Reach_pg_le_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;
    SharedPreferences p;
    String school_code;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_le_1, container, false);

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
                "SELECT \"There is no official language that teacher's are required to use for instruction\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q3\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT (\"The official language that teacher's are required to use for instruction is  $\" || result) AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q4\"  "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT (\"Students learn to read in ${\" || result) AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q6\"  "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students primarily speak the same language teachers are required to use for instruction\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students primarily speak the same language they learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students primarily speak a language different than the one teachers are required to use or the one they learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 3 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students primarily speak a language different than the one teachers are required to use or the one they learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 4 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Not all teachers speak the official language of instruction\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q52\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Not all teachers speak the language students learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q57\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Kindergarten teachers can speak the official language of instruction\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q45\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q53\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 1 teachers can speak the official language of instruction\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q46\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q54\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 2 teachers can speak the official language of instruction\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q47\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q55\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 3 teachers can speak the official language of instruction\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q48\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q56\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Kindergarten teachers can speak the language students learn to read in\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q45\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q58\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 1 teachers can speak the language students learn to read in\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q46\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q59\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 2 teachers can speak the language students learn to read in\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q47\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q60\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) > CAST(b.b as FLOAT)) THEN \" Only $\" || a.a || \" out of $\" || b.b || \" Primary 3 teachers can speak the language students learn to read in\" || b.b END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q48\" "+  stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q61\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT \"Texts used to teach reading are not in the same language that students are expected to learn\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q39\" AND CAST(result AS INTEGER) <> 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Assessments are not given in the same language students learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q62\" AND CAST(result AS INTEGER) <> 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Letter tiles, letter flash cards, or word cards are not in the same language students learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q129\" AND CAST(result AS INTEGER) <>2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Posters are not in the same language students learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q130\" AND CAST(result AS INTEGER) <>2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Supplementary materials are not in the same language students learn to read in\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q131\" AND CAST(result AS INTEGER) <>2 "+  stringFilter + "\n" +
                ") WHERE INDICATOR <> 0 GROUP BY indicator ORDER BY indicator";

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