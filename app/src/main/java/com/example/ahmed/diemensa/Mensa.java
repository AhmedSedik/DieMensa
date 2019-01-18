package com.example.ahmed.diemensa;

public class Mensa {
    private String mTitle;
    private int mResId;
    private String mUrl;

    public Mensa(String title, int resId){
        mTitle = title;
        mResId = resId;
    }

    public String getmTitle(){
        return mTitle;
    }
    public int getmResId(){
        return mResId;
    }
    public String getmUrl(){
        return mUrl;
    }


}
