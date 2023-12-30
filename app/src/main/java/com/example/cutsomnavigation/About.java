package com.example.cutsomnavigation;

import static android.app.AlertDialog.THEME_HOLO_DARK;
import static android.app.AlertDialog.THEME_HOLO_LIGHT;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
Button upcomming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
upcomming = findViewById(R.id.upcomming);
        upcomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("1 - Server Centrelization\n2 - Career Prediction\n");

                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                builder.setCancelable(true);
                builder.setTitle("Upcomming Features\n"+"\n");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }
}