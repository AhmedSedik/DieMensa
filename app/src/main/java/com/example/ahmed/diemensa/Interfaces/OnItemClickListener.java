package com.example.ahmed.diemensa.Interfaces;

import android.view.View;

import com.example.ahmed.diemensa.Adapter.RecyclerAdapter;

public interface OnItemClickListener {


    void onItemClick(View v, int position);

    void onLikedButton(int position);

    void  onUnlikedButton(int position);

    void getLikes(int position);


}