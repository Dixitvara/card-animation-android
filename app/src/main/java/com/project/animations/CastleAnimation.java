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

public class CastleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList, cardList2;
    RelativeLayout.LayoutParams params;
    RelativeLayout container;
    Button resetBtn;
    float centerX, centerY;
    float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        container = findViewById(R.id.container);
        resetBtn = findViewById(R.id.resetBtn);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        cardList = CardMethods.generateCards(1, 1);

        centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        params = new RelativeLayout.LayoutParams(80, 110);

        radius = (float) (screenWidth * 0.08);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        generateShape();
    }

    private void generateShape() {
        ImageView prevImage = null;
        float x, y;

        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades1);
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.2);
                y = (float) (screenHeight * 0.5);
            } else if (i < 4) {
                x = prevImage.getX();
                y = prevImage.getY() + cardHeight - 15f;
            } else if (i == 4) {
                x = prevImage.getX() + cardWidth - 15f;
                y = prevImage.getY();
            } else {
                x = prevImage.getX();
                y = prevImage.getY() - cardHeight + 15f;
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
        }

        prevImage = null;
        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades1);
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.6);
                y = (float) (screenHeight * 0.5);
            } else if (i < 4) {
                x = prevImage.getX();
                y = prevImage.getY() + cardHeight - 15f;
            } else if (i == 4) {
                x = prevImage.getX() + cardWidth - 15f;
                y = prevImage.getY();
            } else {
                x = prevImage.getX();
                y = prevImage.getY() - cardHeight + 15f;
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
        }
    }
}