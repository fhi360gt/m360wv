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
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.utils.CopyAssetDBUtility;
import com.org.fhi360.m360wv.utils.JSONParser2DB;

import java.io.File;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private final static String DB_INDICATORS_NAME = "indicators.db";
    private final static String DB_ANALYTICS_NAME = "analytics.db"; // esta base de datos se copia temporalmente, solo para mostrar INDICADORES

    private int isShow = 0;
    private BluetoothSPP bluetoothAdmin;
    public static ContentValues data_ind = new ContentValues();
    public static String[] data_display;
    public int n_ind = 0, contador=0;
    private DBAnalyticsUtils cnAnalytics;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private TextView btnLoading, btnOlt, btnLitScan, btnHealth, btnComposite, btnDashboard, button_back;
    private ImageView selectOtl, selectLitScan, selectHealth, selectComposite, selectDashboard;
    private LinearLayout title;
    private ListView lstIndicator;
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
        setContentView(R.layout.main_layout);
        cnAnalytics = new DBAnalyticsUtils(this);

        // Copia las base de datos con información, solo para muestra
        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        CopyAssetDBUtility.copyDB(this, DB_ANALYTICS_NAME);




        //lstIndicator = (ListView) findViewById(R.id.listIndicator);
        btnLoading = (TextView) findViewById(R.id.loading);
        btnComposite = (TextView) findViewById(R.id.Composite);
        btnHealth = (TextView) findViewById(R.id.Health);
        btnLitScan = (TextView) findViewById(R.id.LitScan);
        btnOlt = (TextView) findViewById(R.id.OLT);
        btnDashboard = (TextView) findViewById(R.id.Dashboard);
        button_back = (TextView) findViewById(R.id.button_back);
//        selectOtl = (ImageView) findViewById(R.id.selOLT);
//        selectHealth = (ImageView) findViewById(R.id.selHealth);
//        selectComposite = (ImageView) findViewById(R.id.selComposite);
//        selectLitScan = (ImageView) findViewById(R.id.selLitScan);
//        selectDashboard = (ImageView) findViewById(R.id.selDashboard);
        //title = (LinearLayout) findViewById(R.id.layout_title);

        btnLoading.setOnClickListener(this);
        btnComposite.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
        btnLitScan.setOnClickListener(this);
        btnOlt.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);
        button_back.setOnClickListener(this);
        setSelector(0);


        bluetoothAdmin = new BluetoothSPP(this);
        bluetoothAdmin.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] bytes, String s) {
                JSONParser2DB test = new JSONParser2DB(s);
                test.createAndSaveDB(MainActivity.this);
            }
        });

    }

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
        Toast.makeText(MainActivity.this, "Waiting for information....", Toast.LENGTH_LONG).show();
        if (bluetoothAdmin.getBluetoothAdapter().getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverableIntent);
        }
    }

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
                Toast.makeText(MainActivity.this, "Waiting for information again....", Toast.LENGTH_LONG).show();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }

    public void setSelector(int selector) {
        //title.setVisibility(View.GONE);
        //lstIndicator.setVisibility(View.VISIBLE);
        switch (selector) {
            case 1:
                //selectOtl.setVisibility(View.VISIBLE);
                //selectLitScan.setVisibility(View.INVISIBLE);
                //selectHealth.setVisibility(View.INVISIBLE);
                //selectComposite.setVisibility(View.INVISIBLE);
                //selectDashboard.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(),"Ahroa abre la siguiente actividad con el menu de indicadores",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(this,MainSelectActivity.class));
                Intent intent1 = new Intent(MainActivity.this, MainSelectActivity.class);
                Bundle gi1 = new Bundle();
                gi1.putString("groupIndicator", "Time to learn");
                intent1.putExtras(gi1);
                startActivity(intent1);
                break;
            case 2:
                //selectOtl.setVisibility(View.INVISIBLE);
                //selectLitScan.setVisibility(View.VISIBLE);
                //selectHealth.setVisibility(View.INVISIBLE);
                //selectComposite.setVisibility(View.INVISIBLE);
                //selectDashboard.setVisibility(View.INVISIBLE);
                Intent intent2 = new Intent(MainActivity.this, MainSelectActivity.class);
                Bundle gi2 = new Bundle();
                gi2.putString("groupIndicator", "Literacy");
                intent2.putExtras(gi2);
                startActivity(intent2);
                break;
            case 3:
                //selectOtl.setVisibility(View.INVISIBLE);
                //selectLitScan.setVisibility(View.INVISIBLE);
                //selectHealth.setVisibility(View.VISIBLE);
                //selectComposite.setVisibility(View.INVISIBLE);
                //selectDashboard.setVisibility(View.INVISIBLE);
                Intent intent3 = new Intent(MainActivity.this, MainSelectActivity.class);
                Bundle gi3 = new Bundle();
                gi3.putString("groupIndicator", "Health");
                intent3.putExtras(gi3);
                startActivity(intent3);
                break;
            case 4:
                //selectOtl.setVisibility(View.INVISIBLE);
                //selectLitScan.setVisibility(View.INVISIBLE);
                //selectHealth.setVisibility(View.INVISIBLE);
                //selectComposite.setVisibility(View.VISIBLE);
                //selectDashboard.setVisibility(View.INVISIBLE);
                Intent intent4 = new Intent(MainActivity.this, MainSelectActivity.class);
                Bundle gi4 = new Bundle();
                gi4.putString("groupIndicator", "Composite");
                intent4.putExtras(gi4);
                startActivity(intent4);
                break;
            case 5:
                //selectOtl.setVisibility(View.INVISIBLE);
                //selectLitScan.setVisibility(View.INVISIBLE);
                //selectHealth.setVisibility(View.INVISIBLE);
                //selectComposite.setVisibility(View.INVISIBLE);
                //selectDashboard.setVisibility(View.VISIBLE);
                break;
            default:
                //title.setVisibility(View.VISIBLE);
                //lstIndicator.setVisibility(View.GONE);
                //selectOtl.setVisibility(View.INVISIBLE);
                //selectLitScan.setVisibility(View.INVISIBLE);
                //selectHealth.setVisibility(View.INVISIBLE);
                //selectDashboard.setVisibility(View.INVISIBLE);
                //selectComposite.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Health:
//                if (isShow != 3) {
                setSelector(3);
//                    final ArrayList<String> optionsHealth = cnAnalytics.getAnalyticsOptions("HEALTH");
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, optionsHealth));
//
//
//                    // Select indicator_layout and send to new ActivityIndicator
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(MainActivity.this, IndicatorActivity.class);
//                            Bundle b = new Bundle();
//                            b.putString("nombre_indicador", optionsHealth.get(position));
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        }
//                    });
//                    isShow = 3;
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.OLT:
                // fell ListView for OTL indicator_layout group
//                if (isShow != 1) {
                setSelector(1);
//                    final ArrayList<String> optionsOLT = cnAnalytics.getAnalyticsOptions("OTL");
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, optionsOLT));
//
//
//                    // Select indicator_layout and send to new ActivityIndicator
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(MainActivity.this, IndicatorActivity.class);
//                            Bundle b = new Bundle();
//                            b.putString("nombre_indicador", optionsOLT.get(position));
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        }
//                    });
//                    isShow = 1;
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.LitScan:
//                if (isShow != 2) {
                setSelector(2);

//                    final ArrayList<String> optionsLitScan = cnAnalytics.getAnalyticsOptions("LITSCAN");
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, optionsLitScan));
//
//                    // Select indicator_layout and send to new ActivityIndicator
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(MainActivity.this, IndicatorActivity.class);
//                            Bundle b = new Bundle();
//                            b.putString("nombre_indicador", optionsLitScan.get(position));
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        }
//                    });
//                    isShow = 2;
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.Composite:
//                if (isShow != 4) {
                setSelector(4);
//                    final ArrayList<String> optionsComposite = cnAnalytics.getAnalyticsOptions("COMPOSITE");
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, optionsComposite));
//
//                    // Select indicator_layout and send to new ActivityIndicator
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(MainActivity.this, IndicatorActivity.class);
//                            Bundle b = new Bundle();
//                            b.putString("nombre_indicador", optionsComposite.get(position));
//                            intent.putExtras(b);
//                            startActivity(intent);
//                        }
//                    });
//                    isShow = 4;
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.Dashboard :
                setSelector(8);
                isShow = 0;
                Intent intent= new Intent(this, DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.loading:
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

                //Conexion to Instances Database for read intances
                Conexion cninstances = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "instances.db", null, 4);
                SQLiteDatabase dbinstances = cninstances.getWritableDatabase(); // aqui debe ser solo lectura?

                Cursor cursorInstances = dbinstances.rawQuery("SELECT displayName,instanceFilePath  FROM instances", null);
                //Cursor cursorInstances = dbinstances.rawQuery("SELECT nameindicator  FROM tblindicators", null);
                Toast.makeText(getApplicationContext(),"No de registros del cursor ... ",Toast.LENGTH_LONG);
                if (cursorInstances.moveToFirst()) {
                    do  {
                        Toast.makeText(getApplicationContext(), "Entra al ciclo...", Toast.LENGTH_LONG);

                    } while (cursorInstances.moveToNext());
                }
                cursorInstances.close();
                dbinstances.close();
                cninstances.close();

                //Toast.makeText(getApplicationContext(), "Loading instances in analytics database... ", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_back:
                //Toast.makeText(getApplicationContext(),"Ha selecionado salir",Toast.LENGTH_SHORT).show();
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
