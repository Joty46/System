package com.example.kutibari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    ImageView pdimage;
    TextView pdtitle,pdprice,pddays;
    DatabaseReference reference,reference2;
    String uuid,title,price;
    TextView pdetailtxt;
    TextView wantorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        pdimage=findViewById(R.id.imageforpdetail);
        pdtitle=findViewById(R.id.namepdetail);
        pdprice=findViewById(R.id.pricepdetail);
        pddays=findViewById(R.id.dayspdetail);
        wantorder=findViewById(R.id.wantorder);
        Intent intent=getIntent();
        String uid=intent.getStringExtra("Uid");

        uuid=intent.getStringExtra("Uuid");

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
                        Picasso.get().load(link).into(pdimage);
                        pdtitle.setText(product.getTitle());
                        pdprice.setText(product.getPrice());
                        pddays.setText(product.getDays());
                        title=product.getTitle();
                        price=product.getPrice();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        wantorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ProductDetails.this,CustomerOrderPage.class);
                intent1.putExtra("uid", uuid);
                intent1.putExtra("title",title);
                intent1.putExtra("price",price);
                startActivity(intent1);
                finish();
            }
        });

    }
}