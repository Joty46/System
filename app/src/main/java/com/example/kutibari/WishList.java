package com.example.kutibari;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class WishList extends AppCompatActivity {
    /**
     * grid view and arrays for category of products
     */
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase reference;
    private RecyclerView recyclerView;
    private ArrayList<AllProduct> productArrayList=new ArrayList<>();
    CategoryAdapter categoryAdapter;
    DatabaseReference databaseReference;
    TextView customeruname;
    TextView showartist;

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
        setContentView(R.layout.activity_wish_list);
     //   customeruname=findViewById(R.id.cusername);
        Log.e(TAG, "onCreate: I have comed to this page" );

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
        reference= FirebaseDatabase.getInstance();


        /**
         * Image listener to show login dialogue;
         */
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uuid=user.getUid();
//        reference.getReference().child("users").child(uuid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if(user.role.equals("????????????????????????") || user.role.equals("Seller"))
//                {
//                    startActivity(new Intent(WishList.this,MainActivity.class));
//                    finish();
//                }
//                else
//                {
//                    customeruname.setText(""+user.getUsername().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        recyclerView=findViewById(R.id.productlist);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Wishlist").child("user").child("product").addValueEventListener(new ValueEventListener() {
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
                startActivity(new Intent(WishList.this, LoginPage.class));
                finish();
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
//                startActivity(new Intent(CustomerProfilePage.this, MainList.class));
//                finish();
//            }
//
//        });
    }
}