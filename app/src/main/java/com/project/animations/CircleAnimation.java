package com.project.animations;

import static values.values.CARD_HEIGHT;
import static values.values.CARD_WIDTH;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.LinkedList;

public class CircleAnimation extends AppCompatActivity {

    Button resetBtn;
    DisplayMetrics displayMetrics;
    RelativeLayout container;
    int screenWidth, screenHeight;
    LinkedList<CardView> cardList;
    int centerX, centerY;

    final int TOTAL_NUMBER_OF_CARDS = 12;
    final int RADIUS = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cardList = new LinkedList<>();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        centerX = (screenWidth / 2) - (CARD_WIDTH / 2);
        centerY = (screenHeight / 2) - (CARD_HEIGHT / 2);

        // generating cards
        generateCards(TOTAL_NUMBER_OF_CARDS);

        // setting position
        positionCardsInCircle();

    }

    private void generateCards(int totalCards) {
        for (int i = 0; i < totalCards; i++) {
            CardView image = new CardView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CARD_WIDTH, CARD_HEIGHT);

            int resourceId;
            if (i <= 13)
                resourceId = getResources().getIdentifier("h" + (i + 1), "drawable", this.getPackageName());
            else {
                resourceId = getResources().getIdentifier("c" + (i - 13), "drawable", this.getPackageName());
            }
            image.setId(i);
            image.setBackgroundResource(resourceId);
            image.setLayoutParams(params);

            cardList.add(image);
        }
    }

    private void positionCardsInCircle() {
        double angleStep = 360.0 / TOTAL_NUMBER_OF_CARDS;

        for (int i = 0; i < cardList.size(); i++) {
            double angle = i * angleStep;

            cardList.get(i).setX((float) screenWidth / 2 - (float) CARD_WIDTH / 2);
            cardList.get(i).setY((float) screenHeight - (float) CARD_HEIGHT / 2);
            cardList.get(i).setRotation((float) angle);

            container.addView(cardList.get(i));
        }
        animateCard();
    }

    public void animateCard() {
        int adjustedRadius = RADIUS - Math.max(CARD_WIDTH, CARD_HEIGHT) / 2;

        for (int i = 0; i < cardList.size(); i++) {
            CardView image = findViewById(i);

            double angle = 360.0 / TOTAL_NUMBER_OF_CARDS * i;
            double radians = Math.toRadians(angle);

            int x = (int) (centerX + adjustedRadius * Math.cos(radians) - (double) CARD_WIDTH / 2);
            int y = (int) (centerY + adjustedRadius * Math.sin(radians) - (double) CARD_HEIGHT / 2);

            int finalI = i;
            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(100L * i)
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(() -> {
                        if (finalI == cardList.size() - 1)
                            scaleAnimation();
                    })
                    .start();
        }
    }

    private void scaleAnimation() {
        for (int i = 0; i < cardList.size(); i++) {
            CardView image = findViewById(i);

            ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f)
                    .setDuration(400);
            ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f)
                    .setDuration(400);
            ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(image, "scaleX", 1.3f, 1f)
                    .setDuration(400);
            ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(image, "scaleY", 1.3f, 1f)
                    .setDuration(400);

            AnimatorSet scaleUpSet = new AnimatorSet();
            scaleUpSet.playTogether(scaleXUp, scaleYUp);

            AnimatorSet scaleDownSet = new AnimatorSet();
            scaleDownSet.playTogether(scaleXDown, scaleYDown);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(scaleUpSet, scaleDownSet);

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    rotationAnimation();
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {

                }
            });
            animatorSet.start();
        }
    }

    private void rotationAnimation() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(container, "rotation", 0, 2000)
                .setDuration(2000);
        ObjectAnimator translation = ObjectAnimator.ofFloat(container, "translationX", 0, screenWidth)
                .setDuration(1000);

        rotation.start();
        translation.setStartDelay(1000);
        translation.start();
    }
}