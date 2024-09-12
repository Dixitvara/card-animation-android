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

public class TestingClass extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;
    RelativeLayout container;
    Button resetBtn;

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

        cardList = CardMethods.generateCards(13, 0);

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        createCircles();

    }

    private void createCircles() {

        float radius = (float) (screenWidth * 0.10);
        float angle = (float) 360 / 13;
        float x, y;
        float rotation;

        float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        float centerY = (float) screenHeight / 2 - (float) cardHeight / 2;
        ImageView prevImg = null;

        for (int i = 1; i <= cardList.size(); i++) {
            CardModel card = cardList.get(i - 1);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            float angle2;
            float radiance;

            angle2 = angle * i;
            radiance = (float) Math.toRadians(angle2);
            if (prevImg == null) {
                x = (float) (screenWidth * 0.35 - (double) cardWidth / 2);
                y = (float) (screenHeight * 0.4);
                rotation = -45f;
            } else {
                x = (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
            if (i != 1) {
//                image.setVisibility(ImageView.INVISIBLE);
            }

            container.addView(image);
        }

        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);

            float x1 = image.getX();
            float y1 = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);

            animateCircle(image, x1, y1, i);
        }
    }

    private void animateCircle(ImageView image, float x1, float y1, int i) {
        image.animate()
                .translationX(x1)
                .translationY(y1)
                .setDuration(300L)
                .setStartDelay(50L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x1);
                    image.setY(y1);
                    if (i == cardList.size())
//                        rotateAnimation();
                        rotateAnimation2();
                })
                .start();
    }

    private void rotateAnimation() {
        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);
            ImageView nextImg = findViewById(i == 13 ? 1 : i + 1);

            image.animate()
                    .translationX(nextImg.getX())
                    .translationY(nextImg.getY())
                    .rotation(nextImg.getRotation())
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(1000L)
                    .setStartDelay(0L)
                    .withEndAction(() -> {
                    })
                    .start();
        }
    }

    private void rotateAnimation2() {
        float radius = (float) (screenWidth * 0.10);
        float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        float centerY = (float) screenHeight / 2 - (float) cardHeight / 2;
        float anglePerCard = (float) 360 / cardList.size();  // Angle between each card

        for (int i = 1; i <= cardList.size(); i++) {
            final ImageView image = findViewById(i);

            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setDuration(5000);
            animator.setInterpolator(new LinearInterpolator());

            final int index = i - 1;

            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float currentAngle = anglePerCard * index + animatedValue;

                float x = (float) (centerX + radius * Math.cos(Math.toRadians(currentAngle)));
                float y = (float) (centerY + radius * Math.sin(Math.toRadians(currentAngle)));

                image.setX(x);
                image.setY(y);
                image.setRotation(currentAngle);
            });

            animator.start();
        }
    }

}