
package com.org.fhi360.m360wv;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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

public class Reach_pg_sm_2 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_sm_2, container, false);

        lv_show_indicadores = (ListView) mLinearLayout.findViewById(R.id.lv_show_indicator);

        load_indicadores();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), R.layout.row_listview, indicadores);
        lv_show_indicadores.setAdapter(adaptador);


        return mLinearLayout;
    }



    private void load_indicadores () {
        String stringFilter="";
        if (!main_v2.school_selected.equals("")){stringFilter = " AND school_code=\""+main.school_selected+"\""; }
        Conexion cnfhi360 = new Conexion(getActivity(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) >CAST(b.b as FLOAT)) THEN \"School opened late\" END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q3\" " + stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident,   SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q2\" " + stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT \"Students arrived late\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q4\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers arrived late\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q5\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School director arrived late\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q6\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "select case when (b.result_t-a.result_t>c.result_t-a.result_t) then \"School Recess was too long\" else 0 END as indicator  from\n" +
                "(select \"1\" as id, julianday(substr(result,1,2)||\":\"||substr(result,4,2)||\":\"||substr(result,7,2)) *24*60 as result_t from  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q5\" " + stringFilter + ") as a\n" +
                "join (select \"1\" as id, julianday(substr(result,1,2)||\":\"||substr(result,4,2)||\":\"||substr(result,7,2)) *24*60 as result_t from  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q6\" " + stringFilter + ") as b on (a.id=b.id)\n" +
                "join (select \"1\" as id, julianday(substr(result,1,2)||\":\"||substr(result,4,2)||\":\"||substr(result,7,2)) *24*60 as result_t from  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q7\" " + stringFilter + ") as c on (a.id=c.id) \n" +
                "UNION\n" +
                "SELECT \"Students left early\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q2\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Teachers left early\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q3\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"School director leaves early\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q4\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT  IFNULL((CASE WHEN (CAST(a.a as FLOAT) >CAST(b.b as FLOAT)) THEN \"School Ended Early\" END), 0) AS indicator FROM\n" +
                " (SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q8\" " + stringFilter + ") AS a\n" +
                " JOIN( SELECT \"1\" as ident,   SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_School_2\" AND var_form = \"q9\" " + stringFilter + ") AS b ON a.ident=b.ident \n" +
                ") WHERE indicator <> 0 GROUP BY indicator ORDER BY indicator";

        Cursor cursor_indicadores = dbfhi360.rawQuery(sql,null);
        indicadores = new ArrayList<String>();
        if(cursor_indicadores.moveToFirst()) {
            do {
                indicadores.add(cursor_indicadores.getString(0));
            } while (cursor_indicadores.moveToNext());
        }
        cursor_indicadores.close();
        dbfhi360.close();
        cnfhi360.close();
    }

}