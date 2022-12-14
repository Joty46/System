package com.example.kutibari;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;
import java.util.List;

public class RegisterScan extends AppCompatActivity {

    private Button captureImagebtn, detectTextbtn;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scan);


        captureImagebtn = findViewById(R.id.capture_image);
        detectTextbtn = findViewById(R.id.detect_text_image);
        imageView = findViewById(R.id.image_view);
        textView1 = findViewById(R.id.text_display1);
        textView2 = findViewById(R.id.text_display2);
        textView3 = findViewById(R.id.text_display3);

        captureImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                textView1.setText("");
                textView2.setText("");
                textView3.setText("");
//                Toast.makeText(RegisterScan.this, "image captured", Toast.LENGTH_SHORT).show();
            }
        });

        detectTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage();
//                Toast.makeText(RegisterScan.this, "text detected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
//            Toast.makeText(RegisterScan.this, "dispatched", Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Toast.makeText(RegisterScan.this, "bitmap", Toast.LENGTH_SHORT).show();
        }
    }

    private void detectTextFromImage() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
//        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        FirebaseVisionTextRecognizer firebaseVisionTextDetector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
//        Toast.makeText(RegisterScan.this, "detect button clicked", Toast.LENGTH_SHORT).show();
        firebaseVisionTextDetector.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {

            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImage(firebaseVisionText);
//                Toast.makeText(RegisterScan.this, "final", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterScan.this, "Error: " +e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error: ", e.getMessage());
            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
        if(blockList.size() == 0){
            Toast.makeText(RegisterScan.this, "Please take the photo again", Toast.LENGTH_SHORT).show();
        }
        else{

            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {

//                Toast.makeText(RegisterScan.this, "entered in loop", Toast.LENGTH_SHORT).show();

                String text = block.getText().toString();
//                String text1 = block.getText().toString();
//                String text2 = block.getText().toString();
                String a,b,c,d;

//                a=getNextWord(text,"e:");
//                b=getNextWord(text1,"h:");
                c=getNextWord(text,"NO:");

//                textView1.setText(text);
//                textView2.setText(text1);
                if(c.length()>=10) {
                    textView3.setText(c);
                    Intent intent = new Intent(RegisterScan.this, RegistrationNew.class);
                    intent.putExtra("nid", c);
                    startActivity(intent);
                    finish();
                }
                else {
                    textView3.setText("??????????????????, ??????????????? ?????????????????? ???????????????????????????????????? ????????????????????? ?????????, ???????????? ??????????????? ?????????????????? ???????????? ???????????? ?????????????????? ????????? ????????? ???????????????");
                }
            }
        }
    }

    public static String getNextWord(String str, String word) {
        String[] words = str.split(" "), data = word.split(" ");
        int index = Arrays.asList(words).indexOf((data.length > 1) ? data[data.length - 1] : data[0]);
        return (index == -1) ? "Not Found" : ((index + 1) == words.length) ? "End" : words[index + 1];
    }
}