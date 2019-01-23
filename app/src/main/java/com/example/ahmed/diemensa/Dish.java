package com.example.ahmed.diemensa;

public class Dish {

    private final String mPlace;
    //private final int mWeak;
   // private final String mWeakday;
    private final String mDayTime;
    private final String mDish;
    private final String mComponent;
    private final double mPrice;
    private int mImageResId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

  /*  public Dish(String place, int weak,String weakday,String dayTime,String dish,String component,double price ){
        mPlace = place;
        mWeak = weak;
        mWeakday = weakday;
        mDayTime = dayTime;
        mDish = dish;
        mComponent = component;
        mPrice = price;
    }
    public Dish(String place, int weak,String weakday,String dayTime,String dish,String component,double price,int imageResId ){
        mPlace = place;
        mWeak = weak;
        mWeakday = weakday;
        mDayTime = dayTime;
        mDish = dish;
        mComponent = component;
        mPrice = price;
        mImageResId = imageResId;
    }*/
  /*public Dish(String place){
      mPlace = place;
  }*/

    public Dish(String place, String daytime, String dishName, String component, double price) {
        mPlace = place;
        mDayTime = daytime;
        mDish = dishName;
        mComponent = component;
        mPrice = price;
    }
    public Dish(String place, String daytime, String dishName, String component, double price,int imageResId) {
        mPlace = place;
        mDayTime = daytime;
        mDish = dishName;
        mComponent = component;
        mPrice = price;
        mImageResId = imageResId;
    }

    public String getmPlace(){
        return mPlace;
    }

   /* public int getmWeak() {
        return mWeak;
    }

    public String getmWeakday() {
        return mWeakday;
    }*/

    public String getmDayTime() {
        return mDayTime;
    }

    public String getmDish() {
        return mDish;
    }

    public double getmPrice() {
        return mPrice;
    }

    public int getmImageResId() {
        return mImageResId;
    }
    public boolean hasImage(){
        return mImageResId != NO_IMAGE_PROVIDED;
    }


    public String getmComponent() {
        return mComponent;
    }
}
