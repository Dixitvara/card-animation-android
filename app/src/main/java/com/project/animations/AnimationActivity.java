package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {

    Button button, resetBtn;
    RelativeLayout container;
    ImageView image;
    float x = 0f;
    float y = 0f;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        button = findViewById(R.id.startAnimationBtn);
        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        // getting device height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // set those in variables
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        button.setOnClickListener(v -> {

            image = new ImageView(this);
            RelativeLayout.LayoutParams imgParam = new RelativeLayout.LayoutParams(100, 150);

            if (x >= width) {
                y += 30;
                x = 0;
            }
            image.setImageResource(R.drawable.h10);
            image.setX(x);
            image.setY(y);
            image.setLayoutParams(imgParam);
            container.addView(image);

            image.animate()
                    .translationX(x)
                    .translationY(0f)
                    .setDuration(200)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
            x += 200;
        });
    }
}