package com.example.ahmed.diemensa;

public class Dish {



    private final long mDate;
    //private final int mWeak;
    private  String mDay;
    private final String mDayTime;
    private final String mDish;
    private final String mComponent;
    private final double mPrice;
    private int mImageResId1 = NO_IMAGE_PROVIDED;
    private int mImageResId2 = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

  /*public Dish(String place){
      mPlace = place;
  }*/

    public Dish(long mDate, String place, String daytime, String dishName, String component, double price) {
        this.mDate = mDate;
        mPlace = place;
        mDayTime = daytime;
        mDish = dishName;
        mComponent = component;
        mPrice = price;
    }
    public Dish(String place, String daytime, String dishName, String component, double price,
                String weakday,long date,int imageResid,int imageResId2) {
        mPlace = place;
        mDayTime = daytime;
        mDish = dishName;
        mComponent = component;
        mPrice = price;
        mDay = weakday;
        mDate = date;
        mImageResId1 = imageResid;
        mImageResId2 = imageResId2;




    }

    public String getmPlace(){
        return mPlace;
    }



    public String getmDay() {
        return mDay;
    }

    public String getmDayTime() {
        return mDayTime;
    }

    public String getmDish() {
        return mDish;
    }

    public double getmPrice() {
        return mPrice;
    }

    public int getmImageResId1() {
        return mImageResId1;
    }

    public int getmImageResId2() {
        return mImageResId2;
    }

    public boolean hasImage(){
        return mImageResId1 != NO_IMAGE_PROVIDED;
    }


    public String getmComponent() {
        return mComponent;
    }

    private final String mPlace;

    public long getmDate() {
        return mDate;
    }
}
