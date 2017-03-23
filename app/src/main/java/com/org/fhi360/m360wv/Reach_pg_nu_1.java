
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

public class Reach_pg_nu_1 extends Fragment  {
//public class Reach_pg_sm_1 extends Activity implements View.OnClickListener{

    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    ListView lv_show_indicadores;
    ArrayList<String> indicadores;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) { return null;}
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.reach_pg_nu_1, container, false);

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
                "SELECT \"There are students in your class who came to school hungry this past week\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q72\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Most students come to school hungry\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q74\" AND CAST(result AS INTEGER) > 50 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not receive a morning snack at school\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q77\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not bring a morning snack from home\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q79\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not receive a lunch at school each day\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q73\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students do not bring a lunch from home\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q75\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Commodities do not arrive on time to prepare lunch\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q102\" AND CAST(result AS INTEGER) = 1 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Cooks do not come to the school to prepare lunch\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q102\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lunch is not served due to lack of water\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q102\" AND CAST(result AS INTEGER) = 3 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lunch is not served due to lack of cooking fuel\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q102\" AND CAST(result AS INTEGER) = 4 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Lunch is not served due to lack of funding for food\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q102\" AND CAST(result AS INTEGER) = 5 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Students' families do not contribute anything to the school for school meals\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q78\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Boys are served food before girls\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Director\" AND var_form = \"q103\" AND CAST(result AS INTEGER) = 2 "+  stringFilter + "\n" +
                "UNION\n" +
                "SELECT \"Boys and girls are not served the same amount of food\" AS indicator FROM  tblresults WHERE source_form =\"WV_REACH_Teacher\" AND var_form = \"q76\" AND CAST(result AS INTEGER) = 0 "+  stringFilter + "\n" +
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