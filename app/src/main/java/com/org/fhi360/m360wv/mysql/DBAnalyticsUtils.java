package com.org.fhi360.m360wv.mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.org.fhi360.m360wv.data.GraphInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 3/31/15.
 */
public class DBAnalyticsUtils {
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private static final String DB_NAME = STATICS_ROOT + File.separator + "analytics.db";
    private static final int DB_INDICATORS_VERSION = 4;

    public static final String TABLE_NAME ="tblindicators";
    public static final String TABLE_NAME_SyncForm ="syncforms";
    public static final String TABLE_TBLRESULT = "tblresults";

    // ************** tblindicators TABLE ****************
    public static final String CN_ID = "id";
    public static final String CN_INSTRUMENT = "instrument";
    public static final String CN_INDICATOR = "nameindicator";
    public static final String CN_TABLENAME = "tablename";
    public static final String CN_FORMULATE = "formulate";
    public static final String CN_VGRAPHIC = "vgraphic";

    // ************** tblindicators TABLE ****************
    public static final String CN_ID_SF = "id";
    public static final String CN_Iname = "form_id";
    public static final String CN_Fname = "form_name";
    // *************** tblresults TABLE *******************
    public static final String SCHOOL_CODE = "school_code";

    private DBAnalyticsHelper dbHelper;
    private Context context;

    public DBAnalyticsUtils(Context context) {
        this.context = context;
        dbHelper = new DBAnalyticsHelper(context);
    }

    public void deleteIndicators() {
        if (dbHelper==null) {
            dbHelper = new DBAnalyticsHelper(context);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_NAME);
        db.close();
    }

    public boolean existTable(String tableName, String query) {
        if ((query.trim().isEmpty()) || tableName.trim().isEmpty()) {
            return false;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT tbl_name,count(*) FROM sqlite_master WHERE tbl_name='" + tableName + "'", null);
        boolean flag =(c.getCount() > 0);
        db.close();
        return flag;
    }

    public List<String[]> getAnswerFromQuery(String query) {
        ArrayList<String[]> lista = new ArrayList<String []> ();
        int cant;
        List<String> cantidad = new ArrayList<String>();
        List<String> nombres= new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        List<String> titulos = new ArrayList<String>();
        List<String> segundoValor = new ArrayList<String>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        cant = Integer.parseInt(c.getString(0));
        cantidad.add(cant + "");
        for (int i = 1; i <= cant; i ++) {
            nombres.add(c.getColumnName(i));
            values.add(c.getString(i));
        }
        titulos.add(c.getString(cant + 1));
        lista.add(cantidad.toArray(new String[cantidad.size()]));
        lista.add(nombres.toArray(new String[nombres.size()]));
        lista.add(values.toArray(new String[values.size()]));
        if (c.getColumnCount() > cant + 2) {
            for (int i = cant + 2; i <= (2 * cant + 1); i++) {
                segundoValor.add(c.getString(i));
            }
            titulos.add(c.getString(2 * cant + 2));
            lista.add(titulos.toArray(new String[titulos.size()]));
            lista.add(segundoValor.toArray(new String[segundoValor.size()]));
        } else {
            lista.add(titulos.toArray(new String[titulos.size()]));
        }
        c.close();
        db.close();


        return lista;
    }

    public GraphInfo getGraphInfo(String nameIndicator) {
        GraphInfo data = new GraphInfo();
        SQLiteDatabase dbAnalytics = dbHelper.getWritableDatabase();
        Cursor cursor = dbAnalytics.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators WHERE nameindicator LIKE '"+nameIndicator+"'", null);
        if (cursor != null) {
            cursor.moveToFirst();
            data.setId(cursor.getInt(0));
            data.setInstrument(cursor.getString(1));
            data.setIndicator(cursor.getString(2));
            data.setTablename(cursor.getString(3));
            data.setFormulate(cursor.getString(4));
            data.setVgraphic(cursor.getInt(5));
        }
        return data;
    }

    public List<ArrayList<String>> getDashboardData(String query) {
        int cantidad =0;
        List<ArrayList<String>> resultado = new ArrayList<ArrayList<String>>();
        resultado.add(new ArrayList<String>());
        resultado.get(0).add("Category");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            cantidad = c.getInt(0);
            for (int i = 0; i < cantidad; i ++) {
                resultado.add(new ArrayList<String>());
                resultado.get(i + 1).add(c.getColumnName(i+1));
                resultado.get(i + 1).add(c.getString(i+1));
                resultado.get(i + 1).add(c.getString(cantidad + i + 2));
            }

        }
        resultado.get(0).add(c.getString(cantidad +1));
        resultado.get(0).add(c.getString(2 * cantidad + 2));
        c.close();
        db.close();
        return resultado;
    }
    public ArrayList<String> getAnalyticsOptions (String instrumentName) {
        ArrayList<String> opcion=new ArrayList<String>();
        SQLiteDatabase dbAnalytics = dbHelper.getWritableDatabase();

        Cursor cursor = dbAnalytics.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators WHERE instrument LIKE '"+instrumentName+"'", null);

        if (cursor.moveToFirst()) {
            do {
                opcion.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return opcion;
    }

    public ArrayList<String> getCollectOptionsTitle (String instrumentName) {
        ArrayList<String> opcion=new ArrayList<String>();
        SQLiteDatabase dbAnalytics = dbHelper.getWritableDatabase();

        Cursor cursor = dbAnalytics.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators WHERE instrument LIKE '"+instrumentName+"'", null);

        if (cursor.moveToFirst()) {
            do {
                opcion.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return opcion;
    }

    public ArrayList<String> getCollectOptionsForms (String instrumentName) {
        ArrayList<String> opcion=new ArrayList<String>();
        SQLiteDatabase dbAnalytics = dbHelper.getWritableDatabase();

        Cursor cursor = dbAnalytics.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators WHERE instrument LIKE '"+instrumentName+"'", null);

        if (cursor.moveToFirst()) {
            do {
                opcion.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return opcion;
    }

    public void insertIndicators(String id, String instrument, String nameindicator, String tablename, String formulate, String vgraphic) {
        if (dbHelper==null) {
            dbHelper = new DBAnalyticsHelper(context);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("Insert into " + TABLE_NAME + " ( " + CN_ID + "," + CN_INSTRUMENT + "," + CN_INDICATOR + "," + CN_TABLENAME + "," + CN_FORMULATE + "," + CN_VGRAPHIC +
                ") VALUES ('" + id + "','" + instrument + "','" + nameindicator + "','" + tablename + "','" + formulate + "'," + vgraphic + ")");
        db.close();
    }

    public void insertIndicatorsFromCursor(Cursor c) {

        if (c.moveToFirst()) {
            do {
                insertIndicators(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            } while (c.moveToNext());
        }
    }

    private class DBAnalyticsHelper extends SQLiteOpenHelper {

        public DBAnalyticsHelper(Context context) {
            super(context, DB_NAME, null, DB_INDICATORS_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String createTable = "create table " + TABLE_NAME + " ("
                    + CN_ID + " integer primary key autoincrement,"
                    + CN_INSTRUMENT + " text not null,"
                    + CN_INDICATOR + " text not null,"
                    + CN_TABLENAME + " text not null,"
                    + CN_FORMULATE + " text not null,"
                    + CN_VGRAPHIC + " integer);";
            db.execSQL(createTable);

            final String createTable_SF = "create table " + TABLE_NAME_SyncForm + " ("
                    + CN_ID_SF + " integer primary key autoincrement,"
                    + CN_Iname + " text not null,"
                    + CN_Fname + " text not null);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public ArrayList<String> getSchools (String instrumentName) {
        ArrayList<String> opcion=new ArrayList<String>();
        SQLiteDatabase dbAnalytics = dbHelper.getWritableDatabase();

        Cursor cursor = dbAnalytics.rawQuery("SELECT id, instrument, nameindicator, tablename, formulate, vgraphic  FROM tblindicators WHERE instrument LIKE '"+instrumentName+"'", null);

        if (cursor.moveToFirst()) {
            do {
                opcion.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return opcion;
    }


}
