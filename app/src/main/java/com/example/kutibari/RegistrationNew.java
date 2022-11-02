package com.example.kutibari;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationNew extends AppCompatActivity {
    /**
     * database reference
     * @param savedInstanceState
     */
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_new);

        /**
         * code
         */
        final EditText mobile = findViewById(R.id.mobile);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText conpass = findViewById(R.id.conpass);

        final MaterialButton register = findViewById(R.id.signup);
        final TextView login = findViewById(R.id.loginnow);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance();

        /**
         * registering process
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * get data into string form from edit text
                 */
                 String phone = mobile.getText().toString();
                 String authemail = mobile.getText().toString()+"@gmail.com";
                 String user_name = username.getText().toString();
                 String pass = password.getText().toString();
                 String confirm = conpass.getText().toString();

                /**
                 * checking if all fields are fulfilled correctly
                 */
                if(user_name.isEmpty() || pass.isEmpty()){
                    Toast.makeText(RegistrationNew.this,"Please fill out all the fields",Toast.LENGTH_SHORT).show();
                }

                if(!pass.equals(confirm)){
                    Toast.makeText(RegistrationNew.this,"Passwords are not matched",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(authemail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                User user=new User(phone,user_name,pass);
                                try {
                                    reference.getReference().child("users").child(phone).push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            sendUserToNextActivity();
                                            Log.e(TAG, "onSuccess: Add hoise");
                                        }
                                    });
                                }
                                catch (Exception ignored){
                                    Log.e(TAG, "onComplete:a"+ignored.getMessage() );
                                }
                                                }
                            else
                            {
                                Toast.makeText(RegistrationNew.this, "Enter Strong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegistrationNew.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}