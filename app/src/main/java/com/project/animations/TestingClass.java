package com.project.animations;

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

public class TestingClass extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    int TOTAL_NUMBER_OF_CARDS = 7;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout mainContainer;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        mainContainer = findViewById(R.id.container);

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

        createCircle();
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

            mainContainer.addView(image);
            prevImage = image;
        }

        for (int i = 0; i < mainContainer.getChildCount(); i++) {
            View image = mainContainer.getChildAt(i);
            animateLegs(image, angle);
        }
    }

    private void animateLegs(View image, float angle) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                image,
                "rotation",
                image.getRotation() - angle
        );
        animator.setDuration(200L);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    private void animateCircle(View image, float x, float y, int i) {
        image.animate()
                .translationX(image.getX())
                .setStartDelay(30L * i)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}