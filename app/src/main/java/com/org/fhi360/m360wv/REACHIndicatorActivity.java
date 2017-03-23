package com.org.fhi360.m360wv;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.org.fhi360.m360wv.data.GraphInfo;
import com.org.fhi360.m360wv.data.ResultAdapter;
import com.org.fhi360.m360wv.mysql.DBAnalyticsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 14/02/2015.
 */
public class REACHIndicatorActivity extends Activity implements View.OnClickListener
{
    public ArrayList<String> Result = new ArrayList<>();
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private PieChart pieChart, pieChart2, pieChart3, pieChart4;
    private LineChart lineChart, lineChart2, lineChart3, lineChart4;
    private BarChart barChart, barChart2, barChart3, barChart4;
    private TextView tvindicator, tvindicator2,tvindicator3, tvindicator4, txtBack;
    private ListView lvResult, lvResult2, lvResult3, lvResult4;
    private DBAnalyticsUtils conn;
    private GraphInfo data, data2, data3, data4;
    //private final static int [] COLORS = new int[] {Color.GREEN,Color.YELLOW, Color.RED, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.WHITE};
    //private final static int [] COLORS = new int[] {Color.parseColor("#F47321"),Color.parseColor("#FFA909"), Color.parseColor("#BA9C64"), Color.MAGENTA, Color.CYAN, Color.GRAY, Color.WHITE};
    // paleta de colores Rolando
    //private final static int [] COLORS = new int[] {Color.parseColor("#F47321"),Color.parseColor("#FFA909"), Color.parseColor("#06AD55"), Color.parseColor("#54D9B9"), Color.parseColor("#BA9C64"), Color.parseColor("#06AD55"), Color.parseColor("#FF8679")};
    // nueva paleta de colores
    private final static int [] COLORS = new int[] {Color.parseColor("#FF3300"),Color.parseColor("#132595"), Color.parseColor("#06AD55"), Color.parseColor("#F47321"), Color.parseColor("#BA9C64"), Color.parseColor("#06AD55"), Color.parseColor("#FF8679")};
    private final static String [] INFO1 = new String[] {"Prom","Las Year","This year"};
    private final static String [] INFO2 = new String[] {"2.5","2","3"};
    private Legend legend;
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3, fl_part4; // ************ FrameLayout ***************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reach_indicator_layout);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) findViewById(R.id.fl_part2);
        fl_part3 = (FrameLayout) findViewById(R.id.fl_part3);
        fl_part4 = (FrameLayout) findViewById(R.id.fl_part4);


        //  ************************ Objects Buttoms *********************
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        ImageButton btn_next = (ImageButton) findViewById(R.id.btn_next);
        ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);

        fl_part1.setVisibility(View.VISIBLE);
        fl_part2.setVisibility(View.GONE);
        fl_part3.setVisibility(View.GONE);
        fl_part4.setVisibility(View.GONE);

        // **************** CLICK ON BUTTONS ********************
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        conn = new DBAnalyticsUtils(this);
        tvindicator = (TextView)findViewById(R.id.tvindicator);
        tvindicator2 = (TextView)findViewById(R.id.tvindicator2);
        tvindicator3 = (TextView)findViewById(R.id.tvindicator3);
        tvindicator4 = (TextView)findViewById(R.id.tvindicator4);
        txtBack = (TextView) findViewById(R.id.homebtn);
        lvResult = (ListView) findViewById(R.id.lvResult);
        lvResult2 = (ListView) findViewById(R.id.lvResult2);
        lvResult3 = (ListView) findViewById(R.id.lvResult3);
        lvResult4 = (ListView) findViewById(R.id.lvResult4);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        pieChart2 = (PieChart) findViewById(R.id.pieChart2);
        pieChart3 = (PieChart) findViewById(R.id.pieChart3);
        pieChart4 = (PieChart) findViewById(R.id.pieChart4);
        barChart = (BarChart) findViewById(R.id.barChart);
        barChart2 = (BarChart) findViewById(R.id.barChart2);
        barChart3 = (BarChart) findViewById(R.id.barChart3);
        barChart4 = (BarChart) findViewById(R.id.barChart4);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart2 = (LineChart) findViewById(R.id.lineChart2);
        lineChart3 = (LineChart) findViewById(R.id.lineChart3);
        lineChart4 = (LineChart) findViewById(R.id.lineChart4);


//        txtBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        int vgraphic = 0, tcol1=0, tcol3=0;
        String vcol2="";

        Bundle bundle = this.getIntent().getExtras();
        //tvindicator.setText(bundle.getString("nombre_indicador"));
        tvindicator.setText("Official School Days");
        tvindicator2.setText("Official School Length");
        tvindicator3.setText("Literacy Classes per Week");
        tvindicator4.setText("Official Class Time");


        //data = conn.getGraphInfo(bundle.getString("nombre_indicador"));
        data = conn.getGraphInfo("Official School Days");
        if (conn.existTable(data.getTablename(), data.getFormulate())) {
            List<String[]> lista = conn.getAnswerFromQuery(data.getFormulate());
            try {
                setGraphOptions(lista, data.getVgraphic());
                //setGraphOptions(lista, 2);
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            lvResult.setAdapter(new ResultAdapter(this,R.layout.row_data_layout, lista));
        } else {
            Toast.makeText(getApplicationContext(), "There isn't data to show!!! ", Toast.LENGTH_LONG).show();
        }

        data2 = conn.getGraphInfo("Official School Length");
        if (conn.existTable(data2.getTablename(), data2.getFormulate())) {
            List<String[]> lista = conn.getAnswerFromQuery(data2.getFormulate());
            try {
                setGraphOptions2(lista, data2.getVgraphic());
                //setGraphOptions(lista, 2);
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            lvResult2.setAdapter(new ResultAdapter(this,R.layout.row_data_layout, lista));
        } else {
            Toast.makeText(getApplicationContext(), "There isn't data to show!!! ", Toast.LENGTH_LONG).show();
        }

        data3 = conn.getGraphInfo("Literacy Classes per Week");
        if (conn.existTable(data3.getTablename(), data3.getFormulate())) {
            List<String[]> lista = conn.getAnswerFromQuery(data3.getFormulate());
            try {
                setGraphOptions3(lista, data3.getVgraphic());
                //setGraphOptions(lista, 2);
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            lvResult3.setAdapter(new ResultAdapter(this,R.layout.row_data_layout, lista));
        } else {
            Toast.makeText(getApplicationContext(), "There isn't data to show!!! ", Toast.LENGTH_LONG).show();
        }

        data4 = conn.getGraphInfo("Official Class Time");
        if (conn.existTable(data4.getTablename(), data4.getFormulate())) {
            List<String[]> lista = conn.getAnswerFromQuery(data4.getFormulate());
            try {
                setGraphOptions4(lista, data4.getVgraphic());
                //setGraphOptions(lista, 2);
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            lvResult4.setAdapter(new ResultAdapter(this,R.layout.row_data_layout, lista));
        } else {
            Toast.makeText(getApplicationContext(), "There isn't data to show!!! ", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void home ()
    {
        finish();
    }

    private void setGraphOptions(List<String[]> information, int typeGraph) {
        switch (typeGraph) {
            case 1 : // pieChart
                drawPieGraph(information.get(1), information.get(2), COLORS);
                break;
            case 2 : // barChart;
                drawBarGraph(information.get(1), information.get(2), COLORS);
                //drawBarGraph2(INFO1, INFO2, COLORS);  // ************ String para manipular la informacion.
                break;
            case 3: //lineChart
                drawLineGraph(information.get(1), information.get(2), COLORS[0], "titulo");
                break;
        }
    }

    private void setGraphOptions2(List<String[]> information, int typeGraph) {
        switch (typeGraph) {
            case 1 : // pieChart
                drawPieGraph(information.get(1), information.get(2), COLORS);
                break;
            case 2: // barChart;
                drawBarGraph2(information.get(1), information.get(2), COLORS);
                //drawBarGraph2(INFO1, INFO2, COLORS);  // ************ String para manipular la informacion.
                break;
            case 3: //lineChart
                drawLineGraph(information.get(1), information.get(2), COLORS[0], "titulo");
                break;
        }
    }

    private void setGraphOptions3(List<String[]> information, int typeGraph) {
        switch (typeGraph) {
            case 1 : // pieChart
                drawPieGraph(information.get(1), information.get(2), COLORS);
                break;
            case 2: // barChart;
                drawBarGraph3(information.get(1), information.get(2), COLORS);
                //drawBarGraph2(INFO1, INFO2, COLORS);  // ************ String para manipular la informacion.
                break;
            case 3: //lineChart
                drawLineGraph(information.get(1), information.get(2), COLORS[0], "titulo");
                break;
        }
    }

    private void setGraphOptions4(List<String[]> information, int typeGraph) {
        switch (typeGraph) {
            case 1 : // pieChart
                drawPieGraph(information.get(1), information.get(2), COLORS);
                break;
            case 2: // barChart;
                drawBarGraph4(information.get(1), information.get(2), COLORS);
                //drawBarGraph2(INFO1, INFO2, COLORS);  // ************ String para manipular la informacion.
                break;
            case 3: //lineChart
                drawLineGraph(information.get(1), information.get(2), COLORS[0], "titulo");
                break;
        }
    }

    private void drawBarGraph(String [] nombres, String [] valores, int [] colors) {
        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        ArrayList<String> valueX = new ArrayList<String>();
        valueX.add("");
        ArrayList<BarDataSet> valuesY = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries;
        BarEntry entry;
        BarDataSet set;
        for (int i = 0; i < valores.length; i ++) {
            entry = new BarEntry(Float.parseFloat(valores[i]), 0);
            entries = new ArrayList<BarEntry>();

            entries.add(entry);
            set = new BarDataSet(entries, nombres[i]);
            set.setColor(colors[i]);
            valuesY.add(set);
        }

        BarData barData = new BarData(valueX,valuesY);
        barChart.setData(barData);
        barChart.setDescription("");
        legend = barChart.getLegend();
        legend.setTextColor(Color.parseColor("#9B9B9B"));
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle2));
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    private void drawBarGraph2(String [] nombres, String [] valores, int [] colors) {
        pieChart2.setVisibility(View.GONE);
        barChart2.setVisibility(View.VISIBLE);
        lineChart2.setVisibility(View.GONE);
        ArrayList<String> valueX = new ArrayList<String>();
        valueX.add("");
        ArrayList<BarDataSet> valuesY = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries;
        BarEntry entry;
        BarDataSet set;
        for (int i = 0; i < valores.length; i ++) {
            entry = new BarEntry(Float.parseFloat(valores[i]), 0);
            entries = new ArrayList<BarEntry>();

            entries.add(entry);
            set = new BarDataSet(entries, nombres[i]);
            set.setColor(colors[i]);
            valuesY.add(set);
        }

        BarData barData1 = new BarData(valueX,valuesY);
        barChart2.setData(barData1);
        barChart2.setDescription("");
        legend = barChart2.getLegend();
        legend.setTextColor(Color.parseColor("#9B9B9B"));
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle2));
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    private void drawBarGraph3(String [] nombres, String [] valores, int [] colors) {
        pieChart3.setVisibility(View.GONE);
        barChart3.setVisibility(View.VISIBLE);
        lineChart3.setVisibility(View.GONE);
        ArrayList<String> valueX = new ArrayList<String>();
        valueX.add("");
        ArrayList<BarDataSet> valuesY = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries;
        BarEntry entry;
        BarDataSet set;
        for (int i = 0; i < valores.length; i ++) {
            entry = new BarEntry(Float.parseFloat(valores[i]), 0);
            entries = new ArrayList<BarEntry>();

            entries.add(entry);
            set = new BarDataSet(entries, nombres[i]);
            set.setColor(colors[i]);
            valuesY.add(set);
        }

        BarData barData3 = new BarData(valueX,valuesY);
        barChart3.setData(barData3);
        barChart3.setDescription("");
        legend = barChart3.getLegend();
        legend.setTextColor(Color.parseColor("#9B9B9B"));
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle2));
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    private void drawBarGraph4(String [] nombres, String [] valores, int [] colors) {
        pieChart4.setVisibility(View.GONE);
        barChart4.setVisibility(View.VISIBLE);
        lineChart4.setVisibility(View.GONE);
        ArrayList<String> valueX = new ArrayList<String>();
        valueX.add("");
        ArrayList<BarDataSet> valuesY = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries;
        BarEntry entry;
        BarDataSet set;
        for (int i = 0; i < valores.length; i ++) {
            entry = new BarEntry(Float.parseFloat(valores[i]), 0);
            entries = new ArrayList<BarEntry>();

            entries.add(entry);
            set = new BarDataSet(entries, nombres[i]);
            set.setColor(colors[i]);
            valuesY.add(set);
        }

        BarData barData4 = new BarData(valueX,valuesY);
        barChart4.setData(barData4);
        barChart4.setDescription("");
        legend = barChart4.getLegend();
        legend.setTextColor(Color.parseColor("#9B9B9B"));
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle2));
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    private void drawPieGraph(String [] nombres, String [] valores, int [] colors) {
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i< valores.length ; i++) {
            yVals1.add(new Entry(Float.parseFloat(valores[i]), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();//array legend

        for (int i = 0; i < nombres.length; i++)
            xVals.add(nombres[i]);

        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(3f);
        set1.setColors(ColorTemplate.createColors(colors));

        PieData data = new PieData(xVals,set1);
        pieChart.setData(data);
        pieChart.setBackgroundColor(Color.TRANSPARENT);
        pieChart.setCenterTextColor(Color.TRANSPARENT);
        pieChart.setDescription("");
        legend = pieChart.getLegend();
        legend.setTextColor(Color.parseColor("#9B9B9B"));
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle));
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    private void drawLineGraph(String [] nombres, String [] valores, int color, String title) {
        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.VISIBLE);
        ArrayList<LineDataSet> valuesY1 = new ArrayList<LineDataSet>();
        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        Entry entry = null;
        for(int i =0; i < valores.length; i ++) {
            entry = new BarEntry(Float.parseFloat(valores[i]),i, nombres[i]);
            entries1.add(entry);
        }
        LineDataSet set2 = new LineDataSet(entries1,title);
        set2.setColor(color);
        valuesY1.add(set2);
        LineData lineData = new LineData(nombres, valuesY1) ;
        lineChart.setData(lineData);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                fl_location++;
                change_fl(fl_location);
                break;
            case R.id.btn_back:
                fl_location--;
                change_fl(fl_location);
                break;
            case R.id.btn_exit:
                //dialogAlert(2);
                finish();
                break;
        }
    }

    // *********** Control change page
    public void change_fl(Integer fl) {
        if (fl > 5) {fl_location=1;}
        if (fl < 1) {fl_location=4;}
        switch (fl_location){
            case 1:
                fl_part2.setVisibility(View.GONE);
                fl_part3.setVisibility(View.GONE);
                fl_part4.setVisibility(View.GONE);
                fl_part1.setVisibility(View.VISIBLE);
                break;
            case 2:
                fl_part1.setVisibility(View.GONE);
                fl_part3.setVisibility(View.GONE);
                fl_part4.setVisibility(View.GONE);
                fl_part2.setVisibility(View.VISIBLE);
                break;
            case 3:
                fl_part1.setVisibility(View.GONE);
                fl_part2.setVisibility(View.GONE);
                fl_part4.setVisibility(View.GONE);
                fl_part3.setVisibility(View.VISIBLE);
                break;
            case 4:
                fl_part1.setVisibility(View.GONE);
                fl_part2.setVisibility(View.GONE);
                fl_part3.setVisibility(View.GONE);
                fl_part4.setVisibility(View.VISIBLE);
                break;
        }
    }



}
