package com.example.shiryabdo.onesearchapp;

import com.amulyakhare.textdrawable.TextDrawable;

/**
 * Created by falcon on 26/08/2017.
 */

public class DataModel {
    public  int text;
    public TextDrawable drawable;
    public String color;

    public DataModel(int t, TextDrawable d, String c )
    {
        text=t;
        drawable=d;
        color=c;
    }
}
