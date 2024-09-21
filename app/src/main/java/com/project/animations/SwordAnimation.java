package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class SwordAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;

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

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        cardList = CardMethods.generateCards(18, 0);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

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
                x = (float) (screenWidth * 0.2);
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

            container.addView(image);
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
                x = (float) (screenWidth * 0.7);
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

            container.addView(image);
        }
        for (int i = 0; i < 36; i++) {
            View image = findViewById(i);

            float x1 = image.getX();
            float y1 = image.getY();

            animateSword(image);
        }
    }

    private void animateSword(View image) {
        image.animate()
                .alpha(1f)
                .setStartDelay(500L)
                .setDuration(800L)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(this::scaleUpAnimation)
                .start();
    }

    private void scaleUpAnimation() {
        ArrayList<View> sortedCards = sortAndDirectCards(3);
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
            animatorSet.setStartDelay(40L * i);
            animatorSet.start();

            if (i == sortedCards.size() - 1)
                swingSword();
        }
    }

    private void swingSword() {

    }

    public ArrayList<View> sortAndDirectCards(int index) {
        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < container.getChildCount(); i++) {
            views.add(container.getChildAt(i));
        }

        views.sort((view1, view2) -> {
            int[] location1 = new int[2];
            int[] location2 = new int[2];

            view1.getLocationOnScreen(location1);
            view2.getLocationOnScreen(location2);

            switch (index) {
                case 1:
                    // top to bottom
                    return Integer.compare(location1[1], location2[1]);
                case 2:
                    // right to left
                    return Integer.compare(location2[0], location1[0]);
                case 3:
                    // bottom to top
                    return Integer.compare(location2[1], location1[1]);
                case 4:
                    // left to right
                    return Integer.compare(location1[0], location2[0]);
            }
            return 0;
        });
        return views;
    }
}