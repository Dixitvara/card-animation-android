package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        container = findViewById(R.id.container);

        cardList = CardMethods.allCards();
        System.out.println("==} " + cardList.size());

        generateShape();
    }

    private void generateShape() {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(cardWidth, cardHeight);

        int radius = (int) (screenWidth * 0.02);

        float angle = (float) 180 / 13;
        float rotation;
        float x = 0, y = 0;

        ImageView prevImg = null;

        for (int i = 0; i < 27; i++) {
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
                x = i == 13 ? (float) (screenWidth * 0.7) : (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = i == 13 ? (float) (screenHeight * 0.35) : (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = i == 13 ? 90f : prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImg = image;
        }

        for (int i = 27; i < 52; i++) {
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
                x = i == 39 ? (float) (screenWidth * 0.7) : (float) (prevImg.getX() + radius * Math.sin(radiance));
                y = i == 39 ? (float) (screenHeight * 0.65) : (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = i == 39 ? 90f : prevImg.getRotation() + angle;
            }
        }
    }
}