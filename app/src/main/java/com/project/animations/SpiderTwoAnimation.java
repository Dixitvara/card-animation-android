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

public class SpiderTwoAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params, heartParams;

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

        cardList = CardMethods.generateCards(7, 1);
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        heartParams = new RelativeLayout.LayoutParams(250, 250);

        generateShape();
    }

    private void generateShape() {
        float x, y, rotation;
        float radius = 60f;
        float angle = (float) 160 / 7;

        ImageView redHeart = new ImageView(this);
        ImageView prevImage = null;
        redHeart.setImageResource(R.drawable.fullheart);

        ImageView blackHeart = new ImageView(this);
        blackHeart.setImageResource(R.drawable.fullheartblack);

        container.addView(redHeart, heartParams);
        container.addView(blackHeart, heartParams);

        redHeart.setX((float) screenWidth / 2 - 125);
        redHeart.setY((float) screenHeight / 2 - 125);
        redHeart.setZ(3);

        blackHeart.setX(redHeart.getX());
        blackHeart.setY(redHeart.getY() + 140f);

        for (int i = 0; i < 7; i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = cardList.get(i);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            if (i == 0) {
                x = redHeart.getX();
                y = redHeart.getY();
                rotation = -30f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.cos(radiance));
                y = (float) (prevImage.getY() - radius * Math.sin(radiance));
                rotation = prevImage.getRotation() + 10f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            container.addView(image);
        }
    }
}