package com.org.fhi360.m360wv.mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Environment;

import com.org.fhi360.m360wv.data.ODKInstances;

import java.io.File;
import java.util.List;

/**
 * Created by jalfaro on 5/13/17.
 */

public class DBInstanceUtils {
    private static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private static final String DB_NAME = STATICS_ROOT + File.separator + "instances.db";
    private static final int DB_SCHEME_VERSION=4;

    private DBHelper conn;
    private Context context;

    public DBInstanceUtils(Context context) {
        conn = new DBHelper(context);
        this.context = context;
    }


    public List<ODKInstances> getInstances(String displayName) {
        List<ODKInstances> instances = null;
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("Select _id, displayName, submissionUri, canEditWhenComplete, instanceFilePath , jrFormId , jrVersion," +
                "status, date, displaySubtext from instances where displayName ='" + displayName + "'", null);
        if (c != null) {
            instances = ODKInstances.getODKInstancesFromCursor(c);
            c.close();
        }
        db.close();
        return instances;
    }
    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_SCHEME_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}