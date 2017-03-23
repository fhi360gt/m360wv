
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

public class Reach_pg_le_2 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_le_2, container, false);

        lv_show_indicadores = (ListView) mLinearLayout.findViewById(R.id.lv_show_indicator);

        load_indicadores();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), R.layout.row_listview, indicadores);
        lv_show_indicadores.setAdapter(adaptador);


        return mLinearLayout;
    }



    private void load_indicadores () {
        String stringFilter="";
        if (!main.school_selected.equals("")){stringFilter = " AND school_code=\""+main.school_selected+"\""; }
        Conexion cnfhi360 = new Conexion(getActivity(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?
        String sql = "SELECT  indicator FROM (\n" +
                "SELECT \"There is no school library\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q7\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students were not using the library at the time of the visit\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q8\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There is no record of books being borrowed\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_School_1\" AND var_form = \"q9\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  ||  \" Kindergarten students share each available reading textbook\", 0)  AS indicator FROM\n" +
                "(SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q41\" "+  stringFilter + ") AS a\n" +
                "JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q29_t\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  ||  \" Primary 1 students share each available reading textbook\",0)  AS indicator FROM\n" +
                "(SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q42\" "+  stringFilter + ") AS a\n" +
                "JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q30_t\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  ||  \" Primary 2 students share each available reading textbook\", 0)  AS indicator FROM\n" +
                "(SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q43\" "+  stringFilter + ") AS a\n" +
                "JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q31_t\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT  IFNULL(ROUND(CAST(a.a as FLOAT)/CAST(b.b as FLOAT)*100,1)  ||  \" Primary 3 students share each available reading textbook\", 0)  AS indicator FROM\n" +
                "(SELECT \"1\" as ident, SUM(result) AS a  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q44\" "+  stringFilter + ") AS a\n" +
                "JOIN( SELECT \"1\" as ident, SUM(result) AS b  FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q32_t\" "+  stringFilter + ") AS b ON a.ident=b.ident \n" +
                "UNION\n" +
                "SELECT \"Students can't take reading books or other resources home from school\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q47\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students don't have paper and pencils available at home\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q48\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"An alphabet chart or alphabet strip is not displayed in the classroom\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q124\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Letter tiles, letter flash cards, or word cards are not available in the classroom\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q125\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Posters are not displayed on the wall in the classroom\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q126\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students' drawings or writing are not displayed on the walls in the classroom\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q127\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"There are not supplementary materials like big books, read-alouds, or student readers available in the classroom\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q128\" AND CAST(result AS INTEGER) = 0"+  stringFilter + "\n" +
                ") WHERE indicator <> 0  GROUP BY indicator ORDER BY indicator";

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