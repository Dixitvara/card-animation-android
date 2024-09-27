package com.project.animations;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class SemiCircleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList;
    RelativeLayout container;
    Button resetBtn;
    RecyclerView.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        params = new RecyclerView.LayoutParams(cardWidth, cardHeight);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        cardList = CardMethods.generateSelectedCards(52, new int[]{0, 1, 2, 3});

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateShape();
    }

    private void generateShape() {

        int radius = (int) (screenWidth * 0.02);

        float rotation;
        float x, y;

        ImageView prevImg = null;

        for (int i = 0; i < 13; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            if (i < 6) {
                image.setTag("minusAngle");
            } else if (i > 6) {
                image.setTag("plusAngle");
            }

            float radiance = (float) (Math.toRadians((double) 180 / 13) * i);

            x = i == 0 ? (float) screenWidth / 4 - (float) cardWidth / 2 : (float) (prevImg.getX() + radius * Math.sin(radiance));
            y = i == 0 ? (float) (screenHeight * 0.2) : (float) (prevImg.getY() - radius * Math.cos(radiance));
            rotation = i == 0 ? -90f : prevImg.getRotation() + 15f;

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }
        for (int i = 13; i < 26; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            if (i < 19) {
                image.setTag("minusAngle");
            } else if (i > 19) {
                image.setTag("plusAngle");
            }

            float radiance = (float) (Math.toRadians((double) 180 / 13) * i);

            x = i == 13 ? (float) (screenWidth * 0.5) : (float) (prevImg.getX() - radius * Math.sin(radiance));
            y = i == 13 ? (float) (screenHeight * 0.35) : (float) (prevImg.getY() + radius * Math.cos(radiance));
            rotation = i == 13 ? -90f : prevImg.getRotation() + 15f;

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }
        for (int i = 26; i < 39; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            if (i < 32) {
                image.setTag("minusAngle");
            } else if (i > 32) {
                image.setTag("plusAngle");
            }

            float radiance = (float) (Math.toRadians((double) 180 / 13) * i);

            x = i == 26 ? (float) screenWidth / 4 - (float) cardWidth / 2 : (float) (prevImg.getX() + radius * Math.sin(radiance));
            y = i == 26 ? (float) (screenHeight * 0.5) : (float) (prevImg.getY() - radius * Math.cos(radiance));
            rotation = i == 26 ? -90f : prevImg.getRotation() + 15f;

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }
        for (int i = 39; i < 52; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i < 45) {
                image.setTag("minusAngle");
            } else if (i > 45) {
                image.setTag("plusAngle");
            }

            float radiance = (float) (Math.toRadians((double) 180 / 13) * i);

            x = i == 39 ? (float) (screenWidth * 0.5) : (float) (prevImg.getX() - radius * Math.sin(radiance));
            y = i == 39 ? (float) (screenHeight * 0.65) : (float) (prevImg.getY() + radius * Math.cos(radiance));
            rotation = i == 39 ? -90f : prevImg.getRotation() + 15f;

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }

        for (int i = 0; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);
            float x1 = image.getX();
            float y1 = image.getY();

            if (i < 13 || i > 25 && i < 39) {
                image.setX(-cardWidth);
                image.setY((float) screenHeight / 2);
            } else {
                image.setX(screenWidth);
                image.setY((float) screenHeight / 2);
            }
            inAnimation(image, x1, y1, i);
        }
    }

    private void inAnimation(View image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(600L)
                .setStartDelay(10L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == container.getChildCount() - 1)
                        radiusIncreasedAnimation();
                })
                .start();
    }

    private void radiusIncreasedAnimation() {
        for (int i = 0; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);
            if ("minusAngle".equals(image.getTag())) {
                image.animate()
                        .rotationBy(-15)
                        .setDuration(3000L)
                        .setInterpolator(new LinearInterpolator())
                        .start();
            }
            if ("plusAngle".equals(image.getTag())) {
                image.animate()
                        .rotationBy(15)
                        .setDuration(3000L)
                        .setInterpolator(new LinearInterpolator())
                        .start();
            }
        }
        new Handler().postDelayed(this::outAnimation, 3000);
    }

    private void outAnimation() {
        long delay;
        for (int i = 0; i < container.getChildCount(); i++) {
            if (i < 13)
                delay = 20L * i;
            else if (i < 26)
                delay = 20L * (i - 13);
            else if (i < 39)
                delay = 20L * (i - 26);
            else
                delay = 20L * (i - 39);

            View image = findViewById(i);
            image.animate()
                    .translationX(-cardWidth)
                    .translationY(-cardWidth)
                    .setDuration(500L)
                    .setStartDelay(delay)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }
}