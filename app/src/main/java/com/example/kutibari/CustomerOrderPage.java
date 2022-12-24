package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerOrderPage extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase reference;
//    String id;

    DatabaseReference databaseReference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    TextView productname,price_show,price_confirmation;
    int taka;
    EditText address,qty;
    TextView pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page);

        qty = findViewById(R.id.oqty);
        MaterialButton submit = findViewById(R.id.orderbtn);
        productname=findViewById(R.id.txtforwhattoorder);
        price_show=findViewById(R.id.price_show);
        address=findViewById(R.id.address);
        pay=findViewById(R.id.pay);
        reference=FirebaseDatabase.getInstance();
//        price_confirmation=findViewById(R.id.final_price);

        String uid=getIntent().getStringExtra("uid");
        String title=getIntent().getStringExtra("title");
        String price=getIntent().getStringExtra("price");
        String pid=getIntent().getStringExtra("pid");

        productname.setText("The product you want to order : "+title);
        taka=Integer.parseInt(price);
        taka *= 0.4;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.bKash.customerapp");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(CustomerOrderPage.this, "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String puid=mAuth.getCurrentUser().getUid();
                    String quantity = qty.getText().toString();

                    String orderadd=address.getText().toString();
                    int quant = Integer.parseInt(quantity);
                    taka *= quant;
                    String show_price = Integer.toString(taka);

                    price_show.setText(show_price+" taka has been deducted from your account");
//                    price_confirmation.setText("Please type "  +show_price+" in the above box and press the button below to confirm");

                    System.out.println(show_price);
                    databaseReference= FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("AllProduct").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //userArrayList.clear();
                            for(DataSnapshot dataSnapshot:snapshot.getChildren())
                            {
                                AllProduct p=dataSnapshot.getValue(AllProduct.class);
                                String uname=p.getUname();
                                if(!uname.equals("")){
//                                    Intent intent1=new Intent(CustomerOrderPage.this,OtpSend.class);
//                                    intent1.putExtra("uname", uname);
//                                    startActivity(intent1);
                                    databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //userArrayList.clear();
                                            for(DataSnapshot dataSnapshot:snapshot.getChildren())
                                            {

                                                User u=dataSnapshot.getValue(User.class);
                                                String username=u.getUsername();
                                                if(username.equals(uname)){
                                                    String usermail = u.getMail();
                                                    int i=Integer.parseInt(usermail.replaceAll("[\\D]",""));
//                                                    System.out.println(i);
                                                    String number = Integer.toString(i);
                                                    number="0"+number;
                                                    Toast.makeText(CustomerOrderPage.this,"Your order has confirmed. "+show_price+" taka has been sent to "+number,Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                reference.getReference().child("users").child(puid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        String pusername =user.getUsername();
                        Orders  orders=new Orders(pusername,orderadd,quantity,title,pid);
                        reference.getReference().child(uid).child("Orders").child(pid).setValue(orders).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.e(TAG, "onSuccess: ordercomfirmed" );
                            }
                        });
                        reference.getReference().child("AllOrders").child(pid).setValue(orders).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.e(TAG, "onSuccess: ordercomfirmed" );
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}