package com.org.fhi360.m360wv.data;

public class GraphInfo {
    private int id;
    private String instrument;
    private String indicator;
    private String tablename;
    private String formulate;
    private int vgraphic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getFormulate() {
        return formulate;
    }

    public void setFormulate(String formulate) {
        this.formulate = formulate;
    }

    public int getVgraphic() {
        return vgraphic;
    }

    public void setVgraphic(int vgraphic) {
        this.vgraphic = vgraphic;
    }
}
