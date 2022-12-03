package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersProductList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Product> userproductArrayList=new ArrayList<>();
    UserPlistAdapter userPlistAdapter;
    DatabaseReference databaseReference;
    TextView artistname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_product_list);
        artistname=findViewById(R.id.userplistdialog);
        Intent intent=getIntent();
        String uid=intent.getStringExtra("Uid");
        String name=intent.getStringExtra("name");
        artistname.setText("'"+name.toString()+"'-এর পন্য");
        recyclerView=findViewById(R.id.userproductlist);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child(uid).child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userproductArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Product p=dataSnapshot.getValue(Product.class);
                    userproductArrayList.add(p);
                    userPlistAdapter.notifyItemInserted(userproductArrayList.size()-1);
                }
                userPlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userPlistAdapter= new UserPlistAdapter(this);
        userPlistAdapter.setUserplist(userproductArrayList);
        recyclerView.setAdapter(userPlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}