package com.org.fhi360.m360wv.mysql;

/**
 * Created by George on 23/02/2015.
 */
public class ConIndicator {
    private int id, valueformulate;
    private String instrument, nameindicator, tablename, formulate;

    public int getid() {
        return id;
    }
    public void setid(int id) {
        this.id = id;
    }
    public String getinstrument() {
        return instrument;
    }
    public void setinstrument(String instrument) {
        this.instrument = instrument;
    }
    public String getnameindicator() {
        return nameindicator;
    }
    public void setnameindicator(String nameindicator) {
        this.nameindicator = nameindicator;
    }
    public String gettablename() {
        return tablename;
    }
    public void settablename(String tablename) {
        this.tablename = tablename;
    }
    public String getformulate() {
        return formulate;
    }
    public void setformulate(String formulate) {
        this.formulate = formulate;
    }
    public int getvalueformulate() {
        return valueformulate;
    }
    public void setvalueformulate(int valueformulate) {
        this.valueformulate = valueformulate;
    }

    public ConIndicator(int id, String instrument, String nameindicator, String tablename, String formulate, int valueformulate) {
        super();
        this.id = id;
        this.instrument = instrument;
        this.nameindicator = nameindicator;
        this.tablename = tablename;
        this.formulate = formulate;
        this.valueformulate = valueformulate;
    }

    @Override
    public String toString() {
        return id  + " " + instrument  + " " + nameindicator  + " " + tablename  + " " + formulate  + " " + valueformulate;
    }
}

