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

public class ClubAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int cardHeight, cardWidth;
    int TotalCards = 46;

    ArrayList<CardModel> cardList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        container = findViewById(R.id.container);
        resetBtn = findViewById(R.id.resetBtn);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        System.out.println("-} " + screenWidth);
        System.out.println("-} " + screenHeight);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        cardList = CardMethods.generateCards(TotalCards, 0);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCards();
    }

    private void generateCards() {
        float x, y;
        float rotation, firstCardRotation = -10f;
        int halfCircleCardCount = 13;

        ImageView prevImg = null;

        long duration = 300L;
        long startDelay = 30L;

        float radius = (float) (screenWidth * 0.055);
        float angle = (float) (210 / halfCircleCardCount);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);


        for (int i = 0; i < halfCircleCardCount; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(12 - i);

            container.addView(image);

            float angle2 = (float) (230 / halfCircleCardCount) * i;
            float radiance = (float) Math.toRadians(angle2);

            if (prevImg == null) {
                x = (float) screenWidth / 4;
                y = (float) (screenHeight * 0.4);
                rotation = firstCardRotation;
            } else {
                x = (float) (prevImg.getX() - radius * Math.cos(radiance));
                y = (float) (prevImg.getY() + radius * Math.sin(radiance));
                rotation = prevImg.getRotation() - angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        prevImg = findViewById(12);

        rotation = -90f;

        for (int i = 13; i < 26; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            container.addView(image);

            int index = i - 13;

            float angle2 = (float) (210 / halfCircleCardCount) * index;
            angle2 -= 15;
            float radiance = (float) Math.toRadians(angle2);

            if (i == 13) {
                x = prevImg.getX();
                y = prevImg.getY();
            } else {
                x = (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 26; i < 39; i++) {
            int index = i - 26;

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            container.addView(image);

            float angle2 = (float) (230 / halfCircleCardCount) * index;
            float radiance = (float) Math.toRadians(angle2);

            if (i == 26) {
                x = prevImg.getX();
                y = prevImg.getY();
                rotation = 10;
            } else {
                x = (float) (prevImg.getX() + radius * Math.cos(radiance));
                y = (float) (prevImg.getY() + radius * Math.sin(radiance));
                rotation = prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 39; i < 46; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            container.addView(image);

            if (i == 39) {
                x = (float) (prevImg.getX() - prevImg.getX() * 0.14);
                y = prevImg.getY();
                rotation = 0;
            } else if (i == 40) {
                x = prevImg.getX();
                y = (float) (prevImg.getY() + prevImg.getX() * 0.1);
                rotation = prevImg.getRotation();
            } else if (i == 41) {
                x = (float) (prevImg.getX() - prevImg.getX() * 0.1);
                y = (float) (prevImg.getY() + prevImg.getY() * 0.06);
                rotation = 45f;
            } else if (i == 42) {
                x = (float) (prevImg.getX() + prevImg.getX() * 0.25);
                y = prevImg.getY();
                rotation = -45f;
            } else if (i == 43) {
                x = (float) (prevImg.getX() + prevImg.getX() * 0.025);
                y = (float) (prevImg.getY() + prevImg.getY() * 0.04);
                rotation = 90f;
            } else if (i == 44) {
                x = (float) (prevImg.getX() - prevImg.getX() * 0.15);
                y = prevImg.getY();
                rotation = 90f;
            } else {
                x = (float) (prevImg.getX() - prevImg.getX() * 0.12);
                y = prevImg.getY();
                rotation = 90f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 0; i < TotalCards; i++) {
            ImageView image = findViewById(i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);

            animateHeart(image, imageX, imageY, duration, startDelay, i);
        }

    }

    private void animateHeart(ImageView image, float x, float y, long duration, long startDelay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .setStartDelay(startDelay * i)
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == 45)
                       scaleAnimation();
                }).start();
    }

    private void scaleAnimation() {
        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i - 1);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f)
                    .setDuration(400);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f)
                    .setDuration(400);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(30L * i);

            animatorSet.start();
        }
    }

}
