package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
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
import com.project.animations.utils.MyAnim;

import java.util.ArrayList;

public class SwordAnimation extends AppCompatActivity {

    Button resetBtn;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    LinearLayout linearLayout;
    RelativeLayout leftLayout, rightLayout;

    ArrayList<CardModel> cardList;
    ArrayList<View> views;
    RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_linear_layout);

        resetBtn = findViewById(R.id.resetBtn);
        linearLayout = findViewById(R.id.containerLinear);

        leftLayout = new RelativeLayout(this);
        rightLayout = new RelativeLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.weight = 1;
        linearLayout.addView(leftLayout, layoutParams);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                0,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams2.weight = 1;
        linearLayout.addView(rightLayout, layoutParams2);

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

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        cardList = CardMethods.generateCards(18, 0);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        views = new ArrayList<>();

        generateCards();
    }

    private void generateCards() {
        float x, y;
        float rotation;
        float distance = (float) (screenWidth * 0.00741);

        ImageView prevImage = null;
        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.25);
                y = (float) (screenHeight * 0.5);
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
                image.setImageResource(R.drawable.hearts1);
                image.setZ(1);
                rotation = 70f;
            } else if (i == 15) {
                x = prevImage.getX() - distance * 12;
                y = prevImage.getY();
                image.setImageResource(R.drawable.hearts1);
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
                image.setImageResource(R.drawable.hearts13);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;

            views.add(image);
            leftLayout.addView(image);
        }

        prevImage = null;
        cardList = CardMethods.generateCards(18, 1);
        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 18);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.15);
                y = (float) (screenHeight * 0.5);
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

            views.add(image);
            rightLayout.addView(image);
        }

        inAnimation();
    }

    private void inAnimation() {
        ObjectAnimator translateLeftLayout = ObjectAnimator.ofFloat(
                        leftLayout,
                        "translationX",
                        leftLayout.getWidth() - screenWidth,
                        leftLayout.getX())
                .setDuration(800L);
        ObjectAnimator translateRightLayout = ObjectAnimator.ofFloat(
                        rightLayout,
                        "translationX",
                        screenWidth,
                        rightLayout.getX())
                .setDuration(800L);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateLeftLayout, translateRightLayout);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleUpAnimation();
            }
        });
    }

    private void scaleUpAnimation() {
        ArrayList<View> sortedCards = MyAnim.sortAndDirectCards(views, 3);
        for (int i = 0; i < sortedCards.size(); i++) {
            View image = sortedCards.get(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f)
                    .setDuration(300L);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f)
                    .setDuration(300L);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(300L);
            animatorSet.setStartDelay(40L * i);
            animatorSet.start();
            int finalI = i;
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (finalI == sortedCards.size() - 1)
                        swingSword();
                }
            });
        }
    }

    public void swingSword() {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(leftLayout, "rotation", 45f)
                .setDuration(700L);
        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(rightLayout, "rotation", -45f)
                .setDuration(700L);

        rotate.setRepeatMode(ValueAnimator.REVERSE);
        rotate.setRepeatCount(2);
        rotate2.setRepeatMode(ValueAnimator.REVERSE);
        rotate2.setRepeatCount(2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate, rotate2);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                outAnimation();
            }
        });
    }

    public void outAnimation() {
        MyAnim.translateXTo(leftLayout, screenWidth, 700L);
        MyAnim.translateXTo(rightLayout, screenWidth * -1, 700L);
    }
}