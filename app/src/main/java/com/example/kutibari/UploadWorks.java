package com.example.kutibari;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kutibari.databinding.ActivityUploadWorksBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadWorks extends AppCompatActivity {
    ActivityUploadWorksBinding binding;
    ImageView imageView;
    FloatingActionButton fab;
    EditText editprice,editday,editname;
    Uri ur;
    Button uploadworks;
    private String id,title,price,days;
    FirebaseDatabase database,database2;
    FirebaseStorage storage;
    ActivityResultLauncher<String> launcher ;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    String uname;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadWorksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView=findViewById(R.id.imageforworks);
        fab=findViewById(R.id.floatingActionButton);
        editprice=findViewById(R.id.editprice);
        editday=findViewById(R.id.editdays);
        uploadworks=findViewById(R.id.submitworks);
        editname=findViewById(R.id.editname);
        database=FirebaseDatabase.getInstance();
        database2=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        launcher= registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.imageforuploadworks.setImageURI(result);
                ur=result;
            }
        });;


        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                launcher.launch("image/*");
            }
        });
        uploadworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadWork();
                Intent intent=new Intent(UploadWorks.this,MainActivity.class);
                startActivity(intent);
            }
            
        });

    }

    private void uploadWork() {
//        id= UUID.randomUUID().toString();
        id= Long.toString(System.currentTimeMillis()/10000);
        title=editname.getText().toString();
        price=editprice.getText().toString();
        days=editday.getText().toString();
        final StorageReference reference=storage.getReference().child("image/"+id.toString());
        reference.putFile(ur).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uid=mAuth.getCurrentUser().getUid();
                        Product product=new Product(id,uri.toString(),title,price,days,uid);
                        Log.e(TAG, "onSuccess: "+ mAuth.getCurrentUser().getEmail() );


                        database2.getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user=snapshot.getValue(User.class);
                                uname=user.getUsername();
                                Log.e(TAG, "onDataChange: "+uname );
                                AllProduct allProduct=new AllProduct(id,title,uname,price,uid,uri.toString());
                                database2.getReference().child("AllProduct").child(id).setValue(allProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"product uploaded",Toast.LENGTH_SHORT);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        database.getReference().child(mAuth.getCurrentUser().getUid()).child("product").child(id).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"product uploaded",Toast.LENGTH_SHORT);
                            }
                        });
                    }
                });
            }
        });
    }
}