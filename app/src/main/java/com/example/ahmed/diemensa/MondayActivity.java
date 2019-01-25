package com.example.ahmed.diemensa;

import android.app.ActionBar;
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

import com.r0adkll.slidr.Slidr;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MondayActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>> {

    public static final String LOG_TAG = MondayActivity.class.getName();
    public String REQUEST_URL;
    public static final String REQUEST_URL_CZ = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";

    private DishAdapterTest mAdapter;
    LoaderManager loaderManager;
    ArrayList<String> list;
    ArrayList<String> daysArrayList;
    ArrayAdapter<String> mArrayAdapter;
    Date date;
    String montagDate;
    Calendar calendar;
    public  Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

        getSupportActionBar().setTitle("");
        //getting the back functionality for back button or any unassigned item in the menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**to attach the slider i added theme slidrTheme in styles and manifest
         * android:background="@color/background_material_light"  in XML
         */
        Slidr.attach(this);


        ListView dishListView = findViewById(R.id.list_dish_montag);
        dishListView.setDivider(null);
        dishListView.setDividerHeight(0);
        dishListView.setVerticalScrollBarEnabled(false);

        Intent intent = getIntent();
        String info = intent.getStringExtra("Branch");

        TextView titleTextView = findViewById(R.id.title_mensa_text_view1);

        switch (info) {
            case "eap":
                /**must get the data according to the activity day with in this case Monday in each branch
                 * so it will go to the table of monday and fetch the data {@link REQUESTED_LINK}
                 */
                titleTextView.setText(getString(R.string.mensa_eap));
                REQUEST_URL = "http://192.168.178.22:81/mensa.php";
                break;
            case "cz":
                titleTextView.setText(getString(R.string.mensa_cz));
                REQUEST_URL = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";
                break;
            default:
                titleTextView.setText("");

        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            loaderManager = getLoaderManager();

            loaderManager.initLoader(1, null, this);

        }

        if (mAdapter == null) {

            mAdapter = new DishAdapterTest(this, new ArrayList<Dish>());
            dishListView.setAdapter(mAdapter);
        }


        TextView titleTextView2 = findViewById(R.id.title_mensa_text_view2);

        TextView titleTextView3 = findViewById(R.id.title_mensa_text_view3);


        Log.e(LOG_TAG, "Initializing the Loader");

    }


    @Override
    public Loader<List<Dish>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "Initializing OnCreate Loader");
        // container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        //container.startShimmerAnimation();
        return new DishLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Dish>> loader, List<Dish> dishes) {
        Log.e(LOG_TAG, "Initializing onFinished");
        mAdapter.clear();
        Log.e(LOG_TAG, "Initializing onFinished Cleat Adapter");
        Log.e(LOG_TAG, "Loading Animation Stoped");

        if (dishes != null && !dishes.isEmpty()) {
            mAdapter.addAll(dishes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dish>> loader) {
        Log.e(LOG_TAG, "Initializing onLoaderReset");
        mAdapter.clear();
        Log.e(LOG_TAG, "Initializing onLoaderReset Clear Adapter");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dish, menu);


        MenuItem item = menu.findItem(R.id.spinner);

        spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner

        daysArrayList = new ArrayList<>();

        Intent intent = getIntent();

        String friday = intent.getStringExtra("Friday");

        date = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(date);


        int i=0;
        while(i<=5){

            if(i==0){
                calendar.add(Calendar.DATE,0);
            }else{
                calendar.add(Calendar.DATE,1);
            }
                date = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM yyyy",Locale.GERMANY);

                montagDate = dateFormat.format(date);

                daysArrayList.add(i, String.valueOf(montagDate));

            Log.e("Array List: ", String.valueOf(daysArrayList));
            i++;
        }


        if (friday.equals("Friday")) {

            for(int x = daysArrayList.size() - 2; x > 0; x--)
            {
                daysArrayList.remove(x+1);
            }

        }else {
            Log.e(LOG_TAG,"Today is not Friday");
        }

       /* for (int i =0;i < weakday.length;i++) {
            daysArrayList.add(weakday[i]);
        }*/


        mArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item
                , daysArrayList);

        mArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(mArrayAdapter);

        //here may be i can get the day and date from the @DB
        spinner.setSelection(mArrayAdapter.getPosition("Montag"));


        mArrayAdapter.notifyDataSetChanged();


        spinner.setSelection(0);

        //adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                    Toast.makeText(MondayActivity.this, "Montag Activity", Toast.LENGTH_SHORT).show();

                }
                if (i == 1) {
                    Toast.makeText(MondayActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e(LOG_TAG, "DienstagActivity");
                }
                if (i == 2) {
                    Intent intent = new Intent(MondayActivity.this, WednessdayActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e(LOG_TAG, "onNothing Selected ");
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
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

 /*   String dayNames[] = new DateFormatSymbols().getWeekdays();
    Calendar date2 = Calendar.getInstance();
        System.out.println("Today is a "
                + dayNames[date2.get(Calendar.DAY_OF_WEEK)]);

                //getSupportActionBar().setTitle("Days:");

        //am starting to get date to populate in spinner

        //String dienstag = dateFormat.format(date);


        //Toast.makeText(this, "Date: "+ montagDate, Toast.LENGTH_SHORT).show();
        //End

        //String[] weakday = getResources().getStringArray(R.array.weekday_array);



        //mySpinner.setSelection(arrayAdapter.getPosition("Category 2"));
        //spinnerObject.setSelection(INDEX_OF_CATEGORY2)
        //if i want to erase first item
        //int listsize = list.Count - 1;

       /*int array = R.array.weekday_array;
        adapter = ArrayAdapter.createFromResource(this,
                array,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);*/


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
