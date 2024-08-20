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

import com.project.animations.utils.CardDimension;

import java.util.LinkedList;

public class CircleAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    LinkedList<ImageView> cardList;
    int centerX, centerY;
    int cardHeight, cardWidth;

    final int TOTAL_NUMBER_OF_CARDS = 23;
    int RADIUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cardList = new LinkedList<>();

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

        RADIUS = (int) (screenWidth * 0.45);

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        // generating cards
        generateCards();

        // setting position
        positionCardsInCircle();

    }

    private void generateCards() {
        for (int i = 1; i < TOTAL_NUMBER_OF_CARDS; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            if (i <= 13)
                resourceId = getResources().getIdentifier("hearts_" + (i), "drawable", this.getPackageName());
            else {
                resourceId = getResources().getIdentifier("hearts_" + (i - 13), "drawable", this.getPackageName());
            }
            image.setId(i - 1);
            image.setBackgroundResource(resourceId);
            image.setLayoutParams(params);

            cardList.add(image);
        }
    }

    private void positionCardsInCircle() {
        double angleStep = 360.0 / TOTAL_NUMBER_OF_CARDS;

        for (int i = 0; i < cardList.size(); i++) {
            double angle = i * angleStep;

            cardList.get(i).setX((float) screenWidth / 2 - (float) cardWidth / 2);
            cardList.get(i).setY((float) screenHeight);
            cardList.get(i).setRotation((float) angle);

            container.addView(cardList.get(i));
        }
        animateCard();
    }

    public void animateCard() {
        int adjustedRadius = RADIUS - Math.max(cardWidth, cardHeight) / 2;

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i);

            double angle = 360.0 / TOTAL_NUMBER_OF_CARDS * i;
            double radians = Math.toRadians(angle);

            int x = (int) (centerX + adjustedRadius * Math.cos(radians));
            int y = (int) (centerY + adjustedRadius * Math.sin(radians));

            int finalI = i;
            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(50L * i)
                    .setDuration(300)
                    .setInterpolator(new DecelerateInterpolator())
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            if (finalI == cardList.size() - 1)
                                scaleAnimation();
                        }
                    })
                    .start();
        }
    }

    private void scaleAnimation() {
        for (int i = 0; i < cardList.size(); i++) {
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