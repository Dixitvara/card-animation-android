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

        cardList = CardMethods.generateCards(14, 0);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        generateCards();
    }

    private void generateCards() {
        float x, y;
        float rotation;
        float distance = 8f;

        ImageView prevImage = null;
        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth / 2 - cardWidth / 2);
                y = (float) (screenHeight * 0.6);
                rotation = 175f;
            } else if (i < 6) {
                x = prevImage.getX() - distance;
                y = prevImage.getY() - distance * 10;
                rotation = prevImage.getRotation();
            } else if (i == 6) {
                x = prevImage.getX() + distance * 4;
                y = prevImage.getY() - distance * 10;
                rotation = 45f;
            } else if (i == 7) {
                x = prevImage.getX() + distance * 4;
                y = prevImage.getY();
                rotation = prevImage.getRotation() * -1;
            }
            else if (i == 8){
                x = (float) (prevImage.getX() + distance * 3.5);
                y = prevImage.getY() + distance * 10;
                rotation = -175f;
            }
            else{
                x = prevImage.getX() - distance;
                y = prevImage.getY() + distance * 10;
                rotation = prevImage.getRotation();
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;

            container.addView(image);
        }
    }
}