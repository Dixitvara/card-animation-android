package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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

        cardList = CardMethods.generateCards(18, 0);

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

        ImageView prevImage = null;

        float distance = (float) (screenWidth * 0.00741);

        for (int i = 0; i < cardList.size(); i++) {

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.5 - (double) cardWidth / 2);
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

            image.setPivotX((float) cardWidth / 2);
            image.setPivotY((centerY- y) - (float) cardHeight / 2);

            prevImage = image;
            container.addView(image);

/*
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i == 0){
                x = centerX;
                y = centerY;
            }
            else{
                x = prevImage.getX();
                y = prevImage.getY() - cardHeight;
            }

            image.setPivotX((float) cardWidth / 2);
            image.setPivotY(centerY - y);
            image.setX(x);
            image.setY(y);
            image.setRotation(-45f);
            prevImage = image;

            container.addView(image);
*/
        }

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i);

            float x1 = image.getX();
            float y1 = image.getY();

//            image.setX((float) screenWidth / 2);
//            image.setY(screenHeight);

            animateCircle(image, x1, y1, i + 1);
        }
    }

    private void animateCircle(ImageView image, float x1, float y1, int i) {
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
        ObjectAnimator rotate = ObjectAnimator.ofFloat(image, "rotation", 0f, 45f)
                .setDuration(700L);
        rotate.setRepeatMode(ValueAnimator.REVERSE);
        rotate.setRepeatCount(3);
        rotate.setInterpolator(new AccelerateInterpolator());
        rotate.start();
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