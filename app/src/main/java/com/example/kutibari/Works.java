package com.example.kutibari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Works extends AppCompatActivity {
    ImageView wimage;
    TextView wtitle,wprice,wdays;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        wimage=findViewById(R.id.imageforworks);
        wtitle=findViewById(R.id.name);
        wprice=findViewById(R.id.price);
        wdays=findViewById(R.id.days);
        Intent intent=getIntent();
        String uid=intent.getStringExtra("Uid");
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String uuid=user.getUid();
        reference= FirebaseDatabase.getInstance().getReference(uuid).child("product");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String newid=postSnapshot.getValue(Product.class).getId();
                    if (newid.equals(uid)){
                        product=postSnapshot.getValue(Product.class);
                        String link=product.getImage();
                        Picasso.get().load(link).into(wimage);
                        wtitle.setText(product.getTitle());
                        wprice.setText(product.getPrice());
                        wdays.setText(product.getDays());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}