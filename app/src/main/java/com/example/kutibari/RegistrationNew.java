package com.example.kutibari;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;

public class RegistrationNew extends AppCompatActivity {
    /**
     * database reference
     * @param savedInstanceState
     */
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase reference;
    String role,role1;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_new);
        radioGroup=(RadioGroup)findViewById(R.id.userrole);

        /**
         * code
         */
        TextView receiver_msg = findViewById(R.id.nidnumber);
        Intent intent = getIntent();
        String str = intent.getStringExtra("nid");
        // display the string into textView
        receiver_msg.setText(str);

        final EditText mobile = findViewById(R.id.mobile);
//        final EditText mailid = findViewById(R.id.email);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText conpass = findViewById(R.id.conpass);
       // final EditText userrole=findViewById(R.id.userrole);

        final MaterialButton register = findViewById(R.id.signup);
        final TextView login = findViewById(R.id.loginnow);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                    }
                });
        /**
         * registering process
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * get data into string form from edit text
                 */
//                String phone = mobile.getText().toString()+"@gmail.com";
                String mail = mobile.getText().toString()+"@gmail.com";
                String user_name = username.getText().toString();
                String[] pass = {password.getText().toString()};
                String confirm = conpass.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                byte input[] = password.getText().toString().getBytes();
                byte output[] = new byte[0];

                try {
                    output = sha.encryptSHA(input,"SHA-256");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BigInteger shaData= new BigInteger(1,output);
                System.out.println(shaData);
                String b1String = shaData.toString(1);
                System.out.println(b1String);


                if (selectedId == -1) {
                    Toast.makeText(RegistrationNew.this, "No role has been selected", Toast.LENGTH_SHORT).show();
                }
                else {

                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);

                    // Now display the value of selected item
                    // by the Toast message
                    role=radioButton.getText().toString();
                }
              //  role1=userrole.getText().toString();
               // role=role1.substring(0, 1).toUpperCase()+role1.substring(1).toLowerCase();

                /**
                 * checking if all fields are fulfilled correctly
                 */
                if(user_name.isEmpty() || pass[0].isEmpty()){
                    Toast.makeText(RegistrationNew.this,"Please fill out all the fields",Toast.LENGTH_SHORT).show();
                }

                if(!pass[0].equals(confirm)){
                    Toast.makeText(RegistrationNew.this,"Passwords are not matched",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(mail,b1String).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String uuid=task.getResult().getUser().getUid();
                                Log.e(TAG, "onComplete: "+uuid);
                                User user=new User(mail,user_name,b1String,role,uuid);
                                try {
                                    reference.getReference().child("users").child(uuid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            sendUserToNextActivity();
                                            Log.e(TAG, "onSuccess: Add hoise");
                                        }
                                    });
                                }
                                catch (Exception e){
                                    Log.e(TAG, "onComplete:a"+e.getMessage() );
                                }
                            }
                            else
                            {
                                Log.e(TAG, "onComplete:" + task.getException() );
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
                startActivity(new Intent(RegistrationNew.this,LoginPage.class));
                finish();
            }
        });
    }
    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegistrationNew.this,LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}