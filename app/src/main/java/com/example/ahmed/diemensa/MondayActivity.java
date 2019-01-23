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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MondayActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MondayActivity.class.getName();
    public  String REQUEST_URL;
    public static final String REQUEST_URL_CZ = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";

    private DishAdapterTest mAdapter;

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

        Intent intent = getIntent();
        String info = intent.getStringExtra("Branch");

        TextView titleTextView = findViewById(R.id.title_mensa_text_view1);

        switch(info){
            case "eap":
                titleTextView.setText(getString(R.string.mensa_eap));
                REQUEST_URL = "https://api.jsonbin.io/b/5c4629d104ce8017ee279289";
                break;
            case "cz":
                titleTextView.setText(getString(R.string.mensa_cz));
                REQUEST_URL = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";
                break;
            default:
                titleTextView.setText("");

        }

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected()){

            loaderManager = getLoaderManager();

            loaderManager.initLoader(1,null,this);


        }

        if (mAdapter==null){

            mAdapter = new DishAdapterTest(this,new ArrayList<Dish>());
            dishListView.setAdapter(mAdapter);
        }





        TextView titleTextView2 = findViewById(R.id.title_mensa_text_view2);

        TextView titleTextView3 = findViewById(R.id.title_mensa_text_view3);


        Log.e(LOG_TAG,"Initializing the Loader");

    }


    @Override
    public Loader<List<Dish>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG,"Initializing OnCreate Loader");
       // container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        //container.startShimmerAnimation();
        return new DishLoader(this, REQUEST_URL);
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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Days:");
        adapter = ArrayAdapter.createFromResource(this,
                R.array.weekday_array,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);

            spinner.setSelection(0);



        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){

                    Toast.makeText(MondayActivity.this, "Montag Activity", Toast.LENGTH_SHORT).show();

                }
                if(i==1) {
                    Toast.makeText(MondayActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e(LOG_TAG, "DienstagActivity");
                }
                if(i==2){
                    Intent intent = new Intent(MondayActivity.this, WednessdayActivity.class);
                    startActivity(intent);
                    finish();
                }
              /*  switch (i){

                    case 1:
                        Toast.makeText(MondayActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MondayActivity.this,TuesdayActivity.class);
                        startActivity(intent);
                        Log.e(LOG_TAG,"DienstagActivity");
                    case 2:
                        Intent intent2 = new Intent(MondayActivity.this,WednessdayActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
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
        finish();
        super.onStop();

    }
    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause: MontagActivity");
        finish();
        super.onPause();
    }
}
