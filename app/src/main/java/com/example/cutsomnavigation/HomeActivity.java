package com.example.cutsomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cutsomnavigation.MainActivity;

public class HomeActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIMEOUT=3000;
    Animation top,bottom;
    TextView slogan;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        top= AnimationUtils.loadAnimation(this,R.anim.top);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom);
        image = findViewById(R.id.image);
        image.setAnimation(top);
        slogan=findViewById(R.id.slogan);
        slogan.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }
}
