package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
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

        cardList = CardMethods.generateCards(20, 0);

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
        float angle = (float) 360 / 20;
        float x, y;
        float rotation;

        float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        float centerY = (float) screenHeight / 2 - (float) cardHeight / 2;
        ImageView prevImg = null;

        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            float angle2;
            float radiance;

            angle2 = angle * (i + 1);
            radiance = (float) Math.toRadians(angle2);
            if (prevImg == null) {
                x = (float) (screenWidth * 0.35 - (double) cardWidth / 2);
                y = (float) (screenHeight * 0.4);
                rotation = -90;
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

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i);

            float x1 = image.getX();
            float y1 = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);

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
                    if (i == cardList.size())
                        scaleUpAnimation();
                })
                .start();
    }

    private void scaleUpAnimation() {
        int x = screenWidth / 2;
        int y = screenHeight;

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