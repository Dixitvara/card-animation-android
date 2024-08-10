package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class FireworksActivity extends AppCompatActivity {

    Button resetBtn;
    DisplayMetrics displayMetrics;
    RelativeLayout container;
    int width, height;
    LinkedList<ImageView> cardList;

    int x = 300, y = 250, z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cardList = new LinkedList<>();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        for (int i = 0; i < 12; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 150);

//            image.setImageResource(R.drawable.as);
            image.setLayoutParams(params);
            image.setX(x);
            image.setY(y);
            image.setRotation(z);

            cardList.add(image);
            container.addView(image);

            x += 30;
            y += 15;
            z += 10;

        }
    }

    private void animation(ImageView image) {
        image.animate()
                .translationX(0)
                .translationY(0)
                .setDuration(500)
//                .rotation(x)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        x += 5;
    }

}