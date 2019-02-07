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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.r0adkll.slidr.Slidr;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MondayActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Dish>>,SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = MondayActivity.class.getName();
    public String REQUEST_URL;
    public static final String REQUEST_URL_CZ = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";
    private SwipeRefreshLayout swipeRefreshLayout;

    LoaderManager loaderManager;
    ArrayList<String> daysArrayList;
    ArrayAdapter<String> mArrayAdapter;
    //TODO change to RecyclerView

    Date date;
    String montagDate;
    Calendar calendar;
    Intent intent;
    public Spinner spinner;
    TextView mEmptyTextView;
    private static final int LOADER_ID = 1;
    private ArrayList<Dish> dishList;

    RecyclerView recyclerView;
    private List<Dish> dishList1 =   new ArrayList<>();
    private RecyclerAdapter dishAdapter;

    private EditText insertText;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private CollectionReference likesRef = db.collection("likes");

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

        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        FloatingActionButton contactActionButton = findViewById(R.id.fb_contact);
        FloatingActionButton contactActionButton2 = findViewById(R.id.fb_info);
        contactActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MondayActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        // set color schemes on refresh view

        // implement refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAdapter();
            }
        });

        contactActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MondayActivity.this, "FAB2 Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        intent = getIntent();
        String info = intent.getStringExtra("Branch");

        TextView titleTextView = findViewById(R.id.title_mensa_text_view1);
        mEmptyTextView = findViewById(R.id.empty_view);

        switch (info) {
            case "eap":
                /**must get the data according to the activity day with in this case Monday in each branch
                 * so it will go to the table of monday and fetch the data {@link REQUESTED_LINK}
                 */
                titleTextView.setText(getString(R.string.mensa_eap));

                dateComparison();

                break;
            case "cz":
                titleTextView.setText(getString(R.string.mensa_cz));
                REQUEST_URL = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";
                break;
            default:
                titleTextView.setText("");

        }
        recyclerView = findViewById(R.id.recycler_view_dish);
        dishAdapter = new RecyclerAdapter(this,dishList1);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(dishAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListner(this, recyclerView, new RecyclerTouchListner.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Dish book = dishList1.get(position);
                Toast.makeText(MondayActivity.this, book.getmDate() + "is simply clicked", Toast.LENGTH_SHORT).show();
                Toast.makeText(MondayActivity.this, book.getmDate() + "is simply clicked", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                Dish book = dishList1.get(position);
                Toast.makeText(MondayActivity.this, book.getmImageResId1() + "is long clicked", Toast.LENGTH_SHORT).show();

            }
        }));
        //checking network and type of Connection
        checkNetworkConnection();
        //recyclerView.setEmptyView(mEmptyTextView);

        Log.e(LOG_TAG, "Initializing the Loader");

    }


    private void refreshAdapter() {

        getLoaderManager().restartLoader(LOADER_ID,null,MondayActivity.this );
        Toast.makeText(MondayActivity.this, "Items Refreshed", Toast.LENGTH_SHORT).show();
        Log.e(LOG_TAG, "Items Refreshed");

        swipeRefreshLayout.setRefreshing(false);
        dishAdapter.notifyDataSetChanged();
    }


    @Override
    public Loader<List<Dish>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "Initializing OnCreate Loader");
        // container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container1);
        //container.startShimmerAnimation();
        if (dishList1.size() > 0) {
            dishList1.clear();
            dishAdapter.notifyDataSetChanged();
        }
        return new DishLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Dish>> loader, List<Dish> dishes) {
        Log.e(LOG_TAG, "Initializing onFinished");
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        //mEmptyTextView.setText(R.string.no_data);
        //dishAdapter.clear();
        dishList1.clear();
        Log.e(LOG_TAG, "Initializing onFinished Cleat Adapter");
        Log.e(LOG_TAG, "Loading Animation Stoped");

        if (dishes != null && !dishes.isEmpty()) {
            //dishAdapter.addAll(dishList1);
            dishList1.clear();
            dishList1 = dishes;
            dishAdapter.setData(dishes);


        }
    }

    @Override
    public void onLoaderReset(Loader<List<Dish>> loader) {
        Log.e(LOG_TAG, "Initializing onLoaderReset");
        //dishAdapter.clear();
        dishList1.clear();
        Log.e(LOG_TAG, "Initializing onLoaderReset Clear Adapter");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dish, menu);


        MenuItem item = menu.findItem(R.id.spinner);

        spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner

        daysArrayList = new ArrayList<>();

        Intent intent = getIntent();
        //TODO Change everyday
        String dayOfTheWeek = intent.getStringExtra("Tuesday");

        date = new Date();
        calendar = Calendar.getInstance();

        calendar.setTime(date);


        int i = 0;
        while (i <= 5) {

            if (i == 0) {
                calendar.add(Calendar.DATE, 0);
            } else {
                calendar.add(Calendar.DATE, 1);
            }
            date = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.GERMANY);

            montagDate = dateFormat.format(date);

            daysArrayList.add(i, String.valueOf(montagDate));

            Log.e("Array List: ", String.valueOf(daysArrayList));
            i++;
        }

       /* //TODO Change Every day
        if (dayOfTheWeek.equals("Monday")) {

            for (int x = daysArrayList.size() - 2; x > 0; x--) {
                daysArrayList.remove(x + 1);
            }

        } else {
            Log.e(LOG_TAG, "Today is not Monday");
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
                    //TODO method for the 6 days Cases
                    String days = spinner.getSelectedItem().toString();
                    //TODO change every fckn Day bec we still dont have a fckn Database
                    if(days.contains("Mittwoch")){
                        REQUEST_URL = "http://192.168.178.22:81/mensa_eap_tue.php";
                    }

                    getLoaderManager().restartLoader(LOADER_ID,null,MondayActivity.this );

                    Toast.makeText(MondayActivity.this, "Dienstag", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "DienstagActivity");

                    dishAdapter.notifyDataSetChanged();



                  /*  Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
                    startActivity(intent);
                    finish();*/

                }
                if (i == 2) {
                    Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
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
        switch(item.getItemId()){

            case R.id.refresh:
                Log.e(LOG_TAG,"Refresh button triggered");
                //tell SwipeRefreshLayout to start progress Indicator
                swipeRefreshLayout.setRefreshing(true);
                //update
                refreshAdapter();
                return true;
        }
        //this.finish();

        return super.onOptionsItemSelected(item);
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
    protected void onResume() {
        super.onResume();
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

    private void dateComparison(){
        intent = getIntent();
        //String friday = String.valueOf(intent.getExtras());
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        date = new Date();
        calendar = Calendar.getInstance();
        String dayOfTheWeek =  dayNames[calendar.get(Calendar.DAY_OF_WEEK)];
        calendar.setTime(date);
        Bundle extras = intent.getExtras();


        if(extras!=null)
            //TODO Change everyday bec we dont have complete Database
            if (extras.get("Tuesday").equals(dayOfTheWeek)) {
                Log.e("Today= ", dayOfTheWeek);
                REQUEST_URL = "http://192.168.178.22:81/mensa_eap_mon.php";
                //REQUEST_URL = "https://api.jsonbin.io/b/5c4df8cda3fb18257ac2bdb8";
            }else if (extras.get("Wednesday").equals(dayOfTheWeek)) {
                Log.e("Today= ", dayOfTheWeek);
                REQUEST_URL = "http://192.168.178.22:81/mensa_eap_tue.php";
            }

    }
    public void checkNetworkConnection(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            loaderManager = getLoaderManager();
            //Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public void onRefresh() {
        Log.e(LOG_TAG,"OnRefresh ");
        /*swipeView.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeView.setRefreshing(true);
                handler.sendEmptyMessage(0);
            }
        }, 1000);*/
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
