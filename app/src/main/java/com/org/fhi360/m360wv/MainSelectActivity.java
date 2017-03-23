package com.org.fhi360.m360wv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;

import java.util.ArrayList;

/**
 * Created by George on 17/06/2015.
 */
public class MainSelectActivity extends Activity implements View.OnClickListener{
    private TextView tv_groupIndicator, button_back;
    private ListView listIndicator;
    private DBAnalyticsUtils cnAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_select_layout);

        cnAnalytics = new DBAnalyticsUtils(this);

        listIndicator = (ListView) findViewById(R.id.listIndicator);
        tv_groupIndicator = (TextView) findViewById(R.id.tv_groupIndicator);
        button_back = (TextView) findViewById(R.id.button_back);

        button_back.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        tv_groupIndicator.setText(bundle.getString("groupIndicator"));
        Toast.makeText(getApplicationContext(),tv_groupIndicator.getText(),Toast.LENGTH_SHORT).show();

        switch (tv_groupIndicator.getText().toString()) {
            case "Time to learn":
                //Toast.makeText(getApplicationContext(),"Muestra los indicadores de OTL",Toast.LENGTH_SHORT).show();
                final ArrayList<String> optionsOTL = cnAnalytics.getAnalyticsOptions("OTL");
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, optionsOTL));

                // Select indicator_layout and send to new ActivityIndicator
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainSelectActivity.this, IndicatorActivity.class);
                        Bundle b = new Bundle();
                        b.putString("nombre_indicador", optionsOTL.get(position));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                break;
            case "Literacy":
                final ArrayList<String> optionsLitScan = cnAnalytics.getAnalyticsOptions("LITSCAN");
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, optionsLitScan));

                // Select indicator_layout and send to new ActivityIndicator
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainSelectActivity.this, IndicatorActivity.class);
                        Bundle b = new Bundle();
                        b.putString("nombre_indicador", optionsLitScan.get(position));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                break;
            case "Health":
                final ArrayList<String> optionsHealth = cnAnalytics.getAnalyticsOptions("HEALTH");
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, optionsHealth));

                // Select indicator_layout and send to new ActivityIndicator
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainSelectActivity.this, IndicatorActivity.class);
                        Bundle b = new Bundle();
                        b.putString("nombre_indicador", optionsHealth.get(position));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                break;
            case "Composite":
                final ArrayList<String> optionsComposite = cnAnalytics.getAnalyticsOptions("COMPOSITE");
                listIndicator.setAdapter(new ArrayAdapter<String>(this, R.layout.row_menu_select, optionsComposite));

                // Select indicator_layout and send to new ActivityIndicator
                listIndicator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getApplicationContext(),"***id="+id+" INDICATOR.. " + ConexionAnalytics("OTL").get(position) ,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainSelectActivity.this, IndicatorActivity.class);
                        Bundle b = new Bundle();
                        b.putString("nombre_indicador", optionsComposite.get(position));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                break;
            case "Dashboard" :
                Intent intent= new Intent(this, DashboardActivity.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                //Toast.makeText(getApplicationContext(),"Ha selecionado salir",Toast.LENGTH_SHORT).show();
                System.out.println("CloseApplication");
                this.finish();
                break;
        }
    }
}
