package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class SmileTwoAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container, glassContainer, eyesContainer;
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
        glassContainer = new RelativeLayout(this);
        eyesContainer = new RelativeLayout(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        container.addView(glassContainer, layoutParams);
        container.addView(eyesContainer, layoutParams);

        glassContainer.setZ(2);
        glassContainer.setAlpha(0f);
        glassContainer.setPivotY(1000f);

        eyesContainer.setAlpha(0f);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardParams = CardDimension.smallCardsParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        cardList = CardMethods.allCards();
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        generateCards();
    }

    private void generateCards() {
        int circleSize = 52;
        float x, y;
        float rotation;
        float radius = (float) (screenWidth * 0.4);
        float angle = (float) 360 / circleSize;

        ImageView prevImg = null;

        //  circle
        for (int i = 0; i < circleSize; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2 = angle * i;
            double radians = Math.toRadians(angle2);

            x = (float) (centerX + radius * Math.sin(radians));
            y = (float) (centerY - radius * Math.cos(radians));
            if (prevImg == null) {
                rotation = 90f;
            } else {
                rotation = prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
            container.addView(image);
        }

        // smile lips
        float smileAngle = (float) 140 / 7;
        for (int i = 0; i < 7; i++) {
            float radius2 = (float) (screenWidth * 0.065);
            float angle2 = smileAngle * i;
            angle2 += smileAngle;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(52 + i);

            if (i == 0) {
                x = (float) (screenWidth * 0.286);
                y = (float) (screenHeight * 0.56);
                rotation = -30f;
            } else {
                x = (float) (prevImg.getX() + radius2 * Math.sin(radiance));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance));
                rotation = prevImg.getRotation() - smileAngle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            eyesContainer.addView(image);
        }

        // glasses
        prevImg = null;
        cardList = CardMethods.generateCards(34, 2);

        radius = (float) (screenWidth * 0.055);
        angle = (float) 28 / 7;
        float rightGlassAngle = (float) 49 / 7;

        float radius2 = (float) (screenWidth * 0.065);
        float bottomCurveAngle = (float) 230 / 10;

        for (int i = 0; i < 34; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            float rightGlassAngle2 = rightGlassAngle * i;
            float radiance2 = (float) Math.toRadians(rightGlassAngle2);

            float bottomCurveAngle2 = bottomCurveAngle * (i < 25 ? i : i - 10);
            float radiance3 = (float) Math.toRadians(bottomCurveAngle2);

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(59 + i);

            // left card
            if (prevImg == null) {
                x = (float) (screenWidth * 0.095);
                y = (float) (screenHeight * 0.35);
                rotation = -14f;
            } else if (i < 7) {
                x = (float) (prevImg.getX() + radius * Math.cos(radiance));
                y = (float) (prevImg.getY() + radius * Math.sin(radiance));
                rotation = prevImg.getRotation() + 7f;
            }
            // middle top card
            else if (i == 7) {
                card = cardList.get(6);
                image.setImageResource(card.getResourceId(this));

                x = (float) (prevImg.getX() + screenWidth * 0.0556);
                y = prevImg.getY();
                rotation = -26f;
            } else if (i < 14) {
                card = cardList.get(13 - i);
                image.setImageResource(card.getResourceId(this));

                x = (float) (prevImg.getX() + radius * Math.sin(radiance2));
                y = (float) (prevImg.getY() - radius * Math.cos(radiance2));
                rotation = prevImg.getRotation() + 5f;
            }
            // left curve top card
            else if (i == 14) {
                image.setImageResource(R.drawable.spades_1);
                x = (float) (screenWidth * 0.095);
                y = (float) (screenHeight * 0.35);
                rotation = -90f;
            } else if (i < 18) {
                x = (float) (prevImg.getX() + radius2 * Math.sin(radiance3));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance3));
                rotation = prevImg.getRotation() - 12f;
            } else if (i == 18) {
                x = (float) (prevImg.getX() + radius2 * Math.sin(radiance3));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance3));
                rotation = prevImg.getRotation() - 26f;
            } else if (i < 24) {
                x = (float) (prevImg.getX() + radius2 * Math.sin(radiance3));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance3));
                rotation = prevImg.getRotation() - 21f;
                // left curve mid card
                if (i == 23)
                    image.setImageResource(R.drawable.spades_1);
            }
            // right curve end card
            else if (i == 24) {
                x = (float) (screenWidth * 0.79);
                y = (float) (screenHeight * 0.35);
                image.setImageResource(R.drawable.spades_1);
                rotation = 90f;
            } else if (i < 28) {
                x = (float) (prevImg.getX() - radius2 * Math.sin(radiance3));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance3));
                rotation = prevImg.getRotation() + 12f;
                if (i == 25)
                    image.setImageResource(R.drawable.spades_3);
                if (i == 26)
                    image.setImageResource(R.drawable.spades_2);
            } else {
                x = (float) (prevImg.getX() - radius2 * Math.sin(radiance3));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance3));
                rotation = prevImg.getRotation() + 21f;
                // right curve mid card
                if (i == 33)
                    image.setImageResource(R.drawable.spades_1);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            image.setZ(5);
            prevImg = image;

            glassContainer.addView(image);
        }

        // left glass of glass
        // heart shaped glass
        prevImg = null;
        float cardGap = (float) (screenWidth * 0.0093);
        for (int i = 0; i < 18; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.hearts1);
            image.setId(92 + i);

            if (prevImg == null) {
                x = (float) (float) ((screenWidth * 0.095) + screenWidth * 0.0649);
                y = (float) ((float) (screenHeight * 0.35) + screenWidth * 0.06);
            } else if (i < 6) {
                x = prevImg.getX() + (float) cardWidth / 2;
                y = prevImg.getY() + cardGap;
            } else if (i == 6) {
                x = prevImg.getX();
                y = prevImg.getY() + (float) cardHeight / 2;
                image.setVisibility(View.INVISIBLE);
            } else if (i < 12) {
                x = prevImg.getX() - (float) cardWidth / 2;
                y = prevImg.getY() - cardGap;
            } else if (i == 12) {
                x = prevImg.getX();
                y = prevImg.getY() + (float) cardHeight / 2;
            } else {
                x = prevImg.getX() + (float) cardWidth / 2;
                y = prevImg.getY() + cardGap;
                if (i == 17 || i == 16)
                    image.setVisibility(View.INVISIBLE);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(10f);
            image.setZ(4);
            prevImg = image;
            glassContainer.addView(image);
        }

        // right glass of glass
        prevImg = null;
        for (int i = 0; i < 18; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.hearts1);
            image.setId(109 + i);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.53);
                y = (float) ((float) screenHeight * 0.39);
            } else if (i < 6) {
                x = prevImg.getX() + (float) cardWidth / 2;
                y = prevImg.getY() - cardGap;
            } else if (i == 6) {
                x = prevImg.getX();
                y = prevImg.getY() + (float) cardHeight / 2;
            } else if (i < 12) {
                x = prevImg.getX() - (float) cardWidth / 2;
                y = prevImg.getY() + cardGap;
            } else if (i == 12) {
                x = prevImg.getX();
                y = prevImg.getY() + (float) cardHeight / 2;
                image.setVisibility(View.INVISIBLE);
            } else {
                x = prevImg.getX() + (float) cardWidth / 2;
                y = prevImg.getY() - cardGap;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(-10f);
            image.setZ(4);
            prevImg = image;
            glassContainer.addView(image);
        }

        // eyes
        prevImg = null;
        CardModel card;
        for (int i = 0; i < 12; i++) {
            ImageView image = new ImageView(this);
            if (i < 6)
                card = cardList.get(i);
            else
                card = cardList.get(i - 6);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(127 + i);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.3);
                y = (float) (screenHeight * 0.4);
                rotation = 60f;
            } else if (i < 6) {
                x = prevImg.getX();
                y = prevImg.getY();
                rotation = prevImg.getRotation() + 60f;
            } else if (i == 6) {
                x = (float) (screenWidth * 0.6);
                y = prevImg.getY();
                rotation = 60f;
            } else {
                x = prevImg.getX();
                y = prevImg.getY();
                rotation = prevImg.getRotation() + 60f;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
            eyesContainer.addView(image);
        }

        // calling the animation
        for (int i = 0; i < 52; i++) {
            View image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX(-cardWidth);
            image.setY(-cardHeight);

            animateCard(image, x1, y1, i);
        }
    }

    private void animateCard(View image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(400L)
                .setStartDelay(20L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    if (i == 51)
                        fadeInGlasses();
                })
                .start();
    }

    private void fadeInGlasses() {
        ObjectAnimator animateGlass = ObjectAnimator.ofFloat(glassContainer, "alpha", 1f);
        animateGlass.setDuration(800L);
        ObjectAnimator animateEyes = ObjectAnimator.ofFloat(eyesContainer, "alpha", 1f);
        animateEyes.setDuration(800L);
        ObjectAnimator glassUp = ObjectAnimator.ofFloat(glassContainer, "rotation", -40f);
        glassUp.setDuration(800L);

        glassUp.setRepeatMode(ValueAnimator.REVERSE);
        glassUp.setRepeatCount(1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animateGlass, animateEyes, glassUp);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }
}