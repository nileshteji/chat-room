package com.osos.eduwaive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
FirebaseAuth obj;
TextView name,phone,email;
ImageView image;
Button butto;
public static final String CHANNEL_ID = "my_channel_01";
public static final String CHANNEL_NAME = "Firebase Notification";
public static final String CHANNEL_DESCRIPTION = "Firebase.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
obj=FirebaseAuth.getInstance();
name=findViewById(R.id.name);
email=findViewById(R.id.email);
butto=findViewById(R.id.button);

butto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

name.setText(obj.getCurrentUser().getDisplayName());
if(obj.getCurrentUser().getPhoneNumber().trim().equals("")){
    Toast.makeText(this, "Sorry", Toast.LENGTH_SHORT).show();
}
email.setText(obj.getCurrentUser().getEmail());






    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();

      AlertDialog.Builder boj=new AlertDialog.Builder(Main2Activity.this)
              .setTitle("Are YOu Sure You Want To Exit")
              .setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                  }
              });
      boj.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
finishAffinity();
          }
      });
      boj.show();

    }
}
