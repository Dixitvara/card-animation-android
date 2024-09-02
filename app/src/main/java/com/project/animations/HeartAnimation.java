package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
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

            float angle = (float) 180 / 10 * i - 1;
            double radians = Math.toRadians(angle);

            float angle2 = (float) 80 / 10 * i - 1;
            double radians2 = Math.toRadians(angle2);

            // right part of heart
            if (i == 1) {
                x = centerX - (float) cardWidth / 2 + 5;
                y = centerY + 5;
                rotation = -140;
            } else {
                rotation = i == 2 ? -90f : prevImg.getRotation();
                if (i <= 10) {
                    x = (float) (prevImg.getX() + Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() - Radius * Math.cos(radians));
                    rotation += 20;
                } else if (i < 14) {
                    x = ((float) (prevImg.getX() + radius * Math.cos(radians2)) - 5);
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation += 6;
                } else {
                    x = (float) (prevImg.getX() + radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = (prevImg.getRotation() + 11);
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);

            int index = i - 19;

            float angle = (float) 180 / 10 * index;
            double radians = Math.toRadians(angle);

            float angle2 = (float) 80 / 10 * index;
            double radians2 = Math.toRadians(angle2);

            if (index == 1) {
                x = (float) screenWidth / 2 - (float) cardWidth / 2;
                y = (float) screenHeight / 4;
                rotation = 90f;
            } else {
                rotation = index == 2 ? 90f : prevImg.getRotation();
                if (index <= 10) {
                    x = (float) (prevImg.getX() - Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() - Radius * Math.cos(radians));
                    rotation -= 20;
                } else if (index < 14) {
                    x = (float) (prevImg.getX() - radius * Math.cos(radians2)) + 5;
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation -= 6;
                } else {
                    x = (float) (prevImg.getX() - radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = prevImg.getRotation() - 11;
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 1; i <= 19; i++) {
            ImageView image = findViewById(i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);

            animateHeart(image, imageX, imageY, duration, startDelay, i - 1);
        }
        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);

            animateHeart(image, imageX, imageY, duration, startDelay, i - 19);
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
                    if(i == 18){
                        scaleAnimation();
                    }
                }).start();
    }

    public void scaleAnimation() {
        for (int i = 1; i < 39; i++) {
            ImageView image = findViewById(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f)
                    .setDuration(200);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f)
                    .setDuration(200);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(3);
            scaleY.setRepeatCount(3);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);

            animatorSet.start();
        }
    }
}