package com.example.ahmed.diemensa;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

public class MondayDishActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MondayDishActivity.class.getName();
    public static final String REQUEST_URL = "https://api.jsonbin.io/b/5c415f3381fe89272a8ef7cd/4";

    private DishAdapter mAdapter;

    LoaderManager loaderManager;

    ArrayAdapter<CharSequence> adapter;

    Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);
        //List<Dish> dishes = new ArrayList<>();
        getSupportActionBar().setTitle("Montag");


        ListView dishListView = findViewById(R.id.list_dish_montag);


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

         spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Days:");
        adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);
        if(spinner.getSelectedItemId()==0){
            spinner.setSelection(0);
        }


        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                    Toast.makeText(MondayDishActivity.this, "Montag Activity", Toast.LENGTH_SHORT).show();

                }
                if(i==1) {
                    Toast.makeText(MondayDishActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MondayDishActivity.this, TuesdayActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e(LOG_TAG, "DienstagActivity");
                }
              /*  switch (i){

                    case 1:
                        Toast.makeText(MondayDishActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MondayDishActivity.this,TuesdayActivity.class);
                        startActivity(intent);
                        Log.e(LOG_TAG,"DienstagActivity");
                    case 2:
                        Intent intent2 = new Intent(MondayDishActivity.this,WednessdayActivity.class);
                        startActivity(intent2);
                        Log.e(LOG_TAG,"Wednessday Activity");

                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e(LOG_TAG,"onNothing Selected ");
            }
        });
        return true;
    }
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart: MondayActivity ");
        super.onStart();
    }
    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop: MontagActivity");
        super.onStop();

    }
    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause: MontagActivity");
        super.onPause();
    }
}
