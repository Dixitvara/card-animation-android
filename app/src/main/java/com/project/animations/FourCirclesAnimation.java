package com.project.animations;

import static com.project.animations.utils.CardMethods.cardHeight;
import static com.project.animations.utils.CardMethods.cardWidth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class FourCirclesAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container;
    //    RelativeLayout circle1, circle2, circle3, circle4;
    RelativeLayout.LayoutParams layoutParams;
    ArrayList<View> cardList;
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

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.allCardsImages(this);

        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        radius = (float) (screenWidth * 0.1);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCircles(6);
    }

    private void generateCircles(int totalCircle) {
        float x, y, rotation;
        float angle = (float) 360 / 13;

        View prevImage = null;

        for (int i = 0; i < totalCircle; i++) {
            RelativeLayout circle = new RelativeLayout(this);
            container.addView(circle, layoutParams);

            for (int j = 0; j < 13; j++) {
                float angle2 = angle * j;
                float radiance = (float) Math.toRadians(angle2);

                // getting cards
                View image = cardList.get(i % 2 == 0 ? j : j + 13);
                image.setId(j);

                // calculating position
                x = (float) (centerX - radius * Math.sin(radiance));
                y = (float) (centerY + radius * Math.cos(radiance));

                // setting image rotation and position
                rotation = j % 13 == 0 ? 0f : prevImage.getRotation() + angle;
                image.setX(x);
                image.setY(y);
                image.setRotation(rotation);

                prevImage = image;

                image.setVisibility(View.INVISIBLE);
                circle.addView(image);
            }
            new Handler().postDelayed(() -> animateCircle2(circle), 800L * (i + 1));
        }
    }

    private void animateCircle2(RelativeLayout layout) {
        for (int i = 0; i < 13; i++) {
            View image = layout.getChildAt(i);

            image.setVisibility(View.VISIBLE);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, image.getRotation());
            valueAnimator.setDuration(800L);
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
                        bouncingAnimation(layout);
                    }
                }
            });
        }
    }

    private void bouncingAnimation(RelativeLayout layout) {
        float translationX = (float) (Math.random() * screenWidth) - (float) screenWidth / 2;
        float rotation = (float) Math.random() * 360;

        if (translationX >= 0) {
            rotation = Math.abs(rotation);
        } else {
            rotation = rotation * -1;
        }

        ObjectAnimator tX = ObjectAnimator.ofFloat(layout, "translationX", translationX)
                .setDuration(1000L);
        ObjectAnimator tY = ObjectAnimator.ofFloat(layout, "translationY", (float) (layout.getY() + screenHeight * 0.2344))
                .setDuration(1000L);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(layout, "rotation", rotation)
                .setDuration(1000L);

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