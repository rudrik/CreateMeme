package com.apptechx.creatememe.model;

/**
 * Created by AppTechX on 10/15/2014.
 */
public class SpinnerNavItem {

    private String title;
    private int icon;

    public SpinnerNavItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }
}