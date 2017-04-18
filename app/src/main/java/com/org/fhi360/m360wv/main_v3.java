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
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import com.org.fhi360.m360wv.R;

import com.org.fhi360.m360wv.data.ExpandableItemMenu;
import com.org.fhi360.m360wv.mysql.Conexion;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.mysql.DBFormsUtils;
import com.org.fhi360.m360wv.utils.CopyAssetDBUtility;
import com.org.fhi360.m360wv.utils.JSONParser2DB_new;
import com.org.fhi360.m360wv.utils.XMLParserInsertInformation;
import com.org.fhi360.m360wv.utils.XMLParserschoolcode;
import com.org.fhi360.m360wv.utils_menu.ExpandableListOptionAdapter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static android.view.View.GONE;

/**
 * Created by Sergio on 8/23/2016.
 */
public class main_v3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ExpandableListView.OnChildClickListener {

    private final static String FORM_URI = "content://org.odk.collect.android.provider.odk.forms/forms/";
    private DBFormsUtils conn;
    public  static String school_selected ="";
    private final static String DB_INDICATORS_NAME = "indicators.db";
    private final static String DB_ANALYTICS_NAME = "analytics.db"; // esta base de datos se copia temporalmente, solo para mostrar INDICADORES

    public static String xml_path="", xml_res="", xml_form="", jsonSend="", macAdr="";
    private BluetoothSPP bluetoothAdmin;
    public static ContentValues data_ind = new ContentValues();
    public static String[] data_display;
    public int n_ind = 0, contador=0;
    private DBAnalyticsUtils cnAnalytics;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";

    public static final ArrayList<String> listColum = new ArrayList<String>();
    public static final ArrayList<String> SchoolsCode = new ArrayList<String>();
    public static final ArrayList<String> listSchools = new ArrayList<String>();

    //ExpandableListAdapter listAdapter;
    ExpandableListView navigationmenu;
    ListView lv_schools;
    LinearLayout ll_tab_menu, ll_start, ll_select_school;

    String[] listaIndicadores = new String[] {"Management","Literacy","WASH","Nutrition","Health", "Lesson", "Camp"};
    String[] GrupoFormularios = new String[] {"Literacy Boost","REACH"};

    View view_Group;
    private DrawerLayout mDrawerLayout;
    //ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ArrayList<ExpandableItemMenu> menuItems = new ArrayList<ExpandableItemMenu> ();
    ArrayList<Object> subMenuItems = new ArrayList<Object> ();


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
        setContentView(R.layout.activity_main_v2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        conn = new DBFormsUtils(this);

        ll_start = (LinearLayout) findViewById(R.id.ll_start);
        ll_start.setVisibility(View.VISIBLE);
        ll_select_school = (LinearLayout) findViewById(R.id.ll_select_school);
        ll_select_school.setVisibility(View.GONE);
        ll_tab_menu = (LinearLayout) findViewById(R.id.ll_tab_menu);
        ll_tab_menu.setVisibility(GONE);

        lv_schools = (ListView) findViewById(R.id.lv_schools);
        //navigationmenu = (ExpandableListView) findViewById(R.id.navigationmenu);

        createMenuToExpandableListView();
        navigationmenu = (ExpandableListView) findViewById(R.id.navigationmenu);
        navigationmenu.setDividerHeight(2);
        navigationmenu.setGroupIndicator(null);
        navigationmenu.setClickable(true);


        cnAnalytics = new DBAnalyticsUtils(this);

        // Copia las base de datos con información, solo para muestra
        CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
        CopyAssetDBUtility.copyDB(this, DB_ANALYTICS_NAME);

        ExpandableListOptionAdapter mNewAdapter = new ExpandableListOptionAdapter(menuItems, subMenuItems);
        mNewAdapter
                .setInflater(
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                        this);
        navigationmenu.setAdapter(mNewAdapter);
        navigationmenu.setOnChildClickListener(main_v3.this);

        bluetoothAdmin = new BluetoothSPP(this);
        bluetoothAdmin.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] bytes, String s) {
                JSONParser2DB_new test = new JSONParser2DB_new(s);
                test.createAndSaveDB(main_v3.this);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //setContentView(R.layout.content_main);
        TabHost tab_menu = (TabHost) findViewById(R.id.tab_main);

        tab_menu.setup();

        TabHost.TabSpec tabSpec1 = tab_menu.newTabSpec("School/camp");
        tabSpec1.setContent(R.id.tab1);
        tabSpec1.setIndicator("School \nCamp");
        tab_menu.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tab_menu.newTabSpec("Management");
        tabSpec2.setContent(R.id.tab2);
        tabSpec2.setIndicator("Management");
        tab_menu.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tab_menu.newTabSpec("Literacy");
        tabSpec3.setContent(R.id.tab3);
        tabSpec3.setIndicator("Literacy");
        tab_menu.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4 = tab_menu.newTabSpec("WASH");
        tabSpec4.setContent(R.id.tab4);
        tabSpec4.setIndicator("WASH");
        tab_menu.addTab(tabSpec4);

        TabHost.TabSpec tabSpec5 = tab_menu.newTabSpec("Nutrition");
        tabSpec5.setContent(R.id.tab5);
        tabSpec5.setIndicator("Nutrition");
        tab_menu.addTab(tabSpec5);

        TabHost.TabSpec tabSpec6 = tab_menu.newTabSpec("Health");
        tabSpec6.setContent(R.id.tab6);
        tabSpec6.setIndicator("Health");
        tab_menu.addTab(tabSpec6);

        tab_menu.setCurrentTab(0);

        tab_menu.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });



//        tab_menu.setup();
//        tab_menu.setup(this,getSupportFragmentManager(),android.R.id.tabhost);
//        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Lengüeta 1"),
//                Tab1.class, null);
//        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Lengüeta 2"),
//                Tab2.class, null);
//        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Lengüeta 3"),
//                Tab3.class, null);


//        TabHost.TabSpec tabSpec1 = tab_menu.newTabSpec("School/camp");
//        tabSpec1.setContent(R.id.tab1);
//        tabSpec1.setIndicator("School/Camp");
//        //tab_menu.addTab(tabSpec1);
//
//        TabHost.TabSpec tabSpec2 = tab_menu.newTabSpec("camp");
//        tabSpec1.setContent(R.id.tab2);
//        tabSpec1.setIndicator("Camp");
//        //tab_menu.addTab(tabSpec2);
//
//        TabHost.TabSpec tabSpec3 = tab_menu.newTabSpec("School");
//        tabSpec1.setContent(R.id.tab3);
//        tabSpec1.setIndicator("School");
        //tab_menu.addTab(tabSpec3);
//
//        TabHost.TabSpec tabSpec2 = tab_menu.newTabSpec("Management");
//        tabSpec2.setIndicator("Tab2");
//
//        TabHost.TabSpec tabSpec3 = tab_menu.newTabSpec("Lesson");
//        tabSpec3.setIndicator("Tab3");



//        TabHost.TabSpec spec=tab_menu.newTabSpec("mitab1");
//        spec.setContent(R.id.tab1);
//        //spec.setIndicator("", res.getDrawable(android.R.drawable.ic_btn_speak_now));
//        spec.setIndicator("", getDrawable(android.R.drawable.sym_def_app_icon );
//        tab_menu.addTab(spec);
//
//        spec=tab_menu.newTabSpec("mitab2");
//        spec.setContent(R.id.tab2);
//        spec.setIndicator("TAB2", res.getDrawable(android.R.drawable.ic_dialog_map));
//        tab_menu.addTab(spec);
//
//        tab_menu.setCurrentTab(0);



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



        // ******************** Inicia  el navigationmenu para la listaExpandible ****************************
        private void createMenuToExpandableListView() {
            menuItems.add(new ExpandableItemMenu(R.drawable.ic_menu_manage, "Collect"));
            menuItems.add(new ExpandableItemMenu(R.drawable.ic_menu_manage, "Analytics"));
            //menuItems.add("Opcion 3");

            ArrayList<ExpandableItemMenu> submenu = new ArrayList<ExpandableItemMenu>();
            submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "Lesson Observation"));
            submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_manage, "Reading Camp Observation"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_lesson_observation, "School Director Interview"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "Class Observation"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "School Observation 1"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "School Observation 2"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "Teacher Interview"));
            subMenuItems.add(submenu);

            submenu = new  ArrayList<ExpandableItemMenu>();
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "Literacy Boost Campo"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "Literacy Boost Lesson"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "REACH Management"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "REACH Literacy"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "REACH WASH"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "REACH Nutition"));
        submenu.add(new ExpandableItemMenu(R.drawable.ic_menu_collect, "REACH Heatl"));
            subMenuItems.add(submenu);

//            submenu = new ArrayList<String>();
//            submenu.add("Subopcion 3-1");
//            submenu.add("Subopcion 3-2");
//            submenu.add("Subopcion 3-3");
//            subMenuItems.add(submenu);
        }

        // ******************** Termina el navigationmenu para la listaExpandible ****************************


    private void initBT() {
        bluetoothAdmin.setupService();
        bluetoothAdmin.getBluetoothAdapter();
        bluetoothAdmin.startService(BluetoothState.DEVICE_ANDROID);
        Toast.makeText(main_v3.this, "Waiting for information....", Toast.LENGTH_LONG).show();
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
                Toast.makeText(main_v3.this, "Waiting for information again....", Toast.LENGTH_LONG).show();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent00 = new Intent(main_v3.this, Reach_pg_sm_0.class);
            startActivity(intent00);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_form1) {
            openFormId(conn.getFormId("WV_Boost_Lesson"));
            // Handle the camera action

        }
        else if (id == R.id.nav_form2) {
            openFormId(conn.getFormId("WV_Boost_Camp"));
        } else if (id == R.id.nav_form3) {
            openFormId(conn.getFormId("WV_REACH_Director"));
        } else if (id == R.id.nav_form4) {
            openFormId(conn.getFormId("WV_REACH_Class"));
        } else if (id == R.id.nav_form5) {
            openFormId(conn.getFormId("WV_REACH_School_1"));
        } else if (id == R.id.nav_form6) {
            openFormId(conn.getFormId("WV_REACH_School_2"));
        } else if (id == R.id.nav_form7) {
            openFormId(conn.getFormId("WV_REACH_Teacher"));
    }
        else if (id == R.id.nav_report1) {
//            Intent intent66 = new Intent(main_v2.this, Camp_pg_bl_0.class);
//            startActivity(intent66);
            ll_start.setVisibility(GONE);
            //ll_tab_menu.setVisibility(View.VISIBLE);
            try {
                dbConection();
                getSchools();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            ll_select_school.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.nav_report2) {
            Intent intent55 = new Intent(main_v3.this, Literacy_pg_bl_0.class);
            startActivity(intent55);
        }
        else if (id == R.id.nav_report3) {
            Intent intent00 = new Intent(main_v3.this, Reach_pg_sm_0.class);
            startActivity(intent00);
        }
        else if (id == R.id.nav_report4) {
            Intent intent11 = new Intent(main_v3.this, Reach_pg_le_0.class);
            startActivity(intent11);
        }
        else if (id == R.id.nav_report5) {
            Intent intent22 = new Intent(main_v3.this, Reach_pg_we_0.class);
            startActivity(intent22);
        }
        else if (id == R.id.nav_report6) {
            Intent intent33 = new Intent(main_v3.this, Reach_pg_nu_0.class);
            startActivity(intent33);
        }
        else if (id == R.id.nav_report7) {
            Intent intent44 = new Intent(main_v3.this, Reach_pg_he_0.class);
            startActivity(intent44);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFormId(long formId) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.odk.collect.android");
        launchIntent.setClassName("org.odk.collect.android", "org.odk.collect.android.activities.FormEntryActivity");
        launchIntent.setAction(Intent.ACTION_EDIT);
        launchIntent.setData(Uri.parse(FORM_URI + formId));

        startActivity(launchIntent);
    }
    // Al iniciar la aplicación, activa el bluetooth y la función que recibe el json. Por ahora no se va a necesitar.

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
        //tv_school_list.setText(getResources().getString(R.string.str_school_list));

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
            //tv_school_list.setText(getResources().getString(R.string.str_no_information));
            //Toast.makeText(getApplicationContext(), "NO HAY INFORMACIÓN", Toast.LENGTH_LONG).show();
        }

        schools.close();
        dbfhi360SCH.close();
        cnfhi360SCH.close();
    }


    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        if (i==0) {Toast.makeText(main_v3.this, "Collect...", Toast.LENGTH_SHORT).show();}
        if (i==0 && i1==0) {openFormId(conn.getFormId("WV_Boost_Lesson"));}
        if (i==0 && i1==1) {openFormId(conn.getFormId("WV_Boost_Camp"));}
        if (i==0 && i1==2) {openFormId(conn.getFormId("WV_REACH_Director"));}
        if (i==0 && i1==3) {openFormId(conn.getFormId("WV_REACH_Class"));}
        if (i==0 && i1==4) {openFormId(conn.getFormId("WV_REACH_School_1"));}
        if (i==0 && i1==5) {openFormId(conn.getFormId("WV_REACH_School_2"));}
        if (i==0 && i1==6) {openFormId(conn.getFormId("WV_REACH_Teacher"));}

        if (i==1 && i1==0) {
            ll_start.setVisibility(GONE);
            //ll_tab_menu.setVisibility(View.VISIBLE);
            try {
                dbConection();
                getSchools();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            ll_select_school.setVisibility(View.VISIBLE);
//            Intent intent66 = new Intent(main_v3.this, Camp_pg_bl_0.class);
//            startActivity(intent66);
        }
        if (i==1 && i1==1) {
//            Intent intent55 = new Intent(main_v3.this, Literacy_pg_bl_0.class);
//            startActivity(intent55);
        }
        if (i==1 && i1==2) {

            Intent intent00 = new Intent(main_v3.this, Reach_pg_sm_0.class);
            startActivity(intent00);
        }
        if (i==1 && i1==3) {
            Intent intent11 = new Intent(main_v3.this, Reach_pg_le_0.class);
            startActivity(intent11);
        }
        if (i==1 && i1==4) {
            Intent intent22 = new Intent(main_v3.this, Reach_pg_we_0.class);
            startActivity(intent22);
        }
        if (i==1 && i1==5) {
            Intent intent33 = new Intent(main_v3.this, Reach_pg_nu_0.class);
            startActivity(intent33);
        }
        if (i==1 && i1==6) {
            Intent intent44 = new Intent(main_v3.this, Reach_pg_he_0.class);
            startActivity(intent44);
        }


        return true;
    }
}
