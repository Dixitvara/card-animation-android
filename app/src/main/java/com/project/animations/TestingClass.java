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
import java.util.Random;

public class TestingClass extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    int TOTAL_NUMBER_OF_CARDS = 7;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

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

        int[] cardDimension = CardDimension.bigCardsParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.allCards();
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        bounceCard();
    }

    private void slidingDeck() {
        for (int i = 0; i < 13; i++) {
            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);
            container.addView(image, params);

            image.setX(centerX);
            image.setY(centerY);

            image.animate()
                    .translationX(screenWidth)
                    .setDuration(800L)
                    .setStartDelay(50L * i)
                    .start();
        }
    }

    private void spiderLeg() {
        ImageView prevImage = null;
        float radius = (float) (screenWidth * 0.04);
        float angle = (float) 180 / 13;
        float horizontalRadius = (float) (screenWidth * 0.03);
        float x, y, rotation;

        for (int i = 1; i < 13; i++) {
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
                rotation = 160f;
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
        animateLegs();
    }

    private void animateLegs() {
        for (int i = 12; i > 0; i--) {
            View image = findViewById(i);
            View prevImage = findViewById(i - 1);

            animateLegs(image, prevImage);
        }
    }

    private void smileEye() {
        float radius = (float) (screenWidth * 0.01);
        cardList = CardMethods.generateCards(18, 1);

        ImageView prevImg = null;
        CardModel card;
        float x, y, rotation;
        float angle = (float) 360 / 6;

        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            card = cardList.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImg == null) {
                x = (float) ((float) (screenWidth * 0.5));
                y = (float) ((float) (screenHeight * 0.5));
                rotation = 60f;
            } else {
                x = prevImg.getX();
                y = prevImg.getY();
                rotation = prevImg.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            prevImg = image;
            container.addView(image);

            ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(image, "rotation", 45)
                    .setDuration(800L);
//            rotateAnimation.start();
        }
    }

    private void createCircle() {
        ArrayList<CardModel> cardModel = CardMethods.generateCards(TOTAL_NUMBER_OF_CARDS, 0);
        ImageView prevImage = null;

        float radius = (float) (screenWidth * 0.07);
        float rotation;
        float angle = (float) 90 / TOTAL_NUMBER_OF_CARDS;
        float x, y;
        for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {

            float angle2 = angle * i;
            angle2 += angle * 6;
            float radians = (float) Math.toRadians(angle2);

            CardModel card = cardModel.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i == 0) {
                x = (float) (centerX + radius * Math.sin(radians));
                y = (float) (centerY - radius * Math.cos(radians));
                rotation = -90f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radians));
                y = (float) (prevImage.getY() - radius * Math.cos(radians));
                rotation = prevImage.getRotation() + 13;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        for (int i = 0; i < container.getChildCount(); i++) {
            View image = container.getChildAt(i);
            if (i == container.getChildCount() - 1) {
                break;
            }
            View nextImg = container.getChildAt(i + 1);

/*
            image.animate()
                    .translationX(nextImg.getX())
                    .translationY(nextImg.getY())
                    .rotation(nextImg.getRotation())
                    .setDuration(1000L)
                    .setInterpolator(new DecelerateInterpolator())
                    .start();
*/

            animateLegs(image, nextImg);
        }
    }

    private void createTrophyShape() {
        ImageView prevImage = null;
        float x, y, rotation;
        float angle = (float) 70 / 5;
        float radius = (float) (screenHeight * 0.05);

        for (int i = 0; i < 7; i++) {
            float angle2 = angle * i;
            angle2 -= 40f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

/*
            if (prevImage == null) {
                x = (float) (screenWidth * 0.48 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.6 - radius * Math.cos(radiance));
                rotation = -40f;
            } else {
                if (i < 5) {
                    x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                    y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                    rotation = prevImage.getRotation() + 10;
                } else {
                    x = prevImage.getX() + 30f;
                    y = prevImage.getY() - 40f;
                    rotation = 70f;
                    if (i == 6) {
                        x = prevImage.getX() + cardWidth;
                        y = prevImage.getY() - 20f;
                        rotation = prevImage.getRotation() + 20f;
                    }
                }
            }
*/

            if (prevImage == null) {
                x = (float) (screenWidth * 0.4 + radius * Math.sin(radiance));
                y = (float) (screenHeight * 0.4 - radius * Math.cos(radiance));
                rotation = 90f;
            } else {
                if (i == 1) {
                    x = prevImage.getX() - cardWidth;
                    y = prevImage.getY() + 20f;
                    rotation = prevImage.getRotation() - 20;
                } else if (i == 2) {
                    x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                    y = (float) (prevImage.getY() + radius * Math.cos(radiance));
                    rotation = 0f;
                } else {
                    x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                    y = (float) (prevImage.getY() + radius * Math.cos(radiance));
                    rotation = prevImage.getRotation() - 10;
                }
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;

            container.addView(image, params);
        }
    }

    private void animateLegs(View image, View nextImg) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(image, "translationX", nextImg.getX());
        ObjectAnimator translationY = ObjectAnimator.ofFloat(image, "translationY", nextImg.getY());
        ObjectAnimator rotation = ObjectAnimator.ofFloat(image, "rotation", nextImg.getRotation());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500L);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationX.setRepeatMode(ValueAnimator.REVERSE);

        translationY.setRepeatCount(ValueAnimator.INFINITE);
        translationY.setRepeatMode(ValueAnimator.REVERSE);

        rotation.setRepeatCount(ValueAnimator.INFINITE);
        rotation.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet.playTogether(translationX, translationY, rotation);

        animatorSet.start();
    }

    private void animateCircle(View image, float x, float y, int i) {
        image.animate()
                .translationX(image.getX())
                .setStartDelay(30L * i)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void addCards() {
        float x, y;
        float firstCardPosition = (float) (screenWidth * 0.5) - (float) cardWidth / 2;

        for (int i = 0; i < 13; i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i < 13) {
                x = firstCardPosition;
            } else if (i < 26) {
                x = firstCardPosition + cardWidth;
            } else if (i < 39) {
                x = firstCardPosition + cardWidth * 2;
            } else {
                x = firstCardPosition + cardWidth * 3;
            }
            y = (float) (screenHeight * 0.3);

            image.setX(x);
            image.setY(y);

            container.addView(image, params);
        }
    }

    private void bounceCard() {
        for (int i = 0; i < 52; i++) {
            View image = new ImageView(this);
            image.setBackgroundResource(cardList.get(i).getResourceId(this));
            image.setId(i);

            image.setX(centerX);
            image.setY(centerY);

            container.addView(image, params);
        }
//        bounceAnimate();
    }

    private void bounceAnimate() {
        Random random = new Random();
        long delay, delayValue = 100L;
        for (int i = 0; i < 52; i++) {
            View image = findViewById(i);

            if (i < 13)
                delay = delayValue * i;
            else if (i < 26)
                delay = delayValue * (i - 13);
            else if (i < 39)
                delay = delayValue * (i - 26);
            else
                delay = delayValue * (i - 39);

            ObjectAnimator ty = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    image.getY(),
                    screenHeight - cardHeight
            ).setDuration(2000);
            ObjectAnimator ty2 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    image.getY() + 250f
            ).setDuration(2000);
            ObjectAnimator ty3 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    screenHeight - cardHeight
            ).setDuration(2000);
            ObjectAnimator ty4 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    image.getY() + 500f
            ).setDuration(2000);
            ObjectAnimator ty5 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    screenHeight - cardHeight
            ).setDuration(2000);
            ObjectAnimator ty8 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    image.getY() + 650f
            ).setDuration(2000);
            ObjectAnimator ty9 = ObjectAnimator.ofFloat(
                    image,
                    "translationY",
                    screenHeight - cardHeight
            ).setDuration(2000);

            ObjectAnimator tx = ObjectAnimator.ofFloat(
                    image,
                    "translationX",
                    image.getX(),
                    random.nextFloat() * screenWidth
            ).setDuration(2000);
            tx.setStartDelay(delay);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(ty, ty2, ty3, ty4, ty5, ty8, ty9);
            animatorSet.setInterpolator(new LinearInterpolator());
            animatorSet.setStartDelay(delay);
            animatorSet.setDuration(350L);

            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(tx, animatorSet);
            animatorSet2.start();
        }
    }
}