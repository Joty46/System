package com.example.kutibari;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Works extends AppCompatActivity {
    ImageView wimage;
    TextView wtitle,wprice,wdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);
        wimage=findViewById(R.id.imageforworks);
        wtitle=findViewById(R.id.name);
    }
}