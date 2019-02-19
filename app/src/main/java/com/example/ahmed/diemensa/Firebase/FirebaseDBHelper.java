package com.example.ahmed.diemensa.Firebase;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ahmed.diemensa.Adapter.RecyclerAdapter;
import com.example.ahmed.diemensa.Model.Dish;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {

    private DatabaseReference mRef;
    private DatabaseReference likesRef;
    private FirebaseDatabase mDatabase;
    private List<Dish> dishList = new ArrayList<>();
    private RecyclerAdapter mAdapter;

public interface DataStatus{
    void DataIsLoaded(List<Dish> dishes);


}
    public FirebaseDBHelper(){
        mDatabase = FirebaseDatabase.getInstance();

        mRef = mDatabase.getReference("dish");


    }

    public void readData(final DataStatus dataStatus){


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dishList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot nodes : dataSnapshot.getChildren()) {
                    Dish dish = nodes.getValue(Dish.class);
                    dish.setKey(nodes.getKey());
                    dishList.add(dish);
                }
                //adapter.notifyDataSetChanged();
                if(dishList!=null){
                    dataStatus.DataIsLoaded(dishList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    public void addLike(int position) {

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
}
