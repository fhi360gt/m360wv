package com.org.fhi360.m360wv.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jalfaro on 5/18/15.
 */
public class ODKForms {
    //BDD ODKForms
    private int _id;
    private String displayName, displaySubtext, description, jrFormId, jrVersion,
            md5Hash, date , formMediaPath, formFilePath, language, submissionUri,
            base64RsaPublicKey, jrcacheFilePath;

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

    public String getDisplaySubtext() {
        return displaySubtext;
    }

    public void setDisplaySubtext(String displaySubtext) {
        this.displaySubtext = displaySubtext;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormMediaPath() {
        return formMediaPath;
    }

    public void setFormMediaPath(String formMediaPath) {
        this.formMediaPath = formMediaPath;
    }

    public String getFormFilePath() {
        return formFilePath;
    }

    public void setFormFilePath(String formFilePath) {
        this.formFilePath = formFilePath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubmissionUri() {
        return submissionUri;
    }

    public void setSubmissionUri(String submissionUri) {
        this.submissionUri = submissionUri;
    }

    public String getBase64RsaPublicKey() {
        return base64RsaPublicKey;
    }

    public void setBase64RsaPublicKey(String base64RsaPublicKey) {
        this.base64RsaPublicKey = base64RsaPublicKey;
    }

    public String getJrcacheFilePath() {
        return jrcacheFilePath;
    }

    public void setJrcacheFilePath(String jrcacheFilePath) {
        this.jrcacheFilePath = jrcacheFilePath;
    }

    public static List<ODKForms> getODKFormsFromCursor (Cursor c) {
        List<ODKForms> result = new ArrayList<ODKForms>();
        ODKForms temp;
        c.moveToFirst();
        while(!c.isAfterLast()) {
            temp = new ODKForms();
            temp.set_id(c.getInt(c.getColumnIndex("_id")));
            temp.setBase64RsaPublicKey(c.getString(c.getColumnIndex("base64RsaPublicKey")));
            temp.setDate(c.getString(c.getColumnIndex("date")));
            temp.setDescription(c.getString(c.getColumnIndex("description")));
            temp.setDisplayName(c.getString(c.getColumnIndex("displayName")));
            temp.setDisplaySubtext(c.getString(c.getColumnIndex("displaySubtext")));
            temp.setFormFilePath(c.getString(c.getColumnIndex("formFilePath")));
            temp.setFormMediaPath(c.getString(c.getColumnIndex("formMediaPath")));
            temp.setJrcacheFilePath(c.getString(c.getColumnIndex("jrcacheFilePath")));
            temp.setJrFormId(c.getString(c.getColumnIndex("jrFormId")));
            temp.setJrVersion(c.getString(c.getColumnIndex("jrVersion")));
            temp.setLanguage(c.getString(c.getColumnIndex("language")));
            temp.setMd5Hash(c.getString(c.getColumnIndex("md5Hash")));
            temp.setSubmissionUri(c.getString(c.getColumnIndex("submissionUri")));
            result.add(temp);
            c.moveToNext();
        }
        return result;
    }
}
