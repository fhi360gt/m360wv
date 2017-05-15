package com.org.fhi360.m360wv.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 5/13/17.
 */

public class ODKInstances {
    private int _id;
    private String displayName, submissionUri, canEditWhenComplete, instanceFilePath , jrFormId , jrVersion,
            status, date, displaySubtext;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSubmissionUri() {
        return submissionUri;
    }

    public void setSubmissionUri(String submissionUri) {
        this.submissionUri = submissionUri;
    }

    public String getCanEditWhenComplete() {
        return canEditWhenComplete;
    }

    public void setCanEditWhenComplete(String canEditWhenComplete) {
        this.canEditWhenComplete = canEditWhenComplete;
    }

    public String getInstanceFilePath() {
        return instanceFilePath;
    }

    public void setInstanceFilePath(String instanceFilePath) {
        this.instanceFilePath = instanceFilePath;
    }

    public String getJrFormId() {
        return jrFormId;
    }

    public void setJrFormId(String jrFormId) {
        this.jrFormId = jrFormId;
    }

    public String getJrVersion() {
        return jrVersion;
    }

    public void setJrVersion(String jrVersion) {
        this.jrVersion = jrVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplaySubtext() {
        return displaySubtext;
    }

    public void setDisplaySubtext(String displaySubtext) {
        this.displaySubtext = displaySubtext;
    }

    public static List<ODKInstances> getODKInstancesFromCursor (Cursor c) {
        List<ODKInstances> result = new ArrayList<ODKInstances>();
        ODKInstances temp;
        c.moveToFirst();
        while(!c.isAfterLast()) {
            temp = new ODKInstances();
            temp.set_id(c.getInt(c.getColumnIndex("_id")));
            temp.setDisplayName(c.getString(c.getColumnIndex("displayName")));
            temp.setSubmissionUri(c.getString(c.getColumnIndex("submissionUri")));
            temp.setCanEditWhenComplete(c.getString(c.getColumnIndex("canEditWhenComplete")));
            temp.setInstanceFilePath(c.getString(c.getColumnIndex("instanceFilePath")));
            temp.setJrFormId(c.getString(c.getColumnIndex("jrFormId")));
            temp.setJrVersion(c.getString(c.getColumnIndex("jrVersion")));
            temp.setStatus(c.getString(c.getColumnIndex("status")));
            temp.setDate(c.getString(c.getColumnIndex("date")));
            temp.setDisplaySubtext(c.getString(c.getColumnIndex("displaySubtext")));
            result.add(temp);
            c.moveToNext();
        }
        return result;
    }
}