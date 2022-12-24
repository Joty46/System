package com.example.kutibari;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    public static boolean running_wishlist_query = false;
    ImageView pdimage,wishlist;
    TextView pdtitle,pdprice,pddays,rating_outof,pop5,pop4,pop3,pop2,pop1,avg_rating_text;
    DatabaseReference reference,reference2;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uuid,title,price,pid;
    TextView pdetailtxt;
    TextView wantorder;
    TextView giverating;
    int finalrate,rate,totalrate,rate1,rate2,rate3,rate4,rate5;
    String stringrate;
    ProgressBar bar1,bar2,bar3,bar4,bar5;

    //rating layout
    private LinearLayout ratenowcontainer;
    //rating layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

//        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        pdimage=findViewById(R.id.imageforpdetail);
        pdtitle=findViewById(R.id.namepdetail);
        pdprice=findViewById(R.id.pricepdetail);
        pddays=findViewById(R.id.dayspdetail);
        wantorder=findViewById(R.id.wantorder);
        giverating=findViewById(R.id.yourrating);
        rating_outof=findViewById(R.id.rating_outof);
        pop1=findViewById(R.id.pop1);
        pop2=findViewById(R.id.pop2);
        pop3=findViewById(R.id.pop3);
        pop4=findViewById(R.id.pop4);
        pop5=findViewById(R.id.pop5);
        avg_rating_text=findViewById(R.id.avg_rating_text);
        bar1=findViewById(R.id.bar1);
        bar2=findViewById(R.id.bar2);
        bar3=findViewById(R.id.bar3);
        bar4=findViewById(R.id.bar4);
        bar5=findViewById(R.id.bar5);
        wishlist=findViewById(R.id.wishlist);

        Intent intent=getIntent();
        String uid=intent.getStringExtra("Uid");

        uuid=intent.getStringExtra("Uuid");

        reference2= FirebaseDatabase.getInstance().getReference("Wishlist").child("user");
//        reference2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Product product;
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    String newid=dataSnapshot.getValue(Product.class).getId();
//                    if(newid.equals(uid)){
//                        wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B3261B")));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String puid=mAuth.getCurrentUser().getUid();
                wishlist.setImageTintList(ColorStateList.valueOf(Color.parseColor("#B3261B")));
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(ProductDetails.this,"Product has been added to your wish list", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

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
                        pid=product.getId();
                        totalrate=product.getTotal();
                        rate1=product.getOne();
                        rate2=product.getTwo();
                        rate3=product.getThree();
                        rate4=product.getFour();
                        rate5=product.getFive();
                        rate=0;
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
                    }
                }
                float avg=(5*rate5+4*rate4+3*rate3+2*rate2+rate1);
                if(totalrate==0){
                    avg=0;

                    bar1.setProgress(0);
                    bar2.setProgress(0);
                    bar3.setProgress(0);
                    bar4.setProgress(0);
                    bar5.setProgress(0);
                }
                else {
                    avg = avg / totalrate;

                    bar1.setProgress((rate1*100)/totalrate);
                    bar2.setProgress((rate2*100)/totalrate);
                    bar3.setProgress((rate3*100)/totalrate);
                    bar4.setProgress((rate4*100)/totalrate);
                    bar5.setProgress((rate5*100)/totalrate);
                }
//                String s=Integer.toString(totalrate);
                rating_outof.setText(Integer.toString(totalrate)+" ratings");
                avg_rating_text.setText(String.valueOf(avg));
                pop1.setText(Integer.toString(rate1));
                pop2.setText(Integer.toString(rate2));
                pop3.setText(Integer.toString(rate3));
                pop4.setText(Integer.toString(rate4));
                pop5.setText(Integer.toString(rate5));


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
                                rate += 2;
                                totalrate+=1;
                                reference.child(uid).child(stringrate).setValue(rate);
                                reference.child(uid).child("total").setValue(totalrate);
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