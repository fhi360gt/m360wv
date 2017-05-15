package com.org.fhi360.m360wv.mysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.org.fhi360.m360wv.data.ODKForms;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 5/18/15.
 */
public class DBFormsUtils {
    private static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "odk/metadata";
    private static final int DB_SCHEME_VERSION=4;
    private static final String DB_NAME = STATICS_ROOT + File.separator + "forms.db";

    private DBHelper conn;
    private Context context;

    public DBFormsUtils(Context ctx) {
        context = ctx;
        conn = new DBHelper(ctx);
    }

    public Long getFormId(String name) {
        Long value = 0L;
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT _id FROM forms WHERE displayName ='" + name + "'", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            value = c.getLong(0);
        }
        c.close();
        db.close();
        return value;
    }
    public List<ODKForms> getForms() {
        List<ODKForms> data = new ArrayList<ODKForms>();
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT _id, displayName, displaySubtext, description, jrFormId, jrVersion, md5Hash, date , formMediaPath, formFilePath, language, submissionUri, base64RsaPublicKey, jrcacheFilePath FROM forms WHERE displayName like 'REACH%'",null);
        if (c != null) {
            data = ODKForms.getODKFormsFromCursor(c);
            c.close();
        }
        db.close();
        return data;
    }

    public List<ODKForms> getFormsVW() {
        List<ODKForms> data = new ArrayList<ODKForms>();
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT _id, displayName, displaySubtext, description, jrFormId, jrVersion, md5Hash, date , formMediaPath, formFilePath, language, submissionUri, base64RsaPublicKey, jrcacheFilePath FROM forms WHERE displayName like 'WV%'",null);
        if (c != null) {
            data = ODKForms.getODKFormsFromCursor(c);
        }
        return data;
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