package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
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

public class FourCirclesAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container, circle1, circle2, circle3, circle4;
    RelativeLayout.LayoutParams params, layoutParams;
    ArrayList<CardModel> cardList;
    float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.allCards();

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        radius = (float) (screenWidth * 0.1);

        // circle layouts
        circle1 = new RelativeLayout(this);
        circle2 = new RelativeLayout(this);
        circle3 = new RelativeLayout(this);
        circle4 = new RelativeLayout(this);

        container.addView(circle1, layoutParams);
        container.addView(circle2, layoutParams);
        container.addView(circle3, layoutParams);
        container.addView(circle4, layoutParams);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        generateCircles();
    }

    private void generateCircles() {
        float x, y, rotation;
        float angle = (float) 360 / 13;

        ImageView prevImage = null;

        for (int i = 0; i < 13; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            x = (float) (centerX - radius * Math.sin(radiance));
            y = (float) (centerY + radius * Math.cos(radiance));

            if (prevImage == null)
                rotation = 0f;
            else
                rotation = prevImage.getRotation() + angle;

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;

            if (i < 13)
                circle1.addView(image);
            else if (i < 26)
                circle2.addView(image);
            else if (i < 39)
                circle3.addView(image);
            else
                circle4.addView(image);
        }
        animateCircle2();
    }

    private void animateCircle2() {
        for (int i = 0; i < 13; i++) {
            View image = findViewById(i);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, image.getRotation());
            valueAnimator.setDuration(1000L);
            valueAnimator.setInterpolator(new LinearInterpolator());

            valueAnimator.addUpdateListener(animation -> {
                float angle = (float) valueAnimator.getAnimatedValue();
                double radiance = Math.toRadians(angle);

                float x = (float) (centerX - radius * Math.sin(radiance));
                float y = (float) (centerY + radius * Math.cos(radiance));

                image.setX(x);
                image.setY(y);
                image.setRotation(angle);
            });
            valueAnimator.start();

            int finalI = i;
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalI == 12) {
                        bouncingAnimation();
                    }
                }
            });
        }
    }

    private void bouncingAnimation() {
        ObjectAnimator tX = ObjectAnimator.ofFloat(circle1, "translationX", circle1.getX() - 80f)
                .setDuration(3000L);
        ObjectAnimator tY = ObjectAnimator.ofFloat(circle1, "translationY", circle1.getY() + 300f)
                .setDuration(800L);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(circle1, "rotation", circle1.getRotation() - 70f)
                .setDuration(3000L);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.play(tY);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new DecelerateInterpolator());
        animatorSet1.playTogether(tX, rotate);

        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet, animatorSet1);
        animatorSet2.start();

    }
}