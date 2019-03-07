package edu.iastate.graysonc.fastfood.view_models.recyclerClasses;

public class recycler_card {
    private String mLine1;
    private String mLine2;
    private boolean favored;


    public recycler_card(String Food, String Misc_Data){
        this(Food, Misc_Data,true);
    }
    public recycler_card(String Food, String Misc_Data, boolean fav){
        mLine1=Food;
        mLine2=Misc_Data;
        favored=fav;
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

    public void setFavored(boolean val){favored=val;}
}
