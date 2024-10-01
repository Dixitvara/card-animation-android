package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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

public class SpiderAnimation extends AppCompatActivity {

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

        cardList = CardMethods.generateCards(13, 2);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        generateCards();
    }

    @SuppressLint("ResourceType")
    private void generateCards() {
        float x, y;
        ImageView prevImage = null;

        // spider body
        for (int i = 0; i < 14; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades_1);
            image.setTag("body");

            if (prevImage == null) {
                x = (float) (screenWidth * 0.28);
                y = (float) (screenHeight * 0.14);
                image.setId(i);
            } else if (i < 5) {
                x = prevImage.getX() + cardWidth;
                y = prevImage.getY();
                if (i == 4)
                    image.setId(91);
            } else if (i == 5) {
                x = prevImage.getX() - (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
                image.setId(78);
            } else if (i < 9) {
                x = prevImage.getX() - cardWidth;
                y = prevImage.getY();
                if (i == 8)
                    image.setId(13);
            } else if (i == 9) {
                image.setImageResource(R.drawable.spades_1);
                x = prevImage.getX() + (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
                image.setId(26);
            } else if (i < 12) {
                x = prevImage.getX() + cardWidth;
                y = prevImage.getY();
                if (i == 11)
                    image.setId(65);
            } else if (i == 12) {
                x = prevImage.getX() - (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
                image.setId(52);
            } else {
                x = prevImage.getX() - cardWidth;
                y = prevImage.getY();
                image.setId(39);
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
        }

        // left legs
        // first leg
        prevImage = null;
        float radius = (float) (screenWidth * 0.04);
        float angle = (float) 180 / 13;
        float horizontalRadius = (float) (screenWidth * 0.03);
        float rotation;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.28);
                y = (float) (screenHeight * 0.11);
                rotation = -20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // second leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.024);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 13);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.32);
                y = (float) (screenHeight * 0.17);
                rotation = -20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }


        // third leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.018);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 26);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.35);
                y = (float) (screenHeight * 0.23);
                rotation = -20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // fourth leg
        radius = (float) (screenWidth * 0.032);
        horizontalRadius = (float) (screenWidth * 0.014);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 39);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.40);
                y = (float) (screenHeight * 0.29);
                rotation = -20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 4f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // right legs
        // first leg
        prevImage = null;
        radius = (float) (screenWidth * 0.04);
        horizontalRadius = (float) (screenWidth * 0.03);
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 91);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.64);
                y = (float) (screenHeight * 0.11);
                rotation = 20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // second leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.024);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 78);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.60);
                y = (float) (screenHeight * 0.17);
                rotation = 20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }


        // third leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.018);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 65);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.57);
                y = (float) (screenHeight * 0.23);
                rotation = 20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // fourth leg
        radius = (float) (screenWidth * 0.032);
        horizontalRadius = (float) (screenWidth * 0.014);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 52);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.52);
                y = (float) (screenHeight * 0.29);
                rotation = 20f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 4f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        for (int i = 14; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);
            image.setVisibility(View.INVISIBLE);
        }
        inAnimation();
    }

    private void inAnimation() {
        for (int i = 0; i < 14; i++) {
            View image = container.getChildAt(i);

            float x = image.getX();
            float y = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight / 2);

            animateBody(image, x, y);
        }
    }

    private void animateBody(View image, float x, float y) {
        ObjectAnimator tX = ObjectAnimator.ofFloat(image, "translationX", x);
        ObjectAnimator tY = ObjectAnimator.ofFloat(image, "translationY", y);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000L);
        animatorSet.playTogether(tX, tY);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                addLegs();
            }
        });
    }

    private void addLegs() {
        long delayValue = 50L, delay;
        for (int i = 14; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);

            if (i < 26)
                delay = delayValue * (i - 13);
            else if (i < 38)
                delay = delayValue * (i - 26);
            else if (i < 50)
                delay = delayValue * (i - 38);
            else if (i < 62)
                delay = delayValue * (i - 50);
            else if (i < 74)
                delay = delayValue * (i - 62);
            else if (i < 86)
                delay = delayValue * (i - 74);
            else if (i < 98)
                delay = delayValue * (i - 86);
            else
                delay = delayValue * (i - 98);

            int finalI = i;
            image.animate()
                    .translationX(image.getX())
                    .setDuration(200L)
                    .setInterpolator(new DecelerateInterpolator())
                    .setStartDelay(delay)
                    .withEndAction(() -> {
                        image.setVisibility(View.VISIBLE);
                        if (finalI == container.getChildCount() - 1) {
                            animateSpider();
                        }
                    })
                    .start();
        }
    }

    private void animateSpider() {
        long delay;
        for (int i = 103; i > 0; i--) {
            if (i % 13 == 0)
                continue;
            if (i < 13 || i > 25 && i < 39 || i > 51 && i < 65 || i > 77 && i < 91)
                delay = 0L;
            else
                delay = 300L;

            View image = findViewById(i);
            View nextImg = findViewById(i - 1);

            ObjectAnimator translationX = ObjectAnimator.ofFloat(image, "translationX", nextImg.getX());
            ObjectAnimator translationY = ObjectAnimator.ofFloat(image, "translationY", nextImg.getY());
            ObjectAnimator rotation = ObjectAnimator.ofFloat(image, "rotation", nextImg.getRotation());
            ObjectAnimator moveSpider = ObjectAnimator.ofFloat(container, "translationY", screenHeight)
                    .setDuration(5000L);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(250L);
            animatorSet.setStartDelay(delay);
            animatorSet.setInterpolator(new LinearInterpolator());

            translationX.setRepeatCount(ValueAnimator.INFINITE);
            translationX.setRepeatMode(ValueAnimator.REVERSE);

            translationY.setRepeatCount(ValueAnimator.INFINITE);
            translationY.setRepeatMode(ValueAnimator.REVERSE);

            rotation.setRepeatCount(ValueAnimator.INFINITE);
            rotation.setRepeatMode(ValueAnimator.REVERSE);

            animatorSet.playTogether(translationX, translationY, rotation);

            AnimatorSet animatorSet1 = new AnimatorSet();
            animatorSet1.playTogether(animatorSet, moveSpider);

            animatorSet1.start();
        }
    }

}