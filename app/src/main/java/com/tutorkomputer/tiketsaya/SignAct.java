package com.tutorkomputer.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername,xpassword;
    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);


        btn_new_account=findViewById(R.id.btn_new_account);
        btn_sign_in=findViewById(R.id.btn_sign_in);
        xusername=findViewById(R.id.xusername);
        xpassword=findViewById(R.id.xpassword);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

               final String username =xusername.getText().toString();
               final String password =xpassword.getText().toString();

                reference= FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(username);

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"username harus di isi",Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");

                }else{
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"password harus di isi",Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }else{
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){

                                    //ambil data password dari firebase
                                    String passwordFromFirebase =dataSnapshot.child("password").getValue().toString();

                                    //validasi password dengan firebase
                                    if (password.equals(passwordFromFirebase)){
                                        //simpan username key ke local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key,xusername.getText().toString());
                                        editor.apply();



                                        //berpindah activity
                                        Intent gotohomeact= new Intent(SignAct.this,HomeAct.class);
                                        startActivity(gotohomeact);


                                    }else{
                                        Toast.makeText(getApplicationContext(),"password salah",Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");

                                    }

                                }else{
                                    Toast.makeText(getApplicationContext(),"username tidak ada",Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Database Error !",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }


            }
        });
        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone= new Intent(SignAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);

            }
        });
    }
}
