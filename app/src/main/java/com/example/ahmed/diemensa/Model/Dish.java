package com.example.ahmed.diemensa.Model;

public class Dish {
    private int id;
    private  long date;
    //private final int mWeak;

    private String weakday;
    private String daytime;

    private String dish;
    private String component;
    private String key;
    private  double price;
    private int likes;
    private int iconid_1;
    private int iconid_2;
    private boolean liked = false;
    private boolean showShimmer;

    

  public Dish(){

  }


    public Dish(int id, long date, String weakday, String daytime,
                String dish, String component,
                double price, int likes, int iconid_1, int iconid_2, String key) {
        this.id = id;
        this.date = date;
        this.weakday = weakday;
        this.daytime = daytime;
        this.dish = dish;
        this.component = component;
        this.price = price;
        this.likes = likes;
        this.iconid_1 = iconid_1;
        this.iconid_2 = iconid_2;
        this.key = key;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getWeakday() {
        return weakday;
    }

    public void setWeakday(String weakday) {
        this.weakday = weakday;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIconid_1() {
        return iconid_1;
    }

    public void setIconid_1(int iconid_1) {
        this.iconid_1 = iconid_1;
    }

    public int getIconid_2() {
        return iconid_2;
    }

    public void setIconid_2(int iconid_2) {
        this.iconid_2 = iconid_2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isShowShimmer() {
        return showShimmer;
    }

    public void setShowShimmer(boolean showShimmer) {
        this.showShimmer = showShimmer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dish other = (Dish) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
