package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

public class ScreenRectangleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

    float radius;

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

        generateShape();
    }

    private void generateShape() {
        float x, y;
        long delay;
        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i < 13) {
                x = 0;
                y = (float) (screenHeight * 0.2);
                delay = 200L * i;
            } else if (i < 26) {
                x = screenWidth - cardWidth;
                y = (float) (screenHeight * 0.2);
                delay = 200L * (i - 13);
            } else if (i < 39) {
                x = screenWidth - cardWidth;
                y = (float) (screenHeight * 0.8);
                delay = 200L * (i - 26);
            } else {
                x = 0;
                y = (float) (screenHeight * 0.8);
                delay = 200L * (i - 39);
            }

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);

            addCardAnimation(image, x, y, delay, i);
            container.addView(image, params);
        }
    }

    private void addCardAnimation(View image, float x, float y, long delay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(600L)
                .setStartDelay(delay)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    moveCards(image, i);
                })
                .start();
    }

    private void moveCards(View image, int i) {
        ObjectAnimator tx = ObjectAnimator.ofFloat(
                image,
                "translationX",
                screenWidth - cardWidth
        );
        ObjectAnimator ty = ObjectAnimator.ofFloat(
                image,
                "translationY",
                (float) (screenHeight * 0.8)
        );
        ObjectAnimator reverseTx = ObjectAnimator.ofFloat(
                image,
                "translationX",
                0
        );
        ObjectAnimator reverseTy = ObjectAnimator.ofFloat(
                image,
                "translationY",
                (float) (screenHeight * 0.2)
        );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(1500L);

        if (i < 13) {
            animatorSet.playSequentially(tx, ty, reverseTx);
        } else if (i < 26) {
            animatorSet.playSequentially(ty, reverseTx, reverseTy);
        } else if (i < 39) {
            animatorSet.playSequentially(reverseTx, reverseTy, tx);
        } else {
            animatorSet.playSequentially(reverseTy, tx, ty);
        }
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (i == 40) {
                    container.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .setDuration(600L)
                            .start();
                }
            }
        });
    }
}