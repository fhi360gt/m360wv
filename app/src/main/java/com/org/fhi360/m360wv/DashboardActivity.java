package com.org.fhi360.m360wv;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.org.fhi360.m360wv.data.DashboardAdapter;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;
import com.org.fhi360.m360wv.data.GraphInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 5/6/15.
 */
public class DashboardActivity extends Activity {
    private TextView txtTitle, btnBack;
    private ListView lstData;
    private DBAnalyticsUtils connAnalytics;
    private List<ArrayList<String>> resultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        String title, query;
        GraphInfo graphInfo;
        txtTitle = (TextView) findViewById(R.id.txtTitleDashboard);
        btnBack =(TextView) findViewById(R.id.btnBackDashboard);
        lstData = (ListView) findViewById(R.id.listDataDashboard);
        connAnalytics = new DBAnalyticsUtils(this);
        title = connAnalytics.getAnalyticsOptions("DASHBOARD").get(0);
        graphInfo = connAnalytics.getGraphInfo(title);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTitle.setText(title);
        resultados = connAnalytics.getDashboardData(graphInfo.getFormulate());
        lstData.setAdapter(new DashboardAdapter(this,R.layout.row_data_layout, resultados));

    }
}
