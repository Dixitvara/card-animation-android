package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;
    final int TOTAL_CARDS = 52;

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

        cardList = CardMethods.allCards();
        params = new RelativeLayout.LayoutParams(90, 110);

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

            float angle2 = (float) 360 / circleSize * i;
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

            container.addView(image);
        }

        // glasses
        prevImg = null;
        cardList = CardMethods.generateCards(8, 2);

        radius = (float) (screenWidth * 0.06);
        angle = (float) 15 / 7;
        for (int i = 0; i < 8; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(59 + i);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.09);
                y = (float) (screenHeight * 0.35);
                rotation = -10f;
            } else if (i < 7) {
                x = (float) (prevImg.getX() + radius * Math.cos(radiance));
                y = (float) (prevImg.getY() + radius * Math.sin(radiance));
                rotation = prevImg.getRotation() + 5f;
            } else {
                x = (float) (prevImg.getX() + screenWidth * 0.0139);
                y = (float) (prevImg.getY() + screenWidth * (0.0139 * 2));
                rotation = 100f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            container.addView(image);
        }

        // calling the animation
/*        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            if (i < 33) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
                image.setY(screenHeight);
            } else {
                image.setX(i == 33 ? -100f : screenWidth);
                image.setY((float) (screenHeight * 0.35));
            }
            animateCard(image, x1, y1, i);
        }*/
    }
}