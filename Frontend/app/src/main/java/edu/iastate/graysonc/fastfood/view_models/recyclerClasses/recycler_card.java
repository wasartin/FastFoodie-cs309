package edu.iastate.graysonc.fastfood.view_models.recyclerClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class recycler_card implements Parcelable {
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

    protected recycler_card(Parcel in) {
        mLine1 = in.readString();
        mLine2 = in.readString();
        favored = in.readByte() != 0;
    }

    public static final Creator<recycler_card> CREATOR = new Creator<recycler_card>() {
        @Override
        public recycler_card createFromParcel(Parcel in) {
            return new recycler_card(in);
        }

        @Override
        public recycler_card[] newArray(int size) {
            return new recycler_card[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLine1);
        dest.writeString(mLine2);
        dest.writeValue(favored);
    }
}
