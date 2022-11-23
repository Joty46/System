package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfilePage extends AppCompatActivity {


    String[] itemfororder = {"item1","item2","item3","item4"};
    AutoCompleteTextView autoCompleteTxtForOrders,autoCompleteTxtForWorks;
    ArrayAdapter<String> adapterItemforOrders,adapterItemforWorks;
    Button uploadbtn;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        autoCompleteTxtForOrders=findViewById(R.id.auto_complete_text_orders);
        adapterItemforOrders=new ArrayAdapter<String>(this,R.layout.list_for_orders,itemfororder);
        autoCompleteTxtForOrders.setAdapter(adapterItemforOrders);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String uuid=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference(uuid).child("product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> itemforworks = new ArrayList<>();
                ArrayList<String> uiditemforworks = new ArrayList<>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    itemforworks.add(postSnapshot.getValue(Product.class).getTitle());
                    uiditemforworks.add(postSnapshot.getValue(Product.class).getId());
                }
                autoCompleteTxtForWorks=findViewById(R.id.auto_complete_text_uploadedworks);
                adapterItemforWorks=new ArrayAdapter<String>(ProfilePage.this,R.layout.list_for_orders,itemforworks);
                autoCompleteTxtForWorks.setAdapter(adapterItemforWorks);
                autoCompleteTxtForWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // String itemsforworks=parent.getItemAtPosition(position).toString();
                        Intent intent=new Intent(ProfilePage.this,Works.class);//works.java
                        intent.putExtra("Uid",uiditemforworks.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        autoCompleteTxtForOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String itemsfororder=parent.getItemAtPosition(position).toString();
                Intent intent=new Intent(ProfilePage.this,OrderDetails.class);//order details
                startActivity(intent);
            }
        });

        uploadbtn=findViewById(R.id.upload);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilePage.this,UploadWorks.class));
            }
        });
    }
}