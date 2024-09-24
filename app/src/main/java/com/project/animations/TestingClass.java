package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    int TOTAL_NUMBER_OF_CARDS = 24;
    LinearLayout container;
    RelativeLayout childLayout, childLayout2, mainContainer;
    float centerX, centerY;
    Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        mainContainer = findViewById(R.id.container);

        container = new LinearLayout(this);
        childLayout = new RelativeLayout(this);
        childLayout2 = new RelativeLayout(this);

        LinearLayout.LayoutParams linearLayoutParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        container.setOrientation(LinearLayout.HORIZONTAL);
        mainContainer.addView(container, linearLayoutParam);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.weight = 1;
        container.addView(childLayout, layoutParams);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                0,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams2.weight = 1;
        container.addView(childLayout2, layoutParams2);

        childLayout.setBackgroundColor(Color.RED);
        childLayout2.setBackgroundColor(Color.BLUE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.generateCards(18, 1);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

//        createSwords();
        createCircle();

    }

    private void createCircle() {
        ArrayList<CardModel> cardModel = CardMethods.generateCards(TOTAL_NUMBER_OF_CARDS, 0);

        float radius = (int) (screenWidth * 0.3);
        for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {

            int angle = 360 / TOTAL_NUMBER_OF_CARDS * i;
            double radians = Math.toRadians(angle);

            int x = (int) (centerX + radius * Math.sin(radians));
            int y = (int) (centerY - radius * Math.cos(radians));

            CardModel card = cardModel.get(i);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            container.addView(image);

            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);
            image.setRotation(angle);
        }
    }

    private void createSwords() {

        float radius = (float) (screenWidth * 0.10);
        float angle = (float) 360 / 20;
        float x, y;
        float rotation;

        float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        float centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        ImageView prevImage = null;

        float distance = (float) (screenWidth * 0.00741);

        for (int i = 0; i < cardList.size(); i++) {

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.25 - (double) cardWidth / 2);
                y = (float) (screenHeight * 0.5 - (double) cardHeight / 2);
                rotation = 175f;
                image.setZ(2);
            } else if (i < 6) {
                x = prevImage.getX() - distance;
                y = prevImage.getY() - distance * 10;
                rotation = prevImage.getRotation();
            } else if (i == 6) {
                x = (float) (prevImage.getX() + distance * 3.5);
                y = (float) (prevImage.getY() - distance * 9.5);
                rotation = 45f;
            } else if (i == 7) {
                x = (float) (prevImage.getX() + distance * 3.6);
                y = prevImage.getY();
                rotation = prevImage.getRotation() * -1;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + distance * 3.6);
                y = (float) (prevImage.getY() + distance * 9.5);
                rotation = -175f;
            } else if (i < 14) {
                x = prevImage.getX() - distance;
                y = prevImage.getY() + distance * 10;
                rotation = prevImage.getRotation();
                image.setZ(2);
            } else if (i == 14) {
                x = prevImage.getX() + distance * 6;
                y = prevImage.getY() + distance * 6;
                image.setImageResource(R.drawable.spades1);
                image.setZ(1);
                rotation = 70f;
            } else if (i == 15) {
                x = prevImage.getX() - distance * 12;
                y = prevImage.getY();
                image.setImageResource(R.drawable.spades1);
                image.setZ(1);
                rotation = -70f;
            } else if (i == 16) {
                x = (float) (prevImage.getX() + distance * 5.5);
                y = prevImage.getY() + distance * 6;
                rotation = 0f;
            } else {
                x = prevImage.getX();
                y = prevImage.getY() + distance * 10;
                rotation = prevImage.getRotation();
                image.setImageResource(R.drawable.spades13);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            prevImage = image;
            childLayout.addView(image);
        }

        prevImage = null;
        for (int i = 0; i < cardList.size(); i++) {

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.25 - (double) cardWidth / 2);
                y = (float) (screenHeight * 0.5 - (double) cardHeight / 2);
                rotation = 175f;
                image.setZ(2);
            } else if (i < 6) {
                x = prevImage.getX() - distance;
                y = prevImage.getY() - distance * 10;
                rotation = prevImage.getRotation();
            } else if (i == 6) {
                x = (float) (prevImage.getX() + distance * 3.5);
                y = (float) (prevImage.getY() - distance * 9.5);
                rotation = 45f;
            } else if (i == 7) {
                x = (float) (prevImage.getX() + distance * 3.6);
                y = prevImage.getY();
                rotation = prevImage.getRotation() * -1;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + distance * 3.6);
                y = (float) (prevImage.getY() + distance * 9.5);
                rotation = -175f;
            } else if (i < 14) {
                x = prevImage.getX() - distance;
                y = prevImage.getY() + distance * 10;
                rotation = prevImage.getRotation();
                image.setZ(2);
            } else if (i == 14) {
                x = prevImage.getX() + distance * 6;
                y = prevImage.getY() + distance * 6;
                image.setImageResource(R.drawable.spades1);
                image.setZ(1);
                rotation = 70f;
            } else if (i == 15) {
                x = prevImage.getX() - distance * 12;
                y = prevImage.getY();
                image.setImageResource(R.drawable.spades1);
                image.setZ(1);
                rotation = -70f;
            } else if (i == 16) {
                x = (float) (prevImage.getX() + distance * 5.5);
                y = prevImage.getY() + distance * 6;
                rotation = 0f;
            } else {
                x = prevImage.getX();
                y = prevImage.getY() + distance * 10;
                rotation = prevImage.getRotation();
                image.setImageResource(R.drawable.spades13);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            prevImage = image;
            childLayout2.addView(image);
        }

        for (int i = 0; i < cardList.size(); i++) {
//            ImageView image = findViewById(i);

//            float x1 = image.getX();
//            float y1 = image.getY();

//            image.setX((float) screenWidth / 2);
//            image.setY(screenHeight);

        }
        animateCircle();
    }

    private void animateCircle() {
/*
        image.animate()
                .translationX(x1)
                .translationY(y1)
                .rotation(45)
                .setDuration(4000L)
                .setStartDelay(50L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x1);
                    image.setY(y1);
                    if (i == cardList.size())
                        scaleUpAnimation();
                })
                .start();
*/
        ObjectAnimator rotate = ObjectAnimator.ofFloat(childLayout, "rotation", 0f, 45f)
                .setDuration(700L);
        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(childLayout2, "rotation", 0f, -45f)
                .setDuration(700L);
        rotate.setRepeatMode(ValueAnimator.REVERSE);
        rotate.setRepeatCount(3);
        rotate2.setRepeatMode(ValueAnimator.REVERSE);
        rotate2.setRepeatCount(3);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate, rotate2);
        animatorSet.start();
    }

    private void scaleUpAnimation() {
        int x = screenWidth / 2;
        int y = screenHeight;

        for (int i = 0; i < childLayout.getChildCount(); i++) {
            View view = childLayout.getChildAt(i);

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