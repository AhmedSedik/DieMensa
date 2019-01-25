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

public class WednessdayActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>>{


    public static final String LOG_TAG = MondayActivity.class.getName();
    public static final String REQUEST_URL = "https://api.jsonbin.io/b/5c432a577b31f426f85cccb8";

    private DishAdapter mAdapter;

    LoaderManager loaderManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wednessday);
        //List<Dish> dishes = new ArrayList<>();

        //getSupportActionBar().setTitle("Mittwoch");

        ListView dishListView = findViewById(R.id.list_wednessday);


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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Days:");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array,android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);

        Log.d(LOG_TAG, "Item number: "+ spinner.getSelectedItemId());

            spinner.setSelection(2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i==1){
                        Toast.makeText(WednessdayActivity.this, "Montag", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(WednessdayActivity.this,TuesdayActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    if(i==0){
                        Intent intent1 = new Intent(WednessdayActivity.this,MondayActivity.class);
                        startActivity(intent1);
                        finish();
                        Log.e(LOG_TAG,"DienstagActivity");
                    }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart: MittwochActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop: MittwochActivity");
        super.onStop();

    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause: MittwochActivity");
        this.finish();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
