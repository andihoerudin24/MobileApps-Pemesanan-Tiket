package com.tutorkomputer.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username,password,email_addres;
    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email_addres=findViewById(R.id.email_addres);



        btn_continue=findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UBAH STATE MENJADI LOADING
                btn_continue.setEnabled(false);
                btn_continue.setText("loading ...");


                //menyimpan data di local storeage
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key,username.getText().toString());
                editor.apply();

//                //tes apakah username sudah masuk
//                Toast.makeText(getApplicationContext(),"Username" +
//                        username.getText().toString(),Toast.LENGTH_SHORT).show();

                // simpan database
                reference= FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("email_addres").setValue(email_addres.getText().toString());
                        dataSnapshot.getRef().child("user_balance").setValue(1000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //BERPINDAH AKTIFITY
                Intent gotoregistertwo= new Intent(RegisterOneAct.this,RegisterTwoAct.class);
                startActivity(gotoregistertwo);
            }
        });

        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backtosign =new Intent(RegisterOneAct.this,SignAct.class);
                startActivity(backtosign);
            }
        });

    }



}
