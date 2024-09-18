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

public class CircleAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    int TOTAL_NUMBER_OF_CARDS = 24;
    int RADIUS;

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

        RADIUS = (int) (screenWidth * 0.3);

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        animateCard();
    }

    public void animateCard() {
        ArrayList<CardModel> cardModel = CardMethods.generateCards(TOTAL_NUMBER_OF_CARDS, 0);

        for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {

            int angle = 360 / TOTAL_NUMBER_OF_CARDS * i;
            double radians = Math.toRadians(angle);

            int x = (int) (centerX + RADIUS * Math.sin(radians));
            int y = (int) (centerY - RADIUS * Math.cos(radians));

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

            int finalI = i;
            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(30L * i)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator())
                    .withEndAction(() -> {
                        if (finalI == TOTAL_NUMBER_OF_CARDS - 1)
                            scaleAnimation();
                    })
                    .start();
        }
    }

    public void scaleAnimation() {
        for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {
            ImageView image = findViewById(i);

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