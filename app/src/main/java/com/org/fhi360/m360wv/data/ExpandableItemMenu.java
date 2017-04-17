package com.org.fhi360.m360wv.data;

/**
 * Created by jlgarcia on 16/04/2017.
 */

public class ExpandableItemMenu {

    private String text;
    private int image;

    public ExpandableItemMenu(int image, String text) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
