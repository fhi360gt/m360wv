package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.mysql.DBFormsUtils;

import java.util.ArrayList;

/**
 * Created by George on 08/09/2015.
 */
public class CollectSelectActivityNEW extends Activity implements View.OnClickListener {
    private final static String FORM_URI = "content://org.odk.collect.android.provider.odk.forms/forms/";
    private TextView tv_groupIndicator;
    private ListView listIndicator;

    private DBFormsUtils conn;
    private DBAnalyticsUtils cnAnalytics;
    private int isShow;
//    private String[][] forms = {{"REACH_OTL_School_Obs_v3","REACH_OTL_Health_Observation_V3"},
//            {"REACH_OTL_School_Director_v3","REACH_Health_School_Admin_v3"},
//            {"REACH_LitScan_3-4_v3","REACH_OTL_Teacher_v3","REACH_Health_Teacher_Logic_V3"},
//            {"REACH_OTL_Class_Obs_v3"}};
//    private String[][] forms_title = {{"Time Observation000", "Health Observation"},
//            {"Time Interview", "Health Interview"},
//            {"Literacy Interview", "Time Interview", "Health Interview"},
//            {"Time Observation"}};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_form_layout);

        conn = new DBFormsUtils(this);
        cnAnalytics = new DBAnalyticsUtils(this);

        listIndicator = (ListView) findViewById(R.id.listIndicator);
        tv_groupIndicator = (TextView) findViewById(R.id.tv_groupIndicator);
       // button_back = (TextView) findViewById(R.id.button_back);

        //        button_back.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        tv_groupIndicator.setText(bundle.getString("groupIndicator"));
        //tv_groupIndicator.setText("Forms");


        switch (tv_groupIndicator.getText().toString()) {
            case "REACH":
                isShow = 1;
                final ArrayList<String> forms_title1 = cnAnalytics.getCollectOptionsTitle("REACHWV");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title1));
                final ArrayList<String> forms1 = cnAnalytics.getCollectOptionsForms("REACHWV");
                //Toast.makeText(getApplicationContext(),"Formulario", Toast.LENGTH_SHORT).show();

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms1.get(position)));
                    }
                });

                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
//                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
//                        openFormId(forms[position]);
//                    }
//                });
                break;
            case "LITERACY BOOST":
                isShow = 2;
                final ArrayList<String> forms_title2 = cnAnalytics.getCollectOptionsTitle("LITERACYBOOST");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));

                    listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title2));



                final ArrayList<String> forms2 = cnAnalytics.getCollectOptionsForms("LITERACYBOOST");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent1 = new Intent(CollectSelectActivityNEW.this, CollectSelectActivityNEW.class);
//                        Bundle gi1 = new Bundle();
//                        gi1.putString("groupIndicator", forms_title2.get(position));
//                        intent1.putExtras(gi1);
//                        startActivity(intent1);
                        openFormId(conn.getFormId(forms2.get(position)));

                    }
                });

//                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
//                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
//                        openFormId(conn.getFormId(forms[isShow - 1][position]));
//                    }
//                });
                break;
            case "School Observation":
                isShow = 3;
                final ArrayList<String> forms_title3 = cnAnalytics.getCollectOptionsTitle("LBSOWV");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title3));
                final ArrayList<String> forms3 = cnAnalytics.getCollectOptionsForms("LBSOWV");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms3.get(position)));
                    }
                });
//                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
//                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
//                        openFormId(conn.getFormId(forms[isShow - 1][position]));
//                    }
//                });
                break;
            case "School Director Interview":
                isShow = 4;
                final ArrayList<String> forms_title4 = cnAnalytics.getCollectOptionsTitle("LBSDIWV");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title4));
                final ArrayList<String> forms4 = cnAnalytics.getCollectOptionsForms("LBSDIWV");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms4.get(position)));
                    }
                });
//                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
//                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
//                        openFormId(conn.getFormId(forms[isShow - 1][position]));
//                    }
//                });
                break;
            case "Teacher Interview":
                final ArrayList<String> forms_title5 = cnAnalytics.getCollectOptionsTitle("LBTIWV");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title5));
                final ArrayList<String> forms5 = cnAnalytics.getCollectOptionsForms("LBTIWV");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms5.get(position)));
                    }
                });
                break;
            case "Lesson/Camp Observation":
                final ArrayList<String> forms_title6 = cnAnalytics.getCollectOptionsTitle("LBLCOWV");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title6));
                final ArrayList<String> forms6 = cnAnalytics.getCollectOptionsForms("LBLCOWV");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms6.get(position)));
                    }
                });
                break;
            case "Management and Teaching Staff":
                final ArrayList<String> forms_title7 = cnAnalytics.getCollectOptionsTitle("SEMTS1");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title7));
                final ArrayList<String> forms7 = cnAnalytics.getCollectOptionsForms("SEMTS1");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms7.get(position)));
                    }
                });
                break;
            case "Summary Counts":
                final ArrayList<String> forms_title8 = cnAnalytics.getCollectOptionsTitle("SESC");
                //listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title));
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, forms_title8));
                final ArrayList<String> forms8 = cnAnalytics.getCollectOptionsForms("SESC");

                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openFormId(conn.getFormId(forms8.get(position)));
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button_back:
//                System.out.println("CloseApplication");
//                this.finish();
//                break;
//        }
    }

    public void openFormId(long formId) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.odk.collect.android");
        launchIntent.setClassName("org.odk.collect.android", "org.odk.collect.android.activities.FormEntryActivity");
        launchIntent.setAction(Intent.ACTION_EDIT);
        launchIntent.setData(Uri.parse(FORM_URI + formId));

        startActivity(launchIntent);
    }

}
