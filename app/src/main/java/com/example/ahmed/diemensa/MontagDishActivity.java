package com.example.ahmed.diemensa;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public class MontagDishActivity extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MontagDishActivity.class.getName();
    public static final String REQUEST_URL = "https://api.jsonbin.io/b/5c415f3381fe89272a8ef7cd/4";

    private DishAdapter mAdapter;

    ShimmerFrameLayout container;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dish_montag,container,false);

        ListView dishListView = view.findViewById(R.id.list_dish);

        LoaderManager loaderManager = getActivity().getLoaderManager();



        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){



            loaderManager.initLoader(1,null,this);

        }



        if (mAdapter==null){
            mAdapter = new DishAdapter(getActivity(),new ArrayList<Dish>());
            dishListView.setAdapter(mAdapter);
        }



        Log.e(LOG_TAG,"Initializing the Loader Montag");

        return view;
    }

    @Override
    public Loader<List<Dish>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG,"Initializing OnCreate Loader Montag");


        return new DishLoader(getActivity(),REQUEST_URL);


    }

    @Override
    public void onLoadFinished(Loader<List<Dish>> loader, List<Dish> dishes ) {
        Log.e(LOG_TAG,"Initializing onFinished Montag");
        mAdapter.clear();
        Log.e(LOG_TAG,"Initializing onFinished Montag Clear Adapter");
        //container.stopShimmerAnimation();
        Log.e(LOG_TAG,"Loading Animation Stoped Montag");

       if (dishes != null && !dishes.isEmpty()) {
            mAdapter.addAll(dishes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dish>> loader) {
        Log.e(LOG_TAG,"Initializing onLoaderReset Montag");
        mAdapter.clear();
        Log.e(LOG_TAG,"Initializing onLoaderReset Montag Clear Adapter");
    }
}