package com.osos.eduwaive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osos.eduwaive.Model.Message;
import com.osos.eduwaive.adapter.MessageAdapter;

import java.io.File;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
RecyclerView recyclerView;
AppCompatEditText editText;
ImageView imageView;
ArrayList<Message> list=new ArrayList<>();
FirebaseAuth auth;
ProgressBar progressBar;
DatabaseReference database;
Button boj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recyclerview);
        imageView=findViewById(R.id.imageView);
        database=FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.progressBar);
        editText=findViewById(R.id.appCompatEditText);
        auth=FirebaseAuth.getInstance();
        boj=findViewById(R.id.button2);
        progressBar.setVisibility(View.VISIBLE);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue(Message.class));



                }
                if(list.isEmpty())
                {
                    Toast.makeText(Main2Activity.this, "No Messages in the Chat Roon ", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{

                    MessageAdapter adapter=new MessageAdapter(Main2Activity.this,list);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Main2Activity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this, "Some Error", Toast.LENGTH_SHORT).show();
                  progressBar.setVisibility(View.INVISIBLE);
            }
        });




        boj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent boj=new Intent(Main2Activity.this,MainActivity.class);
                boj.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(boj);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText()==null){
                    Toast.makeText(Main2Activity.this, "Pal! You have Hands?", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.push().setValue(new Message(auth.getCurrentUser().getDisplayName(),editText.getText().toString()));
                    list.add(new Message(auth.getCurrentUser().getDisplayName(),editText.getText().toString()));
                     MessageAdapter messageAdapter=new MessageAdapter(Main2Activity.this,list);
                     LinearLayoutManager linearLayoutManager= new LinearLayoutManager(Main2Activity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(messageAdapter);
                    messageAdapter.notifyDataSetChanged();
                    editText.setText("");

                }
            }
        });





    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}

