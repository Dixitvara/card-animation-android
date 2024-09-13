package com.project.animations;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
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
    ArrayList<CardModel> cardList;
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

        cardList = CardMethods.generateSelectedCards(26, new int[]{1, 2});

        centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        params = new RelativeLayout.LayoutParams(80, 110);
        radius = (float) (screenWidth * 0.08);

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

        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2;
            float radiance;

            angle2 = angle * i;
            radiance = (float) Math.toRadians(angle2);

            if (i == 1) {
                x = (float) (centerX + radius * Math.sin(radiance));
                y = (float) (centerY - radius * Math.cos(radiance));
                rotation = angle;
            } else {
                x = (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = prevImg.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }

        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

//            image.setX((float) screenWidth /2);
//            image.setY(screenHeight);

        }
    }

    private void rotateAnimation() {
        float angle = (float) 360 / 13;

        for (int i = 0; i < 13; i++) {
            final ImageView image = findViewById(i);

            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setDuration(5000);
            animator.setInterpolator(new DecelerateInterpolator());

            final int index = i;

            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float currentAngle = angle * index + animatedValue;
                float x = (float) (centerX + radius * Math.sin(Math.toRadians(currentAngle)));
                float y = (float) (centerY - radius * Math.cos(Math.toRadians(currentAngle)));

                image.setX(x);
                image.setY(y);
                image.setRotation(currentAngle);
            });

            animator.start();
        }
    }
}
