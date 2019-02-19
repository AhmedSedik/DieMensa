package com.example.ahmed.diemensa;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ahmed.diemensa.Adapter.RecyclerAdapter;
import com.example.ahmed.diemensa.Interfaces.OnItemClickListener;
import com.example.ahmed.diemensa.Model.Dish;
import com.example.ahmed.diemensa.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class RecyclerConfig {
    private Context mContext;
    private OnItemClickListener mListener;
    public static RecyclerAdapter recyclerAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, final List<Dish> dishes){
        mContext = context;


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAnimation(null);

        recyclerAdapter = new RecyclerAdapter(context, dishes);

        recyclerAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(recyclerAdapter);


    }


}
