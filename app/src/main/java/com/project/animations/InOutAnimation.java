package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class InOutAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;
    final int TOTAL_CARDS = 52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        cardList = CardMethods.allCards();
        params = new RelativeLayout.LayoutParams(90, 110);

        designCircle();

    }

    private void designCircle() {
        int circleSize = 26;
        float x, y;
        float radius = (float) (screenWidth * 0.35);
        float rotation;
        ImageView prevImage = null;
        float angle = (float) 360 / circleSize;

        //  big circle
        for (int i = 0; i < circleSize; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2 = (float) 360 / circleSize * i;
            double radians = Math.toRadians(angle2);

            x = (float) (centerX + radius * Math.sin(radians));
            y = (float) (centerY - radius * Math.cos(radians));
            if (prevImage == null) {
                rotation = 0f;
            } else {
                rotation = prevImage.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // small circle
        float radius2 = (float) (screenWidth * 0.12);
        prevImage = null;
        for (int i = circleSize; i < TOTAL_CARDS; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2 = (float) 360 / circleSize * (i - 26);
            double radians = Math.toRadians(angle2);

            x = (float) (centerX + radius2 * Math.sin(radians));
            y = (float) (centerY - radius2 * Math.cos(radians));
            if (prevImage == null) {
                rotation = 0f;
            } else {
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // calling the animation
        for (int i = 0; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX(centerX);
            image.setY(centerY);
            image.setScaleX(0);
            image.setScaleY(0);

            animateFromMiddle(image, x1, y1, i);
        }
    }

    private void animateFromMiddle(View image, float x, float y, int i) {
        long duration = 400L;
        long delay = 80L;

        ObjectAnimator tX = ObjectAnimator.ofFloat(image, "translationX", x).setDuration(duration);
        ObjectAnimator tY = ObjectAnimator.ofFloat(image, "translationY", y).setDuration(duration);
        ObjectAnimator sX = ObjectAnimator.ofFloat(image, "scaleX", 1f).setDuration(duration);
        ObjectAnimator sY = ObjectAnimator.ofFloat(image, "scaleY", 1f).setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tX, tY, sX, sY);
        animatorSet.setStartDelay(delay * i);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();

        new Handler().postDelayed(this::inOutAnimation, 4600L);
    }

    private void inOutAnimation() {
        float angle = (float) 360 / 26;
        for (int i = 0; i < 26; i++) {
            ImageView image = findViewById(i);

            ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(
                            image,
                            "rotation",
                            image.getRotation(),
                            image.getRotation() + 360
                    )
                    .setDuration(2000);

            ValueAnimator animator = ValueAnimator.ofFloat(0.35f, 0.12f);
            animator.setDuration(2000);
            animator.setInterpolator(new LinearInterpolator());

            int index = i;

            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float modifiedRadius = animatedValue * screenWidth;

                double radiance = Math.toRadians(angle * index);

                float x = (float) (centerX + modifiedRadius * Math.sin(radiance));
                float y = (float) (centerY - modifiedRadius * Math.cos(radiance));

                image.setX(x);
                image.setY(y);
            });

            animator.setRepeatMode(ValueAnimator.REVERSE);
            rotationAnimation.setRepeatMode(ValueAnimator.REVERSE);

            animator.setRepeatCount(1);
            rotationAnimation.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, rotationAnimation);
            animatorSet.start();
        }

        for (int i = 0; i < 26; i++) {
            ImageView image = findViewById(i + 26);

            ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(
                            image,
                            "rotation",
                            image.getRotation(),
                            image.getRotation() - 360
                    )
                    .setDuration(2000);

            ValueAnimator animator2 = ValueAnimator.ofFloat(0.12f, 0.35f);
            animator2.setDuration(2000);
            animator2.setInterpolator(new LinearInterpolator());

            int index2 = i;
            animator2.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float modifiedRadius = animatedValue * screenWidth;

                double radiance = Math.toRadians(angle * index2);

                float x = (float) (centerX + modifiedRadius * Math.sin(radiance));
                float y = (float) (centerY - modifiedRadius * Math.cos(radiance));

                image.setX(x);
                image.setY(y);
            });
            animator2.setRepeatMode(ValueAnimator.REVERSE);
            rotationAnimation.setRepeatMode(ValueAnimator.REVERSE);

            animator2.setRepeatCount(1);
            rotationAnimation.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator2, rotationAnimation);
            animatorSet.start();

        }
        new Handler().postDelayed(this::outAnimation, 4500L);
    }

    public void outAnimation() {
        for (int i = 0; i < container.getChildCount(); i++) {
            ImageView image = findViewById(i);

            float x = i < 26 ? -cardWidth : screenWidth;
            float y = i < 26 ? -cardHeight : screenHeight;
            long delay = i < 26 ? 30L * i : 30L * (i - 26);

            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setDuration(600L)
                    .setStartDelay(delay)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
        }
    }
}