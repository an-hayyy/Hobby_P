package com.ex.hobby.ui.com;

public class ComListItem {
    private String stitle,sletter;
    private String simg;

    public void setTitle(String title) {
        this.stitle = title;
    }

    public void setImg(String img) {
        this.simg = img;
    }


    public void setLetter(String letter) {
        this.sletter = letter;
    }


    public String getTitle()
    {
        return this.stitle;
    }


    public String getLetter()
    {
        return this.sletter;
    }

    public String getImg()
    {
        return this.simg;
    }

}
