package com.example.kutibari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NewPassword extends AppCompatActivity {


    FirebaseDatabase reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        /**
         * code for new password
         */
        final TextView password = (TextView) findViewById(R.id.password);
        MaterialButton submit = (MaterialButton) findViewById(R.id.submit_new_pass);
        reference=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().matches("")){
                    /**
                     * not empty
                     */

                }
                else{
                    /**
                     * empty
                     */
                    Toast.makeText(NewPassword.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
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
    }
}