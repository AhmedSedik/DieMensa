package com.example.ahmed.diemensa;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MondayDishAvtivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MondayDishAvtivity.class.getName();
    public static final String REQUEST_URL = "https://api.jsonbin.io/b/5c415f3381fe89272a8ef7cd/4";

    private DishAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_montag);
        List<Dish> dishes = new ArrayList<>();

        ListView dishListView = findViewById(R.id.list_dish);


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(1,null,this);


        }

        if (mAdapter==null){
            mAdapter = new DishAdapter(this,new ArrayList<Dish>());
            dishListView.setAdapter(mAdapter);
        }
        Log.e(LOG_TAG,"Initializing the Loader");

    }


    @Override
    public Loader<List<Dish>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG,"Initializing OnCreate Loader");
       // container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        //container.startShimmerAnimation();
        return new DishLoader(this,REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Dish>> loader, List<Dish> dishes ) {
        Log.e(LOG_TAG,"Initializing onFinished");
        //mAdapter.clear();
        Log.e(LOG_TAG,"Initializing onFinished Cleat Adapter");
        Log.e(LOG_TAG,"Loading Animation Stoped");

        if (dishes != null && !dishes.isEmpty()) {
            mAdapter.addAll(dishes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dish>> loader) {
        Log.e(LOG_TAG,"Initializing onLoaderReset");
        mAdapter.clear();
        Log.e(LOG_TAG,"Initializing onLoaderReset Clear Adapter");
    }
}
