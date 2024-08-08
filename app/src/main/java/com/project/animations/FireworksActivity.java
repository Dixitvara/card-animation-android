package com.project.animations;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Random;

public class FireworksActivity extends AppCompatActivity {

    Button resetBtn;
    DisplayMetrics displayMetrics;
    RelativeLayout container;
    int randomWidth, randomHeight;
    int width, height;
    Handler handler;
    Random random;
    LinkedList<ImageView> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        handler = new Handler();
        random = new Random();
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

        for (int i = 0; i < 25; i++) {

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 150);

            image.setImageResource(R.drawable.d4);
            image.setLayoutParams(params);
            image.setX((float) width / 2);
            image.setY(height);
            image.setRotation(0f);

            cardList.add(image);
            container.addView(image);
        }
        animateCards();
    }

    private void animateCards() {
        for (int i = 0; i < cardList.size(); i++) {
            int finalI = i;
            handler.postDelayed(() -> {
                animate(cardList.get(finalI));
            }, 120L * i);
        }
    }

    private void animate(ImageView image) {
        randomWidth = random.nextInt(width);
        randomHeight = random.nextInt(height);

        image.animate()
                .translationX((float) randomWidth)
                .translationY((float) randomHeight)
                .setDuration(500)
                .rotation(random.nextInt(361))
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}