package com.ala.elearning.Beans;

/**
 * Created by alaam on 11/20/2017.
 */

public class Paragraph {
    private String text,imgurl;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Paragraph(String text) {
        this.text = text;
    }

    public Paragraph() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
