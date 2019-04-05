package com.tutorkomputer.tiketsaya;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash,btt;
    ImageView app_logo;
    TextView app_subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load animasi
        app_splash= AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt= AnimationUtils.loadAnimation(this,R.anim.btt);

        //app_logo
        app_logo=findViewById(R.id.app_logo);
        app_subtitle=findViewById(R.id.app_subtitle);

        //run animation
        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btt);

        //setting timer untuk 2 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //merubah actifity ke actifity lain
                Intent gotostarted =new Intent(SplashAct.this,GetStartedAct.class);
                startActivity(gotostarted);
                finish();
            }
        },2000);
    }
}
