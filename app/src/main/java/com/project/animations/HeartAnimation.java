package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;

public class HeartAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    int cardWidth, cardHeight;
    float Radius;
    int centerX, centerY;

    // views
    Button resetBtn;
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        centerX = screenWidth / 2;
        centerY = screenHeight / 4;

        Radius = (float) (screenWidth * 0.060);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCard();

    }

    private void generateCard() {
        for (int i = 1; i <= 38; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("hearts_" + i, "drawable", this.getPackageName());
            else if (i <= 26) {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 13), "drawable", this.getPackageName());
            } else {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 26), "drawable", this.getPackageName());
            }

            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);
            container.addView(image);
        }
        animation();
    }

    private void animation() {
        float radius = (int) (screenWidth * 0.075);
        float rotation;

        ImageView prevImg = null;

        float x, y;
        int duration = 300;
        long startDelay = 50L;

        for (int i = 1; i <= 19; i++) {
            ImageView image = findViewById(i);

            int angle = 180 / 10 * i;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / 10 * i;
            double radians2 = Math.toRadians(angle2);

            if (i == 1) {
                x = (int) (centerX - (float) cardWidth / 2 + 5);
                y = centerY + 5;
                rotation = -140;
            } else {
                rotation = i == 2 ? -90f : prevImg.getRotation();
                if (i <= 10) {
                    x = (float) (prevImg.getX() + Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() - Radius * Math.cos(radians));
                    rotation += 20f;
                } else if (i < 14) {
                    x = ((float) (prevImg.getX() + radius * Math.cos(radians2)) - 5);
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation += 6f;
                } else {
                    x = (float) (prevImg.getX() + radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = (int) (prevImg.getRotation() + 11f);
                }
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            prevImg = image;
        }

        prevImg = null;

        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);

            int index = i - 19;
            int angle = 180 / 10 * index;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / 10 * index;
            double radians2 = Math.toRadians(angle2);

            if (index == 1) {
                x = (float) screenWidth / 2 - (float) cardWidth / 2;
                y = (float) screenHeight / 4;
                rotation = 90f;
            } else {
                if (index <= 10) {
                    x = (float) (prevImg.getX() - Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() - Radius * Math.cos(radians));
                    rotation = (int) (prevImg.getRotation() - 20);
                } else if (index < 14) {
                    x = (int) (prevImg.getX() - radius * Math.cos(radians2)) + 5;
                    y = (int) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = (int) (prevImg.getRotation() - 6);
                } else {
                    x = (int) (prevImg.getX() - radius * Math.cos(radians2));
                    y = (int) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = (int) (prevImg.getRotation() - 11);
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            prevImg = image;
        }

        for (int i = 1; i <= 38; i++) {
            ImageView image = findViewById(i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);
            animateHeart(image, imageX, imageY, duration, startDelay, i - 1);
        }
    }

    private void animateHeart(ImageView image, float x, float y, int duration, long startDelay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .setStartDelay(startDelay * i)
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                })
                .start();
    }
}