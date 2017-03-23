package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.org.fhi360.m360wv.mysql.DBFormsUtils;

/**
 * Created by jalfaro on 5/19/15.
 */
public class CollectActivity extends Activity implements View.OnClickListener{
    private final static String FORM_URI = "content://org.odk.collect.android.provider.odk.forms/forms/";
    private ImageView selSchoolObservation, selSchoolDirectorInterview, selTeacherInterview, selClassroomObservation;
    private TextView btnSchoolObservation, btnSchoolDirectorInterview, btnTeacherInterview, btnClassroomObservation, btnSESI, btnSERF, btnSEMTS, btnSESC,  button_back;
    private ListView lstIndicator;
    private LinearLayout title;
    private int isShow;
    private DBFormsUtils conn;
//    private String[][] forms = {{"REACH_OTL_School_Obs_v3","REACH_OTL_Health_Observation_V3"},
//            {"REACH_OTL_School_Director_v3","REACH_Health_School_Admin_v3"},
//            {"REACH_LitScan_3-4_v3","REACH_OTL_Teacher_v3","REACH_Health_Teacher_Logic_V3"},
//            {"REACH_OTL_Class_Obs_v3"}};
//    private String[][] forms_title = {{"Time Observation", "Health Observation"},
//            {"Time Interview", "Health Interview"},
//            {"Literacy Interview", "Time Interview", "Health Interview"},
//            {"Time Observation"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_layout);
        isShow = 0;
        conn = new DBFormsUtils(this);
        //title = (LinearLayout) findViewById(R.id.layout_title);
        //lstIndicator = (ListView) findViewById(R.id.lvindicator);
//        selSchoolDirectorInterview = (ImageView) findViewById(R.id.selSchoolDirectorInterviw);
//        selSchoolObservation = (ImageView) findViewById(R.id.selSchoolObservation);
//        selTeacherInterview = (ImageView) findViewById(R.id.selTeacherInterview);
//        selClassroomObservation = (ImageView) findViewById(R.id.selClassroomObservation);

        btnSchoolDirectorInterview = (TextView) findViewById(R.id.btnSchoolDirectorInterview);
        btnSchoolObservation = (TextView) findViewById(R.id.btnSchoolObservation);
        btnTeacherInterview = (TextView) findViewById(R.id.btnTeacherInterview);
        btnClassroomObservation = (TextView) findViewById(R.id.btnClassroomObservation);
        btnSESI = (TextView) findViewById(R.id.btnSESI);
        btnSERF = (TextView) findViewById(R.id.btnSERF);
        btnSEMTS = (TextView) findViewById(R.id.btnSEMTS);
        btnSESC = (TextView) findViewById(R.id.btnSESC);
        button_back = (TextView) findViewById(R.id.button_back);

        btnSchoolDirectorInterview.setOnClickListener(this);
        btnSchoolObservation.setOnClickListener(this);
        btnTeacherInterview.setOnClickListener(this);
        btnClassroomObservation.setOnClickListener(this);
        btnSESI.setOnClickListener(this);
        btnSERF.setOnClickListener(this);
        btnSEMTS.setOnClickListener(this);
        btnSESC.setOnClickListener(this);
        button_back.setOnClickListener(this);
        //setSelector(8);
    }

//    public void setSelector(int selector) {
//        title.setVisibility(View.GONE);
//        lstIndicator.setVisibility(View.VISIBLE);
//        switch (selector) {
//            case 1:
//                selSchoolObservation.setVisibility(View.VISIBLE);
//                selSchoolDirectorInterview.setVisibility(View.INVISIBLE);
//                selTeacherInterview.setVisibility(View.INVISIBLE);
//                selClassroomObservation.setVisibility(View.INVISIBLE);
//                break;
//            case 2:
//                selSchoolObservation.setVisibility(View.INVISIBLE);
//                selSchoolDirectorInterview.setVisibility(View.VISIBLE);
//                selTeacherInterview.setVisibility(View.INVISIBLE);
//                selClassroomObservation.setVisibility(View.INVISIBLE);
//                break;
//            case 3:
//                selSchoolObservation.setVisibility(View.INVISIBLE);
//                selSchoolDirectorInterview.setVisibility(View.INVISIBLE);
//                selTeacherInterview.setVisibility(View.VISIBLE);
//                selClassroomObservation.setVisibility(View.INVISIBLE);
//                break;
//            case 4:
//                selSchoolObservation.setVisibility(View.INVISIBLE);
//                selSchoolDirectorInterview.setVisibility(View.INVISIBLE);
//                selTeacherInterview.setVisibility(View.INVISIBLE);
//                selClassroomObservation.setVisibility(View.VISIBLE);
//                break;
//            default:
//                title.setVisibility(View.VISIBLE);
//                lstIndicator.setVisibility(View.GONE);
//                selSchoolObservation.setVisibility(View.INVISIBLE);
//                selSchoolDirectorInterview.setVisibility(View.INVISIBLE);
//                selTeacherInterview.setVisibility(View.INVISIBLE);
//                selClassroomObservation.setVisibility(View.INVISIBLE);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSchoolObservation :
                //Toast.makeText(getApplicationContext(),"Ha elegido los formularios de OTL",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi1 = new Bundle();
                gi1.putString("groupIndicator", "Fidelity tool");
                intent1.putExtras(gi1);
                startActivity(intent1);
//                if (isShow != 1) {
//                    isShow = 1;
//                    setSelector(isShow);
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow -1]));
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//                            openFormId(conn.getFormId(forms[isShow - 1][position]));
//                        }
//                    });
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.btnSchoolDirectorInterview :
                Intent intent2 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi2 = new Bundle();
                gi2.putString("groupIndicator", "Pre-Primary Education");
                intent2.putExtras(gi2);
                startActivity(intent2);
//                if (isShow != 2) {
//                    isShow = 2;
//                    setSelector(isShow);
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow -1]));
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//                            openFormId(conn.getFormId(forms[isShow - 1][position]));
//                        }
//                    });
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.btnTeacherInterview :
                Intent intent3 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi3 = new Bundle();
                gi3.putString("groupIndicator", "Primary Schools");
                intent3.putExtras(gi3);
                startActivity(intent3);
//                if (isShow != 3) {
//                    isShow = 3;
//                    setSelector(isShow);
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow -1]));
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//                            openFormId(conn.getFormId(forms[isShow - 1][position]));
//                        }
//                    });
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.btnClassroomObservation :
                Intent intent4 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi4 = new Bundle();
                gi4.putString("groupIndicator", "Secondary Schools");
                intent4.putExtras(gi4);
                startActivity(intent4);
//                if (isShow != 4) {
//                    isShow = 4;
//                    setSelector(isShow);
//                    lstIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow -1]));
//                    lstIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
//                            openFormId(conn.getFormId(forms[isShow - 1][position]));
//                        }
//                    });
//                } else {
//                    setSelector(8);
//                    isShow = 0;
//                }
                break;
            case R.id.btnSESI :
                Intent intent5 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi5 = new Bundle();
                gi5.putString("groupIndicator", "Schools Infrastructure");
                intent5.putExtras(gi5);
                startActivity(intent5);
                break;
            case R.id.btnSERF :
                Intent intent6 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi6 = new Bundle();
                gi6.putString("groupIndicator", "Resource Funds");
                intent6.putExtras(gi6);
                startActivity(intent6);
                break;
            case R.id.btnSEMTS :
                Intent intent7 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi7 = new Bundle();
                gi7.putString("groupIndicator", "Management and Teaching Staff");
                intent7.putExtras(gi7);
                startActivity(intent7);
                break;
            case R.id.btnSESC :
                Intent intent8 = new Intent(CollectActivity.this, CollectSelectActivityNEW.class);
                Bundle gi8 = new Bundle();
                gi8.putString("groupIndicator", "Summary Counts");
                intent8.putExtras(gi8);
                startActivity(intent8);
                break;
            case R.id.button_back:
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }

//    public void openFormId(long formId) {
//        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.odk.collect.android");
//        launchIntent.setClassName("org.odk.collect.android", "org.odk.collect.android.activities.FormEntryActivity");
//        launchIntent.setAction(Intent.ACTION_EDIT);
//        launchIntent.setData(Uri.parse(FORM_URI + formId));
//
//        startActivity(launchIntent);
//    }
}
