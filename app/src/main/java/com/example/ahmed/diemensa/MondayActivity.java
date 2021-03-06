package com.example.ahmed.diemensa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ahmed.diemensa.Adapter.RecyclerAdapter;
import com.example.ahmed.diemensa.Firebase.FirebaseDBHelper;
import com.example.ahmed.diemensa.Interfaces.OnItemClickListener;
import com.example.ahmed.diemensa.Model.Dish;
import com.example.ahmed.diemensa.Utils.SharedPreference;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MondayActivity extends Fragment implements OnItemClickListener {

    public static final String LOG_TAG = MondayActivity.class.getName();
    ArrayList<String> daysArrayList;
    ArrayAdapter<String> mArrayAdapter;
    Date date;
    String montagDate;
    Calendar calendar;
    public Spinner spinner;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private DatabaseReference mRef;
    private DatabaseReference likesRef;
    private FirebaseDatabase mDatabase;
    private List<Dish> dishList;
    private SharedPreference sharedPreference;
    public ImageView heart;
    public Fragment fragment;
    CoordinatorLayout coordinatorLayout;
    public FirebaseDBHelper firebaseDbHelper;
    OnItemClickListener mListener;


    public MondayActivity(){

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setContentView(R.layout.activity_monday);

        firebaseDbHelper = new FirebaseDBHelper();
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        //Toolbar toolbar = getView().findViewById(R.id.toolbar_slide);

        coordinatorLayout = getView().findViewById(R.id.cs_layout);
        //getting the back functionality for back button or any unassigned item in the menu
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**to attach the slider i added theme slidrTheme in styles and manifest
         * android:background="@color/background_material_light"  in XML
         */
        //Slidr.attach(getActivity());

        //setHasOptionsMenu(true);

        /*FloatingActionButton contactActionButton = findViewById(R.id.fb_contact);
        FloatingActionButton contactActionButton2 = findViewById(R.id.fb_info);
        contactActionButton.setOnClickListener((View v) -> {

                Toast.makeText(MondayActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show();

        });
        contactActionButton2.setOnClickListener((View v) ->{
                Toast.makeText(MondayActivity.this, "FAB2 Clicked", Toast.LENGTH_SHORT).show();
        });

        floatingActionMenu = findViewById(R.id.floating_menu);
        cs = findViewById(R.id.cs_layout);*/


        ImageView locationButton = getView().findViewById(R.id.btn_location);
        locationButton.setOnClickListener(this::getMensaLocation);


        heart  =  getView().findViewById(R.id.imgbtn_favorite);
        sharedPreference = new SharedPreference();
        recyclerView = getView().findViewById(R.id.recycler_view_dish);


        FirebaseDBHelper dbHelper = new FirebaseDBHelper();
        dbHelper.readData(new FirebaseDBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Dish> dishes) {
                dishList = new ArrayList<>();
                dishList = dishes;
                sharedPreference = new SharedPreference();

                //RecyclerConfig.recyclerAdapter = new RecyclerAdapter(getContext(),dishes);

                FirebaseApp.initializeApp(getContext());
                RecyclerConfig config = new RecyclerConfig();

                config.setConfig(recyclerView,getContext(),dishes);


                //new RecyclerConfig().setConfig(recyclerView,getContext(),dishes);


                RecyclerConfig.recyclerAdapter.setOnItemClickListener(MondayActivity.this);
            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("");
        return inflater.inflate(R.layout.activity_monday, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "Position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLikedButton(int position) {
        Dish dish = dishList.get(position);
        //update @specific key or id for the dish
        final String itemKey = dish.getKey();
        int value = dish.getLikes();


        int addedValue = value + 1;

        //Toast.makeText(this, "Like Pos" + position, Toast.LENGTH_SHORT).show();
        likesRef = FirebaseDatabase.getInstance().getReference("dish")
                .child(itemKey).child("likes");

        likesRef.setValue(addedValue).addOnSuccessListener(aVoid -> {
            //Toast.makeText(DishActivity.this, "Likes updated Successfully", Toast.LENGTH_SHORT).show();
        });


    }
    @Override
    public void onUnlikedButton(int position) {
        Dish dish = dishList.get(position);
        //update @specific key or id for the dish
        final String itemKey = dish.getKey();
        int value = dish.getLikes();

        int addedValue = value - 1;
        //Toast.makeText(this, "unLike Pos" + position, Toast.LENGTH_SHORT).show();
        likesRef = FirebaseDatabase.getInstance().getReference("dish")
                .child(itemKey).child("likes");

        likesRef.setValue(addedValue).addOnSuccessListener(aVoid -> {
            //Toast.makeText(DishActivity.this, "Likes Removed Successfully", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void getLikes(int position) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.i("FragCreateList","onCreateOptionsMenu called");
        super.onCreateOptionsMenu(menu,menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.menu_dish, menu);
        MenuItem item = menu.findItem(R.id.spinner);

        spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner

        daysArrayList = new ArrayList<>();

        Intent intent = getActivity().getIntent();
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
        mArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item
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

                    Toast.makeText(getContext(), "Montag Activity", Toast.LENGTH_SHORT).show();

                }
                if (i == 1) {
                    //TODO method for the 6 days Cases
                    String days = spinner.getSelectedItem().toString();
                    //TODO change every fckn Day bec we still dont have a fckn Database
                    if(days.contains("Mittwoch")){

                    }



                    Toast.makeText(getContext(), "Dienstag", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "DienstagActivity");

                    mAdapter.notifyDataSetChanged();



                  /*  Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
                    startActivity(intent);
                    finish();*/

                }
                if (i == 2) {
                    //Intent intent = new Intent(MondayActivity.this, TuesdayActivity.class);
                    //startActivity(intent);
                    //finish();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e(LOG_TAG, "onNothing Selected ");
            }
        });
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void getMensaLocation(View view) {//TODO check wich location
        Uri mensaUri = Uri.parse("geo:0,0?q= Ernst-Abbe-Platz 8, 07743 Jena");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mensaUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }



    /*  intent = getIntent();
        String info = intent.getStringExtra("Branch");

        TextView titleTextView = findViewById(R.id.title_mensa_text_view1);
        mEmptyTextView = findViewById(R.id.empty_view);

        switch (info) {
            case "eap":
                /**must get the data according to the activity day with in this case Monday in each branch
                 * so it will go to the table of monday and fetch the data {@link REQUESTED_LINK}

                titleTextView.setText(getString(R.string.mensa_eap));

                dateComparison();

                break;
            case "cz":
                titleTextView.setText(getString(R.string.mensa_cz));
                REQUEST_URL = "https://api.jsonbin.io/b/5c43d51f2c87fa273072fae3/1";
                break;
            default:
                titleTextView.setText("");

        }*/


      /*  private void refreshAdapter () {

            getLoaderManager().restartLoader(LOADER_ID, null, MondayActivity.this);
            Toast.makeText(MondayActivity.this, "Items Refreshed", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Items Refreshed");

            swipeRefreshLayout.setRefreshing(false);
            dishAdapter.notifyDataSetChanged();
        }*/



  /*  @Override
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

        //TODO Change Every day
        if (dayOfTheWeek.equals("Monday")) {

            for (int x = daysArrayList.size() - 2; x > 0; x--) {
                daysArrayList.remove(x + 1);
            }

        } else {
            Log.e(LOG_TAG, "Today is not Monday");
        }

        for (int i =0;i < weakday.length;i++) {
            daysArrayList.add(weakday[i]);
        }


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
                //refreshAdapter();
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

  /*  private void dateComparison(){
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
  / public void checkNetworkConnection(){
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
    }*/


  /*      @Override
        public void onRefresh () {
            Log.e(LOG_TAG, "OnRefresh ");
        /*swipeView.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeView.setRefreshing(true);
                handler.sendEmptyMessage(0);
            }
        }, 1000);*/
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
