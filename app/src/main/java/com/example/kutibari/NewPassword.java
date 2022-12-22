package com.example.kutibari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kutibari.databinding.ActivityOtpSendBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewPassword extends AppCompatActivity {

    private ActivityOtpSendBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth auth = FirebaseAuth.getInstance();

//        Toast.makeText(NewPassword.this,"Your password has been updated Successfully",Toast.LENGTH_SHORT).show();


    //    String receivemsg="01553841598@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);


        String receivemsg=getIntent().getStringExtra("number");
        final EditText pass = findViewById(R.id.password);
        final MaterialButton submit = findViewById(R.id.submit_new_pass);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NewPassword.this,receivemsg,Toast.LENGTH_SHORT).show();

                final String phone = pass.getText().toString();
                if(!phone.matches("")) {
                    /**
                     * not empty
                     */
                    databaseReference= FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //userArrayList.clear();
                            for(DataSnapshot dataSnapshot:snapshot.getChildren())
                            {

                                User u=dataSnapshot.getValue(User.class);
                                String umail=u.getMail();
                                if(umail.equals(receivemsg)){
//                                String pass = u.getPassword();
                                    
                                    databaseReference.child("users").child(u.getUid()).child("password").setValue(phone);
                                    Toast.makeText(NewPassword.this,"Your password has been updated Successfully",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    Toast.makeText(NewPassword.this,"Sorry password can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



        /**
         * redirect to login page
         */
    public void openLogin(){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        finish();
    }
}