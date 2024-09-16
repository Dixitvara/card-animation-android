package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class SmileAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;

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

        cardList = CardMethods.generateCards(49, 0);
        params = new RelativeLayout.LayoutParams(90, 110);

        generateCards();
    }

    private void generateCards() {
        int circleSize = 39;
        float x, y;
        float rotation;
        float radius = (float) (screenWidth * 0.06);
        float angle = (float) 360 / circleSize;

        ImageView prevImg = null;

        //  circle
        for (int i = 0; i < circleSize; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2 = (float) 360 / circleSize * i;
            double radians = Math.toRadians(angle2);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.83);
                y = centerY;
                rotation = 0f;
            } else {
                x = (float) (prevImg.getX() - radius * Math.sin(radians));
                y = (float) (prevImg.getY() + radius * Math.cos(radians));
                rotation = prevImg.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
            container.addView(image);
        }

        // smile lips
        for (int i = 5; i < 15; i++) {
            float radius2 = (float) (screenWidth * 0.045);
            float angle2 = angle * (i + 1);
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(34 + i);

            if (i == 5) {
                x = (float) (screenWidth * 0.6);
                y = (float) (screenHeight * 0.55);
            } else {
                x = (float) (prevImg.getX() - radius2 * Math.sin(radiance));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance));
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(angle2);
            prevImg = image;

            // eyes
            if (i == 13) {
                image.setX((float) (screenWidth * 0.3));
                image.setY((float) (screenHeight * 0.35));
                image.setImageResource(R.drawable.clubs1);
                image.setRotation(-10f);
            }
            if (i == 14) {
                image.setX((float) (screenWidth * 0.6));
                image.setY((float) (screenHeight * 0.35));
                image.setImageResource(R.drawable.clubs1);
                image.setRotation(10f);
            }
            container.addView(image);
        }

        // calling the animation
        for (int i = 0; i < 49; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            if (i < 47) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
                image.setY(screenHeight);
            } else {
                image.setX(i == 47 ? -100f : screenWidth);
                image.setY((float) (screenHeight * 0.35));
            }
            animateCard(image, x1, y1, i + 1);
        }
    }

    private void animateCard(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setStartDelay(30L * i)
                .setDuration(400L)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == 49) {
                        scaleUpAnimation();
                    }
                })
                .start();
    }

    private void scaleUpAnimation() {
        for (int i = 0; i < container.getChildCount(); i++) {

            View view = container.getChildAt(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.3f);

            scaleX.setDuration(300L);
            scaleY.setDuration(300L);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            scaleX.setInterpolator(new DecelerateInterpolator());
            scaleY.setInterpolator(new DecelerateInterpolator());

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(20L * (i));
            animatorSet.start();
        }
    }
}