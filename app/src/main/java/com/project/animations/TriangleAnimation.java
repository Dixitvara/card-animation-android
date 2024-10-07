package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class TriangleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
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

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.generateSelectedCards(26, new int[]{0, 3});

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCards();
    }

    private void generateCards() {
        float x, y;
        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            x = (float) (screenWidth * 0.1);
            y = (float) (screenHeight * 0.6);

            container.addView(image, params);

            image.setX(centerX);
            image.setY(screenHeight);

            int finalI = i;
            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setDuration(300L)
                    .setStartDelay(100L * i)
                    .withEndAction(() -> animateTriangle(image, finalI))
                    .start();
        }
    }

    private void animateTriangle(View image, int i) {
        ObjectAnimator tx = ObjectAnimator.ofFloat(
                image,
                "translationX",
                image.getX(),
                (float) (screenWidth * 0.5),
                (float) (screenWidth * 0.85),
                image.getX()
        );
        ObjectAnimator ty = ObjectAnimator.ofFloat(
                image,
                "translationY",
                image.getY(),
                (float) (screenHeight * 0.3),
                (float) (screenHeight * 0.6),
                image.getY()
        );

        tx.setRepeatCount(2);
        ty.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2500L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(tx, ty);
        animatorSet.start();

        new Handler().postDelayed(() -> {
            if (i == 25) {
                container.animate()
                        .scaleX(0f)
                        .scaleY(0f)
                        .setDuration(500L)
                        .start();
            }
        }, 2500L);
    }
}