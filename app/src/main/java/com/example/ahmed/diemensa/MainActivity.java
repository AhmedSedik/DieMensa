package com.example.ahmed.diemensa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.ahmed.diemensa.Fragments.FavoriteFragment;

public class MainActivity extends AppCompatActivity {
        public Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new MondayActivity());

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:

                            fragment = new MondayActivity();
                            loadFragment(fragment);

                            return true;
                        case R.id.navigation_fav:
                            fragment = new FavoriteFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_info:
                            return true;
                    }

                    return false;
                }


            };


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
