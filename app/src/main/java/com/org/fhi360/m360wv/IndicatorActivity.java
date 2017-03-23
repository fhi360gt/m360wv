package com.org.fhi360.m360wv;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
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
public class IndicatorActivity extends Activity //implements View.OnClickListener
{
    public ArrayList<String> Result = new ArrayList<>();
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private PieChart pieChart;
    private LineChart lineChart;
    private BarChart barChart;
    private TextView tvindicator, txtBack;
    private ListView lvResult;
    private DBAnalyticsUtils conn;
    private GraphInfo data;
    //private final static int [] COLORS = new int[] {Color.GREEN,Color.YELLOW, Color.RED, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.WHITE};
    //private final static int [] COLORS = new int[] {Color.parseColor("#F47321"),Color.parseColor("#FFA909"), Color.parseColor("#BA9C64"), Color.MAGENTA, Color.CYAN, Color.GRAY, Color.WHITE};
    // paleta de colores Rolando
    //private final static int [] COLORS = new int[] {Color.parseColor("#F47321"),Color.parseColor("#FFA909"), Color.parseColor("#06AD55"), Color.parseColor("#54D9B9"), Color.parseColor("#BA9C64"), Color.parseColor("#06AD55"), Color.parseColor("#FF8679")};
    // nueva paleta de colores
    private final static int [] COLORS = new int[] {Color.parseColor("#FF3300"),Color.parseColor("#132595"), Color.parseColor("#06AD55"), Color.parseColor("#F47321"), Color.parseColor("#BA9C64"), Color.parseColor("#06AD55"), Color.parseColor("#FF8679")};
    private final static String [] INFO1 = new String[] {"Prom","Las Year","This year"};
    private final static String [] INFO2 = new String[] {"2.5","2","3"};
    private Legend legend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicator_layout);
        conn = new DBAnalyticsUtils(this);
        tvindicator = (TextView)findViewById(R.id.tvindicator);
        txtBack = (TextView) findViewById(R.id.homebtn);
        lvResult = (ListView) findViewById(R.id.lvResult);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        barChart = (BarChart) findViewById(R.id.barChart);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int vgraphic = 0, tcol1=0, tcol3=0;
        String vcol2="";

        Bundle bundle = this.getIntent().getExtras();
        tvindicator.setText(bundle.getString("nombre_indicador"));

        data = conn.getGraphInfo(bundle.getString("nombre_indicador"));


        if (conn.existTable(data.getTablename(), data.getFormulate())) {
            List<String[]> lista = conn.getAnswerFromQuery(data.getFormulate());



            try {
                setGraphOptions(lista, data.getVgraphic());
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            lvResult.setAdapter(new ResultAdapter(this,R.layout.row_data_layout, lista));
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
                //drawBarGraph(INFO1, INFO2, COLORS);  // ************ String para manipular la informacion.
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
        legend.setTextSize(getResources().getDimension(R.dimen.legendTitle));
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
}
