package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;

import java.util.ArrayList;

public class DiamondAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    final int TOTAL_CARDS = 40;
    Button resetBtn;
    ArrayList<Integer> cardList;
    RelativeLayout container;
    int cardWidth, cardHeight;
    int cardGap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cardList = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        cardGap = (int) (screenWidth * 0.032);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        // generating cards
        generateCards();

    }

    private void generateCards() {
        int resourceId;

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("spades_" + i, "drawable", this.getPackageName());
            else if (i <= 26)
                resourceId = this.getResources().getIdentifier("spades_" + (i - 13), "drawable", this.getPackageName());
            else if (i <= 39)
                resourceId = this.getResources().getIdentifier("spades_" + (i - 26), "drawable", this.getPackageName());
            else
                resourceId = this.getResources().getIdentifier("spades_" + (i - 39), "drawable", this.getPackageName());

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            cardList.add(resourceId);
            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);
            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);

            container.addView(image);
        }
//        setPosition();
        animate();
    }

    // set card position
    private void setPosition() {
        int x = screenWidth / 2 - cardWidth / 2;
        int y = screenHeight / 4;
        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);

            if (i == 11) {
                x += cardGap;
                y += cardGap;
            }
            if (i == 21) {
                x -= cardGap;
                y += cardGap;
            }
            if (i == 31) {
                x -= cardGap;
                y -= cardGap;
            }

            if (i <= 10) {
                image.setX(x += cardGap);
                image.setY(y += cardGap);
                image.setRotation(-45);
            }
//            x += 25;
            if (i >= 11 && i <= 20) {
                image.setRotation(45);
                image.setX(x -= cardGap);
                image.setY(y += cardGap);
            }
            if (i >= 21 && i <= 30) {
                image.setRotation(-45);
                image.setX(x -= cardGap);
                image.setY(y -= cardGap);
            }
            if (i >= 31) {
                image.setRotation(45);
                image.setX(x += cardGap);
                image.setY(y -= cardGap);
            }
        }
    }

    private void animate() {
        int x = screenWidth / 2 - cardWidth / 2;
        int y = screenHeight / 4;

        long duration = 500;
        long delay = 30;

        for (int i = 1; i <= cardList.size(); i++) {

            ImageView image = findViewById(i);

            if (i == 11) {
                x += cardGap;
                y += cardGap;
            }
            if (i == 21) {
                x -= cardGap;
                y += cardGap;
            }
            if (i == 31) {
                x -= cardGap;
                y -= cardGap;
            }

            if (i <= 10) {
                x += cardGap;
                y += cardGap;
                int rotation = -45;
                setXYPosition(image, x, y, rotation, duration, delay, i);
            }
            if (i >= 11 && i <= 20) {
                x -= cardGap;
                y += cardGap;
                int rotation = 45;
                setXYPosition(image, x, y, rotation, duration, delay, i);
            }

            if (i >= 21 && i <= 30) {
                x -= cardGap;
                y -= cardGap;
                int rotation = -45;
                setXYPosition(image, x, y, rotation, duration, delay, i);
            }

            if (i >= 31 && i <= 40) {
                x += cardGap;
                y -= cardGap;
                int rotation = 45;
                setXYPosition(image, x, y, rotation, duration, delay, i);
            }
        }

        new Handler().postDelayed(this::scaleUpAnimation, 1800);
    }

    private void setXYPosition(ImageView image, int x, int y, int rotation, long duration, long delay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .rotation(rotation)
                .setDuration(duration)
                .setStartDelay(delay * i)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    private void scaleUpAnimation() {
        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f);

            scaleX.setDuration(200L);
            scaleY.setDuration(200L);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(30L * i);

            animatorSet.start();
        }
    }
}