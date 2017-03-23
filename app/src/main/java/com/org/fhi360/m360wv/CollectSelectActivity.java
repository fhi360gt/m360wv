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

import com.org.fhi360.m360wv.mysql.DBFormsUtils;

/**
 * Created by George on 20/06/2015.
 */
public class CollectSelectActivity extends Activity implements View.OnClickListener {
    private final static String FORM_URI = "content://org.odk.collect.android.provider.odk.forms/forms/";
    private TextView tv_groupIndicator, button_back;
    private ListView listIndicator;
    private DBFormsUtils conn;
    private int isShow;
    private String[][] forms = {{"REACH_OTL_School_Obs_v3","REACH_OTL_Health_Observation_V3"},
            {"REACH_OTL_School_Director_v3","REACH_Health_School_Admin_v3"},
            {"REACH_LitScan_3-4_v3","REACH_OTL_Teacher_v3","REACH_Health_Teacher_Logic_V3"},
            {"REACH_OTL_Class_Obs_v3"}};
    private String[][] forms_title = {{"Time Observation", "Health Observation"},
            {"Time Interview", "Health Interview"},
            {"Literacy Interview", "Time Interview", "Health Interview"},
            {"Time Observation"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_form_layout);

        conn = new DBFormsUtils(this);
        listIndicator = (ListView) findViewById(R.id.listIndicator);
        tv_groupIndicator = (TextView) findViewById(R.id.tv_groupIndicator);
        button_back = (TextView) findViewById(R.id.button_back);

        button_back.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        tv_groupIndicator.setText(bundle.getString("groupIndicator"));

        switch (tv_groupIndicator.getText().toString()) {
            case "School Observation":
                isShow = 1;
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
                        openFormId(conn.getFormId(forms[isShow - 1][position]));
                    }
                });
                break;
            case "School Director Interview":
                isShow = 2;
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
                        openFormId(conn.getFormId(forms[isShow - 1][position]));
                    }
                });
                break;
            case "Teacher Interview":
                isShow = 3;
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
                        openFormId(conn.getFormId(forms[isShow - 1][position]));
                    }
                });
                break;
            case "Classroom Observation":
                isShow = 4;
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_item_1, forms_title[isShow - 1]));
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"*** forms["+(isShow-1)+"] ["+position+"] Position="+position,Toast.LENGTH_LONG).show();
                        openFormId(conn.getFormId(forms[isShow - 1][position]));
                    }
                });
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                System.out.println("CloseApplication");
                this.finish();
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

}
