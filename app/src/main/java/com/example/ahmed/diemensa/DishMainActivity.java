package com.example.ahmed.diemensa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class DishMainActivity  extends AppCompatActivity {
        private DishPageAdapter mPageAdapter;
    ViewPager mViewPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dish);

        mPageAdapter = new DishPageAdapter(getSupportFragmentManager());

         mViewPager = findViewById(R.id.container);

        this.addPages();
        //setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);
    }

    private void addPages()
    {
        DishPageAdapter adapter=new DishPageAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new MontagDishActivity(),"Montag");
        adapter.addFragment(new DienstagDishActivity(),"Dienstag");
        //adapter.addFragment(new MittwochDishActivity(),"Mittwoch");

        //SET ADAPTER TO VP
        mViewPager.setAdapter(adapter);
    }

   /* private void setupViewPager(ViewPager mViewPager) {

        DishPageAdapter adapter = new DishPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new MontagDishActivity(),"Montag");
        adapter.addFragment(new DienstagDishActivity(),"Dienstag");
        adapter.addFragment(new MittwochDishActivity(),"Mittwoch");


        mViewPager.setAdapter(adapter);

    }*/
}
