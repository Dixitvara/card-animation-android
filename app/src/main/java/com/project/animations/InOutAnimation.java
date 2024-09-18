package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX(centerX);
            image.setY(centerY);
            image.setScaleX(0);
            image.setScaleY(0);

            animateFromMiddle(image, x1, y1, i);
        }
    }

    private void animateFromMiddle(ImageView image, float x, float y, int i) {
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

        if (i == TOTAL_CARDS - 1)
            inOutAnimation();
    }

    private void inOutAnimation() {
        float angle = (float) 360 / 52;
        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);

            ValueAnimator animator = ValueAnimator.ofFloat(0.12f, 0.35f);
            animator.setDuration(3000L);

            int finalI = i;
            float angle2 = angle * i;
            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                float modifiedRadius = (float) (0.12 + animatedValue);

                double radiance = Math.toRadians(angle2);

                float x = (float) (centerX + modifiedRadius * Math.sin(radiance));
                float y = (float) (centerY - modifiedRadius * Math.cos(radiance));

                image.setX(x);
                image.setY(y);
//                image.setRotation(angle * index + );
            });

            animator.start();
        }
    }
}