package com.example.kutibari;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    public static boolean running_wishlist_query = false;
    ImageView pdimage;
    TextView pdtitle,pdprice,pddays;
    DatabaseReference reference,reference2;
    String uuid,title,price,pid;
    TextView pdetailtxt;
    TextView wantorder;
    TextView giverating;
    int finalrate;
    String stringrate;

    //rating layout
    private LinearLayout ratenowcontainer;
    //rating layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        pdimage=findViewById(R.id.imageforpdetail);
        pdtitle=findViewById(R.id.namepdetail);
        pdprice=findViewById(R.id.pricepdetail);
        pddays=findViewById(R.id.dayspdetail);
        wantorder=findViewById(R.id.wantorder);
        giverating=findViewById(R.id.yourrating);
        Intent intent=getIntent();
        String uid=intent.getStringExtra("Uid");
        uuid=intent.getStringExtra("Uuid");
        Log.e(TAG, "producy "+uuid );

        reference= FirebaseDatabase.getInstance().getReference().child(uuid).child("product");
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
                        pid=product.getId();
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
                intent1.putExtra("pid",pid);
                startActivity(intent1);
                finish();
            }
        });


        //rating layout
        ratenowcontainer=findViewById(R.id.rate_now_container);
        for (int x=0; x<ratenowcontainer.getChildCount(); x++){
            final int starPosition = x;
            ratenowcontainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }


//        reference2= FirebaseDatabase.getInstance().getReference(uuid);
//        reference= FirebaseDatabase.getInstance().getReference(uuid).child("product");
        giverating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Product product = new Product();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            String newid=postSnapshot.getValue(Product.class).getId();
                            if (newid.equals(uid)){
                                int rate=0;
                                if(finalrate==5){
                                    rate=product.getFive();
                                    stringrate="five";
                                }
                                else if(finalrate==4){
                                    rate=product.getFour();
                                    stringrate="four";
                                }
                                else if(finalrate==3){
                                    rate=product.getThree();
                                    stringrate="three";
                                }
                                else if(finalrate==2){
                                    rate=product.getTwo();
                                    stringrate="two";
                                }
                                else if(finalrate==1){
                                    rate=product.getOne();
                                    stringrate="one";
                                }
                                rate += 2;
                                reference.child(uid).child(stringrate).setValue(rate);
                                Toast.makeText(ProductDetails.this,"Your rating has been taken",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //rating layout

    }

    private void setRating(int starPosition) {
        for (int x=0; x<ratenowcontainer.getChildCount(); x++){
            ImageView starBtn= (ImageView) ratenowcontainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#1A000000")));
            if(x<=starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#09EA12")));
                finalrate=x;
            }
        }

    }
}