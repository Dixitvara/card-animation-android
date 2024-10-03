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

public class TrophyAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.allCards();

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        createShape();
    }

    private void createShape() {
        ImageView prevImage = null;
        float x, y, rotation;
        float angle = (float) 100 / 5;
        float radius = (float) (screenHeight * 0.06);

        for (int i = 0; i < 5; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.45);
                y = (float) (screenHeight * 0.6);
                rotation = -40f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.cos(radiance));
                y = (float) (prevImage.getY() - radius * Math.sin(radiance));
                rotation = prevImage.getRotation() + 10;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;

            container.addView(image, params);
        }
    }
}