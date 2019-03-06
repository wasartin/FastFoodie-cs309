package edu.iastate.graysonc.fastfood.view_models.recyclerClasses;

import android.media.Image;
import android.widget.ImageButton;

public class recyler_card {
    private String mLine1;
    private String mLine2;
    private boolean favored;


    public recyler_card(String Food, String Misc_Data){
        this(Food, Misc_Data,true);
    }
    public recyler_card(String Food, String Misc_Data, boolean favored){
        mLine1=Food;
        mLine2=Misc_Data;
        this.favored=favored;
    }

    public String getFood() {
        return mLine1;
    }

    public String getData() {
        return mLine2;
    }

    public boolean isFavored() {
        return favored;
    }
}
