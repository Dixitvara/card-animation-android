package com.project.animations;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class EightCircleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList, cardList2;
    RelativeLayout.LayoutParams params;
    RelativeLayout container;
    Button resetBtn;
    float centerX, centerY;
    float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        container = findViewById(R.id.container);
        resetBtn = findViewById(R.id.resetBtn);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        cardList = CardMethods.generateSelectedCards(52, new int[]{1, 2, 3, 0});
        cardList2 = CardMethods.generateSelectedCards(52, new int[]{1, 2, 3, 0});

        centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        params = new RelativeLayout.LayoutParams(80, 110);

        radius = (float) (screenWidth * 0.1);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        createCircles();
    }

    private void createCircles() {
        ImageView prevImg = null;

        float angle = (float) 360 / 13;
        float x, y;
        float rotation;

        for (int i = 0; i < 104; i++) {
            CardModel card;
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);

            if (i < 52)
                card = cardList.get(i);
            else
                card = cardList2.get(i - 52);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i < 13) {
                x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.15 - radius * Math.cos(radiance));
            } else if (i < 26) {
                x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.15 - radius * Math.cos(radiance));
            } else if (i < 39) {
                x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.35 - radius * Math.cos(radiance));
            } else if (i < 52) {
                x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.35 - radius * Math.cos(radiance));
            } else if (i < 65) {
                x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.55 - radius * Math.cos(radiance));
            } else if (i < 78) {
                x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.55 - radius * Math.cos(radiance));
            } else if (i < 91) {
                x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.75 - radius * Math.cos(radiance));
            } else {
                x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.75 - radius * Math.cos(radiance));
            }

            if (prevImg == null) {
                rotation = angle2;
            } else {
                rotation = prevImg.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }

        for (int i = 0; i < 104; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);

            addToViewAnimation(image, x1, y1, i);
        }
    }

    private void addToViewAnimation(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(400L)
                .setStartDelay(30L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == 103) {
                        rotateAnimation();
                    }
                })
                .start();
    }


    private void rotateAnimation() {
        float angle = (float) 360 / 13;

        for (int i = 0; i < 104; i++) {
            ImageView image = findViewById(i);

            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setDuration(3000);
            animator.setInterpolator(new LinearInterpolator());

            final int index = i;

            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float currentAngle = angle * index + animatedValue;
                float x, y;
                double radiance = Math.toRadians(currentAngle);

                if (index < 13) {
                    x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.15 - radius * Math.cos(radiance));
                } else if (index < 26) {
                    x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.15 - radius * Math.cos(radiance));
                } else if (index < 39) {
                    x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.35 - radius * Math.cos(radiance));
                } else if (index < 52) {
                    x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.35 - radius * Math.cos(radiance));
                } else if (index < 65) {
                    x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.55 - radius * Math.cos(radiance));
                } else if (index < 78) {
                    x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.55 - radius * Math.cos(radiance));
                } else if (index < 91) {
                    x = (float) (screenWidth * 0.25 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.75 - radius * Math.cos(radiance));
                } else {
                    x = (float) (screenWidth * 0.65 + radius * Math.sin(radiance));
                    y = (float) (screenHeight * 0.75 - radius * Math.cos(radiance));
                }
                image.setX(x);
                image.setY(y);
                image.setRotation(currentAngle);
            });

            animator.start();
        }
    }
}
