package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class SpiderAnimation extends AppCompatActivity {

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

        cardList = CardMethods.generateCards(13, 2);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        generateCards();
    }

    private void generateCards() {
        float x, y;
        ImageView prevImage = null;

        // spider body
        for (int i = 0; i < 14; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades_1);
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.28);
                y = (float) (screenHeight * 0.14);
            } else if (i < 5) {
                x = prevImage.getX() + cardWidth;
                y = prevImage.getY();
            } else if (i == 5) {
                x = prevImage.getX() - (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
            } else if (i < 9) {
                x = prevImage.getX() - cardWidth;
                y = prevImage.getY();
            } else if (i == 9) {
                image.setImageResource(R.drawable.spades_1);
                x = prevImage.getX() + (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
            } else if (i < 12) {
                x = prevImage.getX() + cardWidth;
                y = prevImage.getY();
            } else if (i == 12) {
                x = prevImage.getX() - (float) cardWidth / 2;
                y = prevImage.getY() + cardHeight;
            } else {
                x = prevImage.getX() - cardWidth;
                y = prevImage.getY();
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
        }

        // left legs
        // first leg
        prevImage = null;
        float radius = (float) (screenWidth * 0.04);
        float angle = (float) 180 / 13;
        float horizontalRadius = (float) (screenWidth * 0.03);
        float rotation;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.28);
                y = (float) (screenHeight * 0.13);
                rotation = 160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            }
            else{
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
//            image.setVisibility(View.INVISIBLE);
        }

        // second leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.024);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.32);
                y = (float) (screenHeight * 0.19);
                rotation = 160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            }
            else{
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }


        // third leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.018);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.35);
                y = (float) (screenHeight * 0.25);
                rotation = 160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            }
            else{
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // fourth leg
        radius = (float) (screenWidth * 0.032);
        horizontalRadius = (float) (screenWidth * 0.014);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.40);
                y = (float) (screenHeight * 0.31);
                rotation = 160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 16f;
            }
            else{
                x = (float) (prevImage.getX() - horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 4f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // right legs
        // first leg
        prevImage = null;
        radius = (float) (screenWidth * 0.04);
        horizontalRadius = (float) (screenWidth * 0.03);
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.64);
                y = (float) (screenHeight * 0.13);
                rotation = -160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            }
            else{
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // second leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.024);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.60);
                y = (float) (screenHeight * 0.19);
                rotation = -160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            }
            else{
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }


        // third leg
        radius = (float) (screenWidth * 0.035);
        horizontalRadius = (float) (screenWidth * 0.018);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.57);
                y = (float) (screenHeight * 0.25);
                rotation = -160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            }
            else{
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 2f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }

        // fourth leg
        radius = (float) (screenWidth * 0.032);
        horizontalRadius = (float) (screenWidth * 0.014);
        prevImage = null;
        for (int i = 1; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.52);
                y = (float) (screenHeight * 0.31);
                rotation = -160f;
            } else if (i < 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 18f;
            } else if (i == 8) {
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            } else if (i == 9){
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 16f;
            }
            else{
                x = (float) (prevImage.getX() + horizontalRadius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 4f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }
    }
}