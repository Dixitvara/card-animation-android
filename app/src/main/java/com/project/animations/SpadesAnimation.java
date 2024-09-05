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


public class SpadesAnimation extends AppCompatActivity {

    int screenWidth, screenHeight, cardWidth, cardHeight;
    RelativeLayout container;
    Button resetBtn;
    SpadesAnimation generateCards;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        generateCards = new SpadesAnimation();
        cardList = CardMethods.generateCards(45, 1);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardParam = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParam[0];
        cardHeight = cardParam[1];

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        System.out.println("==} " + cardList.size());

        animation();
    }

    private void animation() {
        float Radius = (float) (screenWidth * 0.060);
        float radius = (int) (screenWidth * 0.075);
        float rotation;
        float firstCardPositionX = (float) screenWidth / 2 - (float) cardWidth / 2;
        float firstCardPositionY = (float) ((float) screenHeight * 0.5);

        ImageView prevImg = null;

        float x, y;
        int duration = 300;
        long startDelay = 30L;

        // left half
        for (int i = 1; i <= 20; i++) {
            CardModel card = cardList.get(i - 1);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i - 1);

            container.addView(image);

            float angle = (float) (180 / 10) * i - 1;
            double radians = Math.toRadians(angle);

            float angle2 = (float) (80 / 10) * i - 1;
            double radians2 = Math.toRadians(angle2);

            if (i == 1) {
                x = firstCardPositionX;
                y = firstCardPositionY;
                rotation = -45;
                image.setVisibility(View.INVISIBLE);
            } else {
                rotation = i == 2 ? -45 : prevImg.getRotation();
                if (i < 10) {
                    x = (float) (prevImg.getX() - Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() + Radius * Math.cos(radians));
                    rotation += 15;
                } else {
                    x = (float) (prevImg.getX() - radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation = i == 20 ? 0 : (prevImg.getRotation() + 8);
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        // right half
        for (int i = 1; i <= 19; i++) {
            CardModel card = cardList.get(19 + i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(39 - i);

            container.addView(image);

            float angle = (float) (180 / 10) * i - 1;
            double radians = Math.toRadians(angle);

            float angle2 = (float) (80 / 10) * i - 1;
            double radians2 = Math.toRadians(angle2);

            if (i == 1) {
                x = firstCardPositionX;
                y = firstCardPositionY;
                rotation = 45;
                image.setVisibility(View.INVISIBLE);
            } else {
                rotation = i == 2 ? 45 : prevImg.getRotation();
                if (i < 10) {
                    x = (float) (prevImg.getX() + Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() + Radius * Math.cos(radians));
                    rotation -= 15;
                } else {
                    x = (float) (prevImg.getX() + radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation = prevImg.getRotation() - 8;
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        for (int i = 1; i <= 6; i++) {
            CardModel card = cardList.get(37 + i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(38 + i);

            container.addView(image);

            if (i == 1) {
                x = firstCardPositionX;
                y = (float) (firstCardPositionY + screenHeight * 0.085);
                rotation = 0;
            } else if (i == 2) {
                x = (float) (prevImg.getX() - prevImg.getX() * 0.1);
                y = (float) (prevImg.getY() + prevImg.getY() * 0.08);
                rotation = 45f;
            } else if (i == 3) {
                x = (float) (prevImg.getX() + prevImg.getX() * 0.25);
                y = prevImg.getY();
                rotation = -45f;
            } else if (i == 4) {
                x = (float) (prevImg.getX() + prevImg.getX() * 0.025);
                y = (float) (prevImg.getY() + prevImg.getY() * 0.04);
                rotation = 90f;
            } else if (i == 5) {
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

        for (int i = 1; i <= 45; i++) {
            ImageView image = findViewById(i - 1);
            System.out.println("-} " + i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);
            animateHeart(image, imageX, imageY, duration, startDelay, i - 1);
        }
    }

    private void animateHeart(ImageView image, float imageX, float imageY, int duration,
                              long startDelay, int i) {
        image.animate()
                .translationX(imageX)
                .translationY(imageY)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .setStartDelay(startDelay * i)
                .withEndAction(() -> {
                    image.setX(imageX);
                    image.setY(imageY);
                    if(i == 44){
                        scaleAnimation();
                    }
                })
                .start();
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