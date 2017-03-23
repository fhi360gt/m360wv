package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.utils.CopyAssetDBUtility;

import java.io.File;

/**
 * Created by George on 16/06/2015.
 */
public class REACHActivity extends Activity implements View.OnClickListener {
    //private TextView btn_exit;  btn_collect;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private final static String DB_INDICATORS_NAME = "indicators.db";
    private final static String DB_ANALYTICS_NAME = "analytics.db"; // esta base de datos se copia temporalmente, solo para mostrar INDICADORES
    private DBAnalyticsUtils cnAnalytics; // agregue esto
    private ImageButton btn_start, btn_exit, btn_analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect360_layout);

        //btn_exit = (TextView) findViewById(R.id.btn_exit);
        //btn_collect = (TextView) findViewById(R.id.btn_collect);
        btn_exit = (ImageButton) findViewById(R.id.btn_exit);
        btn_start = (ImageButton) findViewById(R.id.btn_start);
        //btn_analytics = (ImageButton) findViewById(R.id.btn_analytics);

        btn_exit.setOnClickListener(this);
        btn_start.setOnClickListener(this);
       // btn_analytics.setOnClickListener(this);

        cnAnalytics = new DBAnalyticsUtils(this);
        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        CopyAssetDBUtility.copyDB(this, DB_ANALYTICS_NAME);

        Toast.makeText(getApplicationContext(), "Loading indicators database... ", Toast.LENGTH_SHORT).show();

        // Conexion Load indicators to Analytics database
        cnAnalytics.deleteIndicators();

        // Conexion to Indicators Database for Load Indicators and Index
        Conexion cnind = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "indicators.db", null, 4);
        SQLiteDatabase dbind = cnind.getWritableDatabase(); // aqui debe ser solo lectura?

        Cursor cursor = dbind.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators", null);

        cnAnalytics.insertIndicatorsFromCursor(cursor);

        cnind.close();
        dbind.close();
        cursor.close();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                //Toast.makeText(getApplicationContext(),"Ha selecionado salir",Toast.LENGTH_SHORT).show();
                System.out.println("CloseApplication");
                this.finish();
                break;
            case R.id.btn_start:
                //Toast.makeText(getApplicationContext(),"Ha desidido continuar con la Aplicacion",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this, CollectActivity.class));
                //Intent intent1 = new Intent(REACHActivity.this, CollectSelectActivityNEW.class);
                Intent intent1 = new Intent(REACHActivity.this, MenuREACH_start.class);
//                Bundle gi1 = new Bundle();
//                gi1.putString("groupIndicator", "Forms");
//                intent1.putExtras(gi1);
                startActivity(intent1);
                break;
            //case R.id.btn_analytics:
                //Toast.makeText(getApplicationContext(),"Ha desidido continuar con la Aplicacion",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(this,MainActivity.class)); pendiente para mostrar graficas
              //  break;
        }

    }
}
