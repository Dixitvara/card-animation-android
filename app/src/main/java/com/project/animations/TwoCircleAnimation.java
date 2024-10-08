package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;
import com.project.animations.utils.MyAnim;

import java.util.ArrayList;

public class TwoCircleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;
    RelativeLayout topCircle, bottomCircle;
    LinearLayout linearContainer;
    Button resetBtn;
    float centerX, centerY;
    final int TOTAL_CARDS = 26;
    float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_linear_layout);

        resetBtn = findViewById(R.id.resetBtn);
        linearContainer = findViewById(R.id.containerLinear);

        topCircle = new RelativeLayout(this);
        bottomCircle = new RelativeLayout(this);

        linearContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                0
        );
        layoutParams.weight = 1;
        linearContainer.addView(topCircle, layoutParams);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                0
        );
        layoutParams2.weight = 1;
        linearContainer.addView(bottomCircle, layoutParams2);

//        topCircle.setBackgroundColor(Color.RED);
//        bottomCircle.setBackgroundColor(Color.BLUE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        cardList = CardMethods.generateSelectedCards(26, new int[]{1, 2});

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        radius = (float) (screenWidth * 0.12);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        createCircles();
    }

    private void createCircles() {
        float angle = (float) 360 / 13;
        float x, y;
        float rotation;

        ImageView prevImg = null;

        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = new ImageView(this);

            CardModel card = cardList.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2;
            float radiance;

            if (i < 13) {
                angle2 = angle * i;
                radiance = (float) Math.toRadians(angle2);
                y = (float) (screenHeight * 0.3 - radius * Math.cos(radiance));
                rotation = i == 0 ? angle2 : prevImg.getRotation() + angle;
            } else {
                angle2 = angle * (i - 13);
                radiance = (float) Math.toRadians(angle2);
                y = (float) (screenHeight * 0.07 - radius * Math.cos(radiance));
                rotation = i == 13 ? angle2 : prevImg.getRotation() + angle;
            }

            x = (float) (centerX + radius * Math.sin(radiance));

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            if (i < 13) {
                topCircle.addView(image);
            } else {
                bottomCircle.addView(image);
            }

            prevImg = image;
        }

        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX(centerX);
            image.setY(i < 13 ? screenHeight * -1 : screenHeight);

            animateCircle(image, x1, y1, i + 1);
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
                    if (i == TOTAL_CARDS - 1)
                        rotateAnimation();
                })
                .start();
    }

    private void rotateAnimation() {
        float angle = (float) 360 / 13;

        for (int i = 0; i < TOTAL_CARDS; i++) {
            final ImageView image = findViewById(i);

            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setDuration(5000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            final int index = i;

            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float currentAngle = angle * index + animatedValue;

                double radiance = Math.toRadians(currentAngle);

                float x = (float) (centerX + radius * Math.sin(radiance));
                float y;
                if (index < 13) {
                    y = (float) (screenHeight * 0.3 - radius * Math.cos(radiance));
                } else {
                    y = (float) (screenHeight * 0.07 - radius * Math.cos(radiance));
                }
                image.setX(x);
                image.setY(y);
                image.setRotation(angle * index + (animatedValue * 2));
            });

            animator.start();

            int finalI = i;
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalI == TOTAL_CARDS - 1)
                        outAnimation();
                }
            });
        }
    }

    private void outAnimation() {
        MyAnim.translateYTo(topCircle, screenHeight * -1, 1500L);
        MyAnim.translateYTo(bottomCircle, screenHeight, 1500L);
    }
}