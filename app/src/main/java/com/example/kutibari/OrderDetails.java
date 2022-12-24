package com.example.kutibari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetails extends AppCompatActivity {

    TextView who,where,quantity;
    DatabaseReference reference;
    Button done;
    String uid,pid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        who=findViewById(R.id.orderusername);
        where=findViewById(R.id.orderlocation);
        quantity=findViewById(R.id.orderquantity);
        done=findViewById(R.id.donebtn);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent=getIntent();
        pid=intent.getStringExtra("Pid");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child(uid).child("Orders").child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders orders=snapshot.getValue(Orders.class);
                who.setText(orders.getName());
                where.setText(orders.getAddress());
                quantity.setText(orders.getQuantity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child(uid).child("Orders").child(pid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent1=new Intent(OrderDetails.this,ProfilePage.class);
                startActivity(intent1);
            }
        });

    }
}