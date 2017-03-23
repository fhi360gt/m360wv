package com.org.fhi360.m360wv;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.utils.CopyAssetDBUtility;
import com.org.fhi360.m360wv.utils.JSONParser2DB_new;
import com.org.fhi360.m360wv.utils.XMLParserInsertInformation;
import com.org.fhi360.m360wv.utils.XMLParserschoolcode;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by Sergio on 8/23/2016.
 */
public class main extends Activity implements View.OnClickListener {
    public  static String app_proyect = "World Vision";
    public  static String school_selected;
    private final static String DB_INDICATORS_NAME = "indicators.db";
    private final static String DB_ANALYTICS_NAME = "analytics.db"; // esta base de datos se copia temporalmente, solo para mostrar INDICADORES

    public static String xml_path="", xml_res="", xml_form="", jsonSend="", macAdr="";
    public static String school_code = "";
    public static final ArrayList<String> listColum = new ArrayList<String>();
    public static final ArrayList<String> SchoolsCode = new ArrayList<String>();
    public static final ArrayList<String> listSchools = new ArrayList<String>();
    public static int ncolums=0;
    private int isShow = 0;
    private BluetoothSPP bluetoothAdmin;
    public static ContentValues data_ind = new ContentValues();
    public static String[] data_display;
    public int n_ind = 0, contador=0;
    private DBAnalyticsUtils cnAnalytics;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";


    ImageButton btn_infra,btn_base,btn_daily,btn_teacher, btn_sm, btn_le, btn_we, btn_nu, btn_he;
    LinearLayout ll_baseline, ll_dailyClassroom, ll_indicator, ll_collect;
    TextView tv_baseline, tv_dailyclassroom;
    FrameLayout fl0, fl1,fl2,fl3;
    ListView lv_indicator, lv_forms, lv_schools;
    String[] listaIndicadores = new String[] {"Management","Literacy","WASH","Nutrition","Health", "Lesson", "Camp"};
    String[] GrupoFormularios = new String[] {"Literacy Boost","REACH"};
    TextView tv_school_list, tv_school_name;



    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        bluetoothAdmin.enable();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        initBT();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cnAnalytics = new DBAnalyticsUtils(this);

        // Copia las base de datos con información, solo para muestra
        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        CopyAssetDBUtility.copyDB(this, DB_ANALYTICS_NAME);

        ll_baseline = (LinearLayout) findViewById(R.id.ll_baseline);
        ll_dailyClassroom = (LinearLayout) findViewById(R.id.ll_dailyClassroom);
        ll_indicator = (LinearLayout) findViewById(R.id.ll_indicator);
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        lv_indicator = (ListView) findViewById(R.id.lv_indicator);
        lv_forms = (ListView) findViewById(R.id.lv_forms);
        lv_schools = (ListView) findViewById(R.id.lv_schools);
        tv_baseline = (TextView) findViewById(R.id.tv_baseline);
        tv_dailyclassroom = (TextView) findViewById(R.id.tv_dailyclassroom);
        btn_infra = (ImageButton) findViewById(R.id.btn_infra);
        fl0 = (FrameLayout) findViewById(R.id.fl0);
        fl1 = (FrameLayout) findViewById(R.id.fl1);
        fl2 = (FrameLayout) findViewById(R.id.fl2);
        fl3 = (FrameLayout) findViewById(R.id.fl3);
        btn_base = (ImageButton) findViewById(R.id.btn_base);
        btn_daily = (ImageButton) findViewById(R.id.btn_daily);
        btn_teacher= (ImageButton) findViewById(R.id.btn_teacher);
        btn_sm = (ImageButton) findViewById(R.id.btn_sm);
        btn_le = (ImageButton) findViewById(R.id.btn_le);
        btn_we = (ImageButton) findViewById(R.id.btn_we);
        btn_nu = (ImageButton) findViewById(R.id.btn_nu);
        btn_he = (ImageButton) findViewById(R.id.btn_he);
        tv_school_list = (TextView) findViewById(R.id.tv_school_list);
        tv_school_name = (TextView) findViewById(R.id.tv_school_name);

        fl0.setVisibility(View.GONE);
        fl1.setVisibility(View.GONE);
        fl2.setVisibility(View.GONE); // PRIMER MENU, PERO POR AHORA QUEDA FUERA
        fl3.setVisibility(View.GONE);
        ll_collect.setVisibility(View.GONE);
        ll_indicator.setVisibility(View.GONE);

        btn_infra.setOnClickListener(this);
        btn_teacher.setOnClickListener(this);
        btn_sm.setOnClickListener(this);
        btn_le.setOnClickListener(this);
        btn_we.setOnClickListener(this);
        btn_nu.setOnClickListener(this);
        btn_he.setOnClickListener(this);

        ll_baseline.setOnClickListener(this);
        ll_dailyClassroom.setOnClickListener(this);

        ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
        tv_baseline.setTextColor(Color.parseColor("#F47321"));
        btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));

        bluetoothAdmin = new BluetoothSPP(this);
        bluetoothAdmin.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] bytes, String s) {
                JSONParser2DB_new test = new JSONParser2DB_new(s);
                test.createAndSaveDB(main.this);
            }
        });



//        ArrayAdapter adaptadorSchools = new ArrayAdapter<String> (this, R.layout.row_listview, GrupoFormularios);
//        lv_schools.setAdapter(adaptadorSchools);

        lv_schools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) lv_schools.getItemAtPosition(itemPosition);
                //Toast.makeText(getApplicationContext(), String.valueOf(position) , Toast.LENGTH_SHORT).show();
                school_selected = itemValue;
                Toast.makeText(getApplicationContext(), school_selected , Toast.LENGTH_SHORT).show();

                ll_indicator.setVisibility(View.VISIBLE);
                fl1.setVisibility(View.GONE);
                tv_school_name.setText(itemValue);
                fl0.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter adaptadorGrupoForms = new ArrayAdapter<String> (this, R.layout.row_list_indicator, GrupoFormularios);
        lv_forms.setAdapter(adaptadorGrupoForms);

        lv_forms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //Toast.makeText(getApplicationContext(), "Literacy Boost", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(main.this, CollectSelectActivityNEW.class);
                        Bundle gi1 = new Bundle();
                        gi1.putString("groupIndicator", "LITERACY BOOST");
                        intent1.putExtras(gi1);
                        startActivity(intent1);
                        break;
                    case 1: //Toast.makeText(getApplicationContext(), "REACH", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(main.this, CollectSelectActivityNEW.class);
                        Bundle gi2 = new Bundle();
                        gi2.putString("groupIndicator", "REACH");
                        intent2.putExtras(gi2);
                        startActivity(intent2);
                        break;
                }
            }
        });

        ArrayAdapter adaptador = new ArrayAdapter<String> (this, R.layout.row_list_indicator, listaIndicadores);
        lv_indicator.setAdapter(adaptador);

        lv_indicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //Toast.makeText(getApplicationContext(), "Management", Toast.LENGTH_SHORT).show();
                        Intent intent00 = new Intent(main.this, Reach_pg_sm_0.class);
                        startActivity(intent00);
                        break;
                    case 1: //Toast.makeText(getApplicationContext(), "Literacy", Toast.LENGTH_SHORT).show();
                        Intent intent11 = new Intent(main.this, Reach_pg_le_0.class);
                        startActivity(intent11);
                        break;
                    case 2: //Toast.makeText(getApplicationContext(), "WASH", Toast.LENGTH_SHORT).show();
                        Intent intent22 = new Intent(main.this, Reach_pg_we_0.class);
                        startActivity(intent22);
                        break;
                    case 3: //Toast.makeText(getApplicationContext(), "Nutrition", Toast.LENGTH_SHORT).show();
                        Intent intent33 = new Intent(main.this, Reach_pg_nu_0.class);
                        startActivity(intent33);
                        break;
                    case 4: //Toast.makeText(getApplicationContext(), "Health", Toast.LENGTH_SHORT).show();
                        Intent intent44 = new Intent(main.this, Reach_pg_he_0.class);
                        startActivity(intent44);
                        break;
                    case 5: //Toast.makeText(getApplicationContext(), "Health", Toast.LENGTH_SHORT).show();
                        Intent intent55 = new Intent(main.this, Literacy_pg_bl_0.class);
                        startActivity(intent55);
                        break;
                    case 6: //Toast.makeText(getApplicationContext(), "Health", Toast.LENGTH_SHORT).show();
                        Intent intent66 = new Intent(main.this, Camp_pg_bl_0.class);
                        startActivity(intent66);
                        break;
                }
            }
        });

    }




    // Al iniciar la aplicación, activa el bluetooth y la función que recibe el json. Por ahora no se va a necesitar.
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        super.onStart();
        if (bluetoothAdmin != null) {
            if (!bluetoothAdmin.isBluetoothEnabled()) {
                bluetoothAdmin.enable();
            } else {
                initBT();
            }
        }
    }

    private void initBT() {
        bluetoothAdmin.setupService();
        bluetoothAdmin.getBluetoothAdapter();
        bluetoothAdmin.startService(BluetoothState.DEVICE_ANDROID);
        Toast.makeText(main.this, "Waiting for information....", Toast.LENGTH_LONG).show();
        if (bluetoothAdmin.getBluetoothAdapter().getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverableIntent);
        }
    }

    // Detiene el servicio de Bluetooth, por ahora no va a ser util.
    @Override
    protected void onStop() {
        super.onStop();
        bluetoothAdmin.stopService();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bluetoothAdmin.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bluetoothAdmin.setupService();
                bluetoothAdmin.startService(BluetoothState.DEVICE_ANDROID);
                Toast.makeText(main.this, "Waiting for information again....", Toast.LENGTH_LONG).show();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_baseline:
                // fl2.setVisibility(View.VISIBLE); PRIMER MENU, POR AHORA QUEDA FUERA
                fl1.setVisibility(View.GONE);
                fl3.setVisibility(View.GONE);
                ll_indicator.setVisibility(View.GONE);
                ll_collect.setVisibility(View.VISIBLE);
                ll_baseline.setBackgroundColor(Color.parseColor("#F47321"));
                tv_baseline.setTextColor(Color.parseColor("#ffffff"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#343434"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#F47321"));
                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4_));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5));
                break;
            case R.id.ll_dailyClassroom:
                //fl3.setVisibility(View.VISIBLE);
                fl0.setVisibility(View.GONE);
                fl1.setVisibility(View.VISIBLE);
                ll_collect.setVisibility(View.GONE);
                ll_indicator.setVisibility(View.GONE);
                fl2.setVisibility(View.GONE);
                ll_baseline.setBackgroundColor(Color.parseColor("#343434"));
                tv_baseline.setTextColor(Color.parseColor("#F47321"));
                ll_dailyClassroom.setBackgroundColor(Color.parseColor("#F47321"));
                tv_dailyclassroom.setTextColor(Color.parseColor("#ffffff"));

                btn_base.setImageDrawable(getResources().getDrawable(R.drawable.icon4));
                btn_daily.setImageDrawable(getResources().getDrawable(R.drawable.icon5_));



                //Toast.makeText(getApplicationContext(), "Synchronizing ... please wait!!!", Toast.LENGTH_LONG).show();
                try {
                    dbConection();
                    getSchools();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.btn_infra:
            //dialogAlert(2);
            Intent intent1 = new Intent(main.this, CollectSelectActivityNEW.class);
                Bundle gi1 = new Bundle();
                gi1.putString("groupIndicator", "LITERACY BOOST");
                intent1.putExtras(gi1);
            startActivity(intent1);
            break;
            case R.id.btn_teacher:
                //dialogAlert(2);
                Intent intent2 = new Intent(main.this, CollectSelectActivityNEW.class);
                Bundle gi2 = new Bundle();
                gi2.putString("groupIndicator", "REACH");
                intent2.putExtras(gi2);
                startActivity(intent2);
                break;

//            case R.id.btn_sm:
//                //Toast.makeText(getApplicationContext(), "Management", Toast.LENGTH_SHORT).show();
//                Intent intent00 = new Intent(main.this, Reach_pg_sm_0.class);
//                startActivity(intent00);
//                break;
//            case R.id.btn_le:
//                //Toast.makeText(getApplicationContext(), "Literacy", Toast.LENGTH_SHORT).show();
//                Intent intent11 = new Intent(main.this, Reach_pg_le_0.class);
//                startActivity(intent11);
//                break;
//            case R.id.btn_we:
//                //Toast.makeText(getApplicationContext(), "Literacy", Toast.LENGTH_SHORT).show();
//                Intent intent22 = new Intent(main.this, Reach_pg_we_0.class);
//                startActivity(intent22);
//                break;
//            case R.id.btn_nu:
//                //Toast.makeText(getApplicationContext(), "Nutrition", Toast.LENGTH_SHORT).show();
//                Intent intent33 = new Intent(main.this, Reach_pg_nu_0.class);
//                startActivity(intent33);
//                break;
//            case R.id.btn_he:
//                //Toast.makeText(getApplicationContext(), "Health", Toast.LENGTH_SHORT).show();
//                Intent intent44 = new Intent(main.this, Reach_pg_he_0.class);
//                startActivity(intent44);
//                break;
        }
    }

    public void dbConection () throws IOException, XmlPullParserException {

        macAdr = getMacAddress();
        String formsSync;
        // Conexion to Forms for obtain the table name
        Conexion cnodkforms = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "forms.db",null,4);
        SQLiteDatabase dbodkforms = cnodkforms.getWritableDatabase(); // aqui debe ser solo lectura?
        Cursor cforms = dbodkforms.rawQuery("SELECT displayName, formFilePath FROM forms WHERE displayName like 'WV%'",null);

        // Conexion each intances to Form List
        Conexion cnodkintances = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "instances.db", null,4);
        SQLiteDatabase dbodkintances = cnodkintances.getWritableDatabase(); // aqui debe ser solo lectura?

        Conexion cnfhi360 = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360 = cnfhi360.getWritableDatabase(); // aqui debe ser solo lectura?

        if (cforms.moveToFirst()) {
            do {
                 // Show all form in ODK
                //Toast.makeText(getApplicationContext(), cforms.getString(0), Toast.LENGTH_SHORT).show();
                xml_form = cforms.getString(0);
                // Extract all instances and Path (all filled form) from ODK
                Cursor cintances =  dbodkintances.rawQuery("SELECT instanceFilePath,_id FROM instances where displayName='" + cforms.getString(0) + "'" ,null);
                if (cintances.moveToFirst()) {
                    do {
                        // Show all instances filled in ODK
                        //Toast.makeText(getApplicationContext(), cintances.getString(0), Toast.LENGTH_SHORT).show();

                        // Mark forms sync
                        Cursor sftrue = dbfhi360.rawQuery("SELECT count(*) FROM syncforms WHERE form_id="+ cintances.getString(1).toString(),null);
                        sftrue.moveToFirst();
                        if (sftrue.getString(0).equals("0")) {
                            dbfhi360.execSQL("INSERT INTO syncforms(form_id,form_name) VALUES(" + cintances.getString(1).toString() + ",\"" + cintances.getString(0).toString() + "\");");
                            //InsertFormSycn(cintances.getString(1).toString(),cintances.getString(0).toString());
                            //if (xml_form.equals("WV_Boost_Lesson")) {
                                xml_path = cintances.getString(0).toString();
                                String[] tmp_path = {xml_path};


                                XMLParserschoolcode.main(tmp_path);
                                XMLParserInsertInformation.main(tmp_path);
                                //if (!jsonSend.isEmpty()) {dbfhi360.execSQL(jsonSend); Toast.makeText(getApplicationContext(), jsonSend, Toast.LENGTH_LONG).show();}
                            if (listColum.size()>0) {
                                for (int i=0; i < listColum.size(); i++) {
                                    dbfhi360.execSQL(listColum.get(i));
                                }
                            }
                            listColum.clear();
                            //}

                        } sftrue.close();

                    } while ((cintances.moveToNext()));
                } cintances.close();

            } while (cforms.moveToNext());
        }

        dbfhi360.close();
        cnfhi360.close();

        dbodkintances.close();
        cnodkintances.close();

        cforms.close();
        dbodkforms.close();
        cnodkforms.close();

    }

    public void InsertFormSycn (String id, String nameForm) {
        //Toast.makeText(getApplicationContext(),"INSERT INTO syncforms VALUES(" + id + ",\"" + nameForm + "\"", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"synchronizing ... please wait!!!", Toast.LENGTH_LONG).show();
    }

    public String getMacAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String mac = wInfo.getMacAddress();
        return mac;
    }

    public  void getSchools() {
        SchoolsCode.clear();
        listSchools.clear();
        int i = 0;
        tv_school_list.setText(getResources().getString(R.string.str_school_list));

        Conexion cnfhi360SCH = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "analytics.db",null,4);
        SQLiteDatabase dbfhi360SCH = cnfhi360SCH.getWritableDatabase(); // aqui debe ser solo lectura?

        Cursor schools = dbfhi360SCH.rawQuery("SELECT school_code, count(*)  FROM tblresults GROUP BY school_code",null);
        //Cursor schools = dbfhi360SCH.rawQuery("SELECT a.school_code, school  FROM tblresults AS a JOIN WV_schools AS b ON    a.school_code=b.id_school GROUP BY a.school_code",null);

        if (schools.moveToFirst()) {
          do {
              //Toast.makeText(getApplicationContext(), schools.getString(2).toString(), Toast.LENGTH_LONG).show();
              SchoolsCode.add(schools.getString(0).toString());
              listSchools.add(schools.getString(0).toString());
              i++;
          }  while (schools.moveToNext());
        }

        if (i>0) {
            ArrayAdapter adaptadorSchools = new ArrayAdapter<String> (this, R.layout.row_listview, listSchools);
            lv_schools.setAdapter(adaptadorSchools);
        } else {
            tv_school_list.setText(getResources().getString(R.string.str_no_information));
            //Toast.makeText(getApplicationContext(), "NO HAY INFORMACIÓN", Toast.LENGTH_LONG).show();
        }

        schools.close();
        dbfhi360SCH.close();
        cnfhi360SCH.close();
    }

}
