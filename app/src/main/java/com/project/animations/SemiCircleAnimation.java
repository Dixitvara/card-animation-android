package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class SemiCircleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList;
    RelativeLayout container;
    Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        cardList = CardMethods.allCards();

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateShape();
    }

    private void generateShape() {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(cardWidth, cardHeight);

        int radius = (int) (screenWidth * 0.02);

        float angle = (float) 180 / 13;
        float rotation;
        float x, y;

        ImageView prevImg = null;

        for (int i = 0; i < 26; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            double radiance = (float) (Math.toRadians(angle) * i);

            if (i <= 12) {
                x = i == 0 ? (float) screenWidth / 4 - (float) cardWidth / 2 : (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = i == 0 ? (float) (screenHeight * 0.2) : (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = i == 0 ? 90f : prevImg.getRotation() + angle;
            } else {
                x = i == 13 ? (float) (screenWidth * 0.5) : (float) (prevImg.getX() - radius * Math.sin(radiance));
                y = i == 13 ? (float) (screenHeight * 0.35) : (float) (prevImg.getY() + radius * Math.cos(radiance));
                rotation = i == 13 ? 90f : prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImg = image;
        }

        for (int i = 26; i < 52; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            double radiance = (float) (Math.toRadians(angle) * i);

            if (i <= 38) {
                x = i == 26 ? (float) screenWidth / 4 - (float) cardWidth / 2 : (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = i == 26 ? (float) (screenHeight * 0.5) : (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = i == 26 ? 90f : prevImg.getRotation() + angle;
            } else {
                x = i == 39 ? (float) (screenWidth * 0.5) : (float) (prevImg.getX() - radius * Math.sin(radiance));
                y = i == 39 ? (float) (screenHeight * 0.65) : (float) (prevImg.getY() + radius * Math.cos(radiance));
                rotation = i == 39 ? 90f : prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImg = image;
        }

        for (int i = 1; i <= 52; i++) {
            ImageView image = findViewById(i - 1);
            float x1 = image.getX();
            float y1 = image.getY();

            if (i <= 13 || i > 26 && i <= 39) {
                image.setX(-100f);
                image.setY((float) screenHeight / 2);
            } else {
                image.setX(screenWidth);
                image.setY((float) screenHeight / 2);
            }

            inAnimation(image, x1, y1, i);
        }
    }

    private void inAnimation(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(600L)
                .setStartDelay(10L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
//                        radiusIncreasedAnimation();
                })
                .start();
    }

    private void radiusIncreasedAnimation() {
        for (int i = 1; i <= 52; i++) {
            ImageView image = findViewById(i - 1);
        }
    }
}