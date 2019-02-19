package com.example.ahmed.diemensa.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.diemensa.Adapter.FavoriteListAdapter;
import com.example.ahmed.diemensa.Interfaces.OnItemClickListener;
import com.example.ahmed.diemensa.Model.Dish;
import com.example.ahmed.diemensa.MondayActivity;
import com.example.ahmed.diemensa.R;
import com.example.ahmed.diemensa.Utils.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnItemClickListener {

    SharedPreference sharedPreference;
    Activity activity;
    List<Dish> favorites;
    FavoriteListAdapter listAdapter;
    ImageView heart;
    ListView listView;
    private TextView likesTextView;

    OnItemClickListener listener;

    private DatabaseReference mRef;
    private DatabaseReference likesRef;
    private FirebaseDatabase mDatabase;
    private List<Dish> dishList;

    public FavoriteFragment() {

    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listAdapter.setOnItemClickListener(FavoriteFragment.this);
        dishList = new ArrayList<>();
        super.onActivityCreated(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        heart = container.findViewById(R.id.imgbtn_favorite);
        // Get favorite items from SharedPreferences.

        //getActivity().setTitle("Favourites");
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);
        if (favorites == null) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {
            if (favorites.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }
            listView = view.findViewById(R.id.list_product);
            if (favorites != null) {

                listAdapter = new FavoriteListAdapter(activity, favorites);
                listView.setAdapter(listAdapter);
            }
        }
        likesTextView = container.findViewById(R.id.likes_counter_text_view);

        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("dish");
        return view;


    }

    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            if (getFragmentManager() != null) {
                                getFragmentManager().popBackStackImmediate();
                            }
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.favorites);
        super.onResume();
    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(activity, "Items Clicked" + position ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLikedButton(int position) {

    }

    @Override
    public void onUnlikedButton(int position) {

    }

    @Override
    public void getLikes(int position) {


    }
}
