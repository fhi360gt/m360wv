package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.org.fhi360.m360wv.data.ODKForms;
import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.mysql.DBFormsUtils;
import com.org.fhi360.m360wv.utils.CopyAssetDBUtility;

import java.io.File;
import java.util.List;

/**
 * Created by jalfaro on 5/17/15.
 */
public class MenuActivity extends Activity implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private final static String DB_INDICATORS_NAME = "indicators.db";
    private ImageView selCollect, selIndicators;
    private TextView btnCollect, btnIndicators, btn_back, btn_sync; // agregue el boton sync
    private LinearLayout title;
    private ListView lstIndicator;
    private int isShow = 0;
    private DBFormsUtils conn;
    private final static String FORM_URI = "content://org.odk.collect.android.provider.odk.forms/forms/";
    private List<ODKForms> formsList;
    private DBAnalyticsUtils cnAnalytics; // agregue esto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        conn = new DBFormsUtils(this);

        cnAnalytics = new DBAnalyticsUtils(this);

        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        //title = (LinearLayout) findViewById(R.id.layout_title_menu);
        //lstIndicator = (ListView) findViewById(R.id.listShowOptionsMenu);
        //selCollect =(ImageView) findViewById(R.id.selCollect);
        //selIndicators = (ImageView) findViewById(R.id.selIndicators);
        btnCollect =(TextView) findViewById(R.id.Collect);
        btnIndicators = (TextView) findViewById(R.id.Indicators);
        btn_back = (TextView) findViewById(R.id.btn_back);
        btn_sync = (TextView) findViewById(R.id.btn_sync);
        btnCollect.setOnClickListener(this);
        btnIndicators.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_sync.setOnClickListener(this);
        setSelector(8);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Collect :
//                if (isShow != 1) {
//                    setSelector(1);
//                    formsList = conn.getForms();
//                    lstIndicator.setAdapter(new ODKFormsAdapter(MenuActivity.this, R.layout.row_menu_item_1, formsList));
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            openFormId(formsList.get((int) id).get_id());
//                        }
//                    });
//                    isShow = 1;
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                //startActivity(new Intent(this,CollectActivity.class));
                startActivity(new Intent(this,CollectSelectActivityNEW.class));
                break;
            case R.id.Indicators :
                setSelector(8);
                isShow = 0;
                // Para esta version se ha desactivado este menu, pero luego se debe activar.
                //startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_sync :
                Toast.makeText(getApplicationContext(),"Loading indicators database... ",Toast.LENGTH_SHORT).show();
                cnAnalytics.deleteIndicators();
                // Conexion to Indicators Database for Load Indicators and Index
                Conexion cnind = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "indicators.db", null, 4);
                SQLiteDatabase dbind = cnind.getWritableDatabase(); // aqui debe ser solo lectura?

                Cursor cursor = dbind.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators", null);

                cnAnalytics.insertIndicatorsFromCursor(cursor);

                cnind.close();
                dbind.close();
                cursor.close();
                break;
            case R.id.btn_back :
                System.exit(0);
                break;
        }
    }

    public void openFormId(long formId) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.odk.collect.android");
        launchIntent.setClassName("org.odk.collect.android", "org.odk.collect.android.activities.FormEntryActivity");
        launchIntent.setAction(Intent.ACTION_EDIT);
        launchIntent.setData(Uri.parse(FORM_URI + formId));

        startActivity(launchIntent);
    }

    public void setSelector(int selector) {
        //title.setVisibility(View.GONE);
        //lstIndicator.setVisibility(View.VISIBLE);
        switch (selector) {
            case 1:
                //selCollect.setVisibility(View.VISIBLE);
                //selIndicators.setVisibility(View.INVISIBLE);
                break;
            case 2:
                //selCollect.setVisibility(View.VISIBLE);
                //selIndicators.setVisibility(View.INVISIBLE);
                break;
            case 3:
                System.exit(0);
                break;
            default:
                //title.setVisibility(View.VISIBLE);
                //lstIndicator.setVisibility(View.GONE);
                //selCollect.setVisibility(View.INVISIBLE);
                //selIndicators.setVisibility(View.INVISIBLE);
        }
    }
}
