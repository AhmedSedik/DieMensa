package com.example.ahmed.diemensa;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class DishLoader extends AsyncTaskLoader<List<Dish>> {
    private String mUrl;

    public DishLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

        Log.v("Loader:","onStartLoading");
    }

   /* @Override
    protected void onStopLoading() {
       //cancelLoad();
        Log.v("Loader:","onStopLoading");
    }

   /* @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }
*/
    @Override
    public List<Dish> loadInBackground() {
        if(mUrl==null){
            return null;
        }

        List<Dish> dishes  = QueryUtils.fetchData(mUrl);
        Log.v("Loader:","loadingInBackground");
        return dishes;
    }
}
