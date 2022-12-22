package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArtistShow extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> userArrayList=new ArrayList<>();
    WomenAdapter womenAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_show);
        recyclerView=findViewById(R.id.artistlist);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    User u=dataSnapshot.getValue(User.class);
                    String urole=u.getRole();
                    if(urole.equals("Seller")){
                        userArrayList.add(u);


                    }
                    Log.e(TAG, "onDataChange: "+userArrayList.size());
                    womenAdapter.notifyItemInserted(userArrayList.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        for(int i =0;i<userArrayList.size();i++)
//        {
//            Log.e(TAG, "onDataChange: "+userArrayList.get(i).getUsername() );
//        }
        Log.e(TAG, "After onCreate: ");
        womenAdapter= new WomenAdapter(ArtistShow.this);
        womenAdapter.setUserlist(userArrayList);
        recyclerView.setAdapter(womenAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ArtistShow.this));

    }
}