package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import com.project.animations.utils.MyAnim;

import java.util.ArrayList;

public class DiamondAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    final int TOTAL_CARDS = 40;
    Button resetBtn;
    RelativeLayout container;
    int cardWidth, cardHeight;
    float cardGap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        cardGap = (float) (screenWidth * 0.03);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        animate();
    }

    private void animate() {
        ArrayList<CardModel> cardModels = CardMethods.generateCards(TOTAL_CARDS, 1);

        float x, y;
        float rotation;

        long duration = 400L;
        long delay = 30L;

        ImageView prevImg = null;

        // designing animation
        for (int i = 1; i <= 40; i++) {
            CardModel card = cardModels.get(i - 1);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            container.addView(image);

            if (prevImg == null) {
                x = (float) screenWidth / 2;
                y = (float) screenHeight / 4;
                rotation = -45;
            } else {
                if (i <= 10) {
                    x = prevImg.getX() + cardGap;
                    y = prevImg.getY() + cardGap;
                    rotation = prevImg.getRotation();
                } else if (i <= 20) {
                    x = prevImg.getX() - cardGap;
                    y = i == 11 ? prevImg.getY() : prevImg.getY() + cardGap;
                    rotation = 45;
                } else if (i <= 30) {
                    x = i == 21 ? prevImg.getX() : prevImg.getX() - cardGap;
                    y = prevImg.getY() - cardGap;
                    rotation = -45;
                } else {
                    x = prevImg.getX() + cardGap;
                    y = i == 31 ? prevImg.getY() : prevImg.getY() - cardGap;
                    rotation = 45;
                }
                if (i == 10 || i == 20 || i == 30 || i == 40)
                    image.setVisibility(ImageView.INVISIBLE);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

        // calling animation
        for (int i = 1; i <= 40; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();
            float rotate = image.getRotation();

            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);
            image.setRotation(0f);

            animateDiamond(image, x1, y1, rotate, duration, delay, i);
        }
    }

    private void animateDiamond(ImageView image, float x, float y, float rotation, long duration, long delay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .rotation(rotation)
                .setDuration(duration)
                .setStartDelay(delay * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == TOTAL_CARDS - 1)
                        scaleUpAnimation();
                })
                .start();
    }

    private void scaleUpAnimation() {
        for (int i = 1; i <= TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f);

            scaleX.setDuration(300L);
            scaleY.setDuration(300L);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(30L * i);

            animatorSet.start();
            int finalI = i;
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalI == TOTAL_CARDS)
                        outAnimation();
                }
            });
        }
    }

    private void outAnimation() {
        MyAnim.translateYTo(container, screenHeight * -1, 700L);
    }
}