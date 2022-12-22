package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * grid view and arrays for category of products
     */
//    private GridView gridcat;
//    public static int categoryImages[] = {R.drawable.craft1, R.drawable.craft2, R.drawable.craft3, R.drawable.craft4, R.drawable.craft5, R.drawable.craft6, R.drawable.craft7};
//    public static String[] productName = {"cat1", "cat2", "cat3", "cat4", "cat5", "cat6", "cat7"};
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase reference;
    private RecyclerView recyclerView;
    private ArrayList<AllProduct>productArrayList=new ArrayList<>();
    CategoryAdapter categoryAdapter;
    DatabaseReference databaseReference;
    TextView profiletxt;
    TextView seeartist;

    /**
     * grid view and arrays
     * @param savedInstanceState
     */
//    private GridView gridView;
//    public static int womenImages[] = {R.drawable.woman1, R.drawable.woman2, R.drawable.woman3, R.drawable.woman4, R.drawable.woman5, R.drawable.woman6, R.drawable.woman7};
//    public static String[] womenName = {"cat1", "cat2", "cat3", "cat4", "cat5", "cat6", "cat7"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profiletxt=findViewById(R.id.yourprofile);
        seeartist=findViewById(R.id.showartist);

        /**
         * gridview for product category
         */
//        gridcat = (GridView) findViewById(R.id.gridcat);
//        CategoryAdapter categoryAdapter = new CategoryAdapter(this);
//        gridcat.setAdapter(categoryAdapter);
//        /**
//         * grid view for women
//         */
//        gridView = (GridView) findViewById(R.id.gridView);
//        WomenAdapter adapter = new WomenAdapter(this);
//        gridView.setAdapter(adapter);
        reference=FirebaseDatabase.getInstance();


        /**
         * Image listener to show login dialogue;
         */
        ImageView imageView = findViewById(R.id.image);
//        TextView showtext = findViewById(R.id.login);

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        //Log.e(TAG, "onCreate: "+fuser.getEmail());
        if (fuser == null) {
            Intent intent=new Intent(MainActivity.this,LoginPage.class);
            Log.e(TAG, "onComplete: Login complete" );
            startActivity(intent);
        }
        else {
            String email = fuser.getUid();
            // String phone=email.split("@")[0];
            reference = FirebaseDatabase.getInstance();
            reference.getReference().child("users").child(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user.role.equals("ক্রেতা") || user.role.equals("Buyer")) {
                        startActivity(new Intent(MainActivity.this, CustomerProfilePage.class));
                        finish();
                    }
                    else
                    {
                        profiletxt.setText(user.getUsername().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilePage.class));
            }
        });
        recyclerView=findViewById(R.id.productlist);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AllProduct").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    AllProduct p=dataSnapshot.getValue(AllProduct.class);
                    productArrayList.add(p);
                    categoryAdapter.notifyItemInserted(productArrayList.size()-1);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        categoryAdapter= new CategoryAdapter(this);
        categoryAdapter.setProductlist(productArrayList);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         * login button for going to login page
         */
        MaterialButton loginbtn= (MaterialButton) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginPage.class));
                finish();
            }
        });
        seeartist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ArtistShow.class);
                startActivity(intent);
            }
        });
        /**
         * sign up text to go create account page
         */

        /**
         * view category to go to category page
         */
//        TextView viewcatergory = (TextView) findViewById(R.id.artisanlist);
//        viewcatergory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MainList.class));
//                finish();
//            }
//
//        });
    }
}