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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    FirebaseDatabase reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        /**
         * login page work
         */
        final EditText mobile = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final MaterialButton loginbtn = findViewById(R.id.loginbtn);
        final TextView regnow = findViewById(R.id.regnow);
        reference=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * code
                 */
                final String phone = mobile.getText().toString();
                final String[] pass = {password.getText().toString()};

                if(phone.isEmpty()){
                    Toast.makeText(LoginPage.this,"Please Enter your username",Toast.LENGTH_SHORT).show();
                }
                else if(pass[0].isEmpty() ){
                    Toast.makeText(LoginPage.this,"Please Enter your password",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(phone,pass[0]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                reference.getReference().child("users").child(mobile.getText().toString()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User user = snapshot.getValue(User.class);
                                        if(user.role.equals("ক্রেতা") || user.role.equals("Buyer"))
                                        {
                                            startActivity(new Intent(LoginPage.this, CustomerProfilePage.class));
                                            finish();
                                        }
                                        else
                                        {
                                            startActivity(new Intent(LoginPage.this, MainActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Log.e(TAG, "onComplete: Login complete" );
                            }
                            else{
                                Log.e(TAG, "onComplete: "+task.getException() );
                                Toast.makeText(LoginPage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//                    reference.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            //check if mobile phone exits in firebase
//                            if(snapshot.hasChild(phone)){
//                                //mobile exits in firebase now take password from user
//                                final String getpass = snapshot.child(phone).child("password").getValue(String.class);
//                                if(getpass.equals(pass)){
//                                    Toast.makeText(LoginPage.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//
//                                }
//                                else{
//                                    Toast.makeText(LoginPage.this, "Sorry you provided wrong password", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else {
//                                Toast.makeText(LoginPage.this, "Sorry you don't have an account, please create an account first", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(LoginPage.this, RegistrationNew.class));
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }
            }
        });

        /**
         * go to register page
         */
        regnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterScan.class));
                finish();
            }
        });

        TextView forgotpass = (TextView) findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, OtpSend.class));
                finish();
            }

        });
    }

    /**
     * go to forget password page
     */
//    public void openforgotpass(){
//
//        Intent intent = new Intent(this, ForgetPassword.class);
//        startActivity(intent);
//    }

    /**
     * go to home page
     */
//    public void gotohomepage(){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
}