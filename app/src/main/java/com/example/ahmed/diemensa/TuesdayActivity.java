package com.example.ahmed.diemensa;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TuesdayActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MondayDishActivity.class.getName();
    public static final String REQUEST_URL = "https://api.jsonbin.io/b/5c42d92e2c87fa273072831f/1";

    private DishAdapter mAdapter;

    LoaderManager loaderManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);
        //List<Dish> dishes = new ArrayList<>();

        getSupportActionBar().setTitle("Dienstag");

        ListView dishListView = findViewById(R.id.list_tuesday);


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){

            loaderManager = getLoaderManager();

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
        mAdapter.clear();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dish,menu);

        MenuItem item = menu.findItem(R.id.spinner);

        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Days:");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();


        spinner.setAdapter(adapter);

        Log.d(LOG_TAG, "Item number: "+ spinner.getSelectedItemId());
        if (spinner.getSelectedItemId()==0){
            spinner.setSelection(1);
        }




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Log.e(LOG_TAG,"Tuesday Activity case 0");
                        Intent intent = new Intent(TuesdayActivity.this,MondayDishActivity.class);
                        startActivity(intent);
                        finish();
                    case 1:
                      //  Intent intent = new Intent(TuesdayActivity.this,MondayDishActivity.class);
                       // startActivity(intent);
                        Log.e(LOG_TAG,"Montag Activity");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart: DienstagActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop: DienstagActivity");
        super.onStop();

    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause: DienstagActivity");
        super.onPause();
    }
}
