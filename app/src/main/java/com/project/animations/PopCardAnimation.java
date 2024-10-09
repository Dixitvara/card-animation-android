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
import com.project.animations.utils.MyAnim;

import java.util.ArrayList;
import java.util.Random;

public class PopCardAnimation extends AppCompatActivity {

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
        addCards();
    }

    private void addCards() {
        float x, y;
        float firstCardPosition = (float) (screenWidth * 0.2);

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));

            if (i < 13) {
                x = firstCardPosition;
                image.setId(12 - i);
            } else if (i < 26) {
                x = firstCardPosition + cardWidth;
                image.setId(38 - i);
            } else if (i < 39) {
                x = firstCardPosition + cardWidth * 2;
                image.setId(64 - i);
            } else {
                x = firstCardPosition + cardWidth * 3;
                image.setId(90 - i);
            }
            y = (float) (screenHeight * 0.3);

            image.setX(x);
            image.setY(y);

            container.addView(image, params);
        }
        bounceAnimate();
    }

    private void bounceAnimate() {
        Random random = new Random();
        long delay, delayValue = 250L;
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
        new Handler().postDelayed(() -> {
            container.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .setDuration(500L)
                    .start();
//            MyAnim.translateYTo(container, -screenHeight, 1500L);
        }, 5500L);
    }
}