package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
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

    ArrayList<CardModel> redCardList, blackCardList;
    RelativeLayout.LayoutParams params, heartParams;
    RelativeLayout heartContainer;

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

        redCardList = CardMethods.generateCards(13, 3);
        blackCardList = CardMethods.generateCards(13, 0);

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        heartParams = new RelativeLayout.LayoutParams(180, 180);

        heartContainer = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        container.addView(heartContainer, layoutParams);
        heartContainer.setZ(1);
        heartContainer.setAlpha(0f);

        generateShape();
    }

    private void generateShape() {
        float x, y, rotation;
        float radius = (float) (screenWidth * 0.07);
        float angle;

        ImageView redHeart = new ImageView(this);
        ImageView blackHeart = new ImageView(this);
        ImageView prevImage = null;

        redHeart.setImageResource(R.drawable.fullheart);
        blackHeart.setImageResource(R.drawable.fullheartblack);

        heartContainer.addView(redHeart, heartParams);
        heartContainer.addView(blackHeart, heartParams);

        redHeart.setX((float) screenWidth / 2 - 90);
        redHeart.setY((float) screenHeight / 2 - 90 - 60);
        redHeart.setZ(6);

        blackHeart.setX(redHeart.getX());
        blackHeart.setY(redHeart.getY() + 100f);
        blackHeart.setZ(5);

        // right legs
        // first leg
        angle = (float) 165 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 85f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = redCardList.get(i + 5);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            if (prevImage == null) {
                x = (float) (centerX + radius * Math.sin(radiance));
                y = (float) (centerY - screenWidth * 0.0649 - radius * Math.cos(radiance));
                rotation = -100f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                y = (float) (prevImage.getY() + radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // second leg
        prevImage = null;
        angle = (float) 80 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 35f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = blackCardList.get(i + 7);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 7);

            if (prevImage == null) {
                x = (float) (centerX + radius * Math.sin(radiance));
                y = (float) (centerY - radius * Math.cos(radiance));
                rotation = -140f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // third leg
        prevImage = null;
        angle = (float) 90 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 60f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = redCardList.get(i + 7);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 13);

            if (prevImage == null) {
                x = (float) (centerX + radius * Math.sin(radiance));
                y = (float) (centerY - radius * Math.cos(radiance));
                rotation = -90f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + 12f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // fourth leg
        prevImage = null;
        angle = (float) 165 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 85f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = blackCardList.get(i + 5);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 19);

            if (prevImage == null) {
                x = (float) (centerX + radius * Math.sin(radiance));
                y = (float) (centerY + screenWidth * 0.0371 - radius * Math.cos(radiance));
                rotation = -80f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // left legs
        // first leg
        prevImage = null;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 85f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = blackCardList.get(i + 5);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 26);

            if (prevImage == null) {
                x = (float) (centerX - screenWidth * 0.13 + radius * Math.sin(radiance));
                y = (float) (centerY - screenWidth * 0.0649 - radius * Math.cos(radiance));
                rotation = 100f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.sin(radiance));
                y = (float) (prevImage.getY() + radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // second leg
        prevImage = null;
        angle = (float) 80 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 35f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = redCardList.get(i + 7);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 33);

            if (prevImage == null) {
                x = (float) (centerX - radius * Math.sin(radiance));
                y = (float) (centerY - radius * Math.cos(radiance));
                rotation = 140f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // third leg
        prevImage = null;
        angle = (float) 90 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 60f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = blackCardList.get(i + 7);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 39);

            if (prevImage == null) {
                x = (float) (centerX - radius * Math.sin(radiance));
                y = (float) (centerY - radius * Math.cos(radiance));
                rotation = 90f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - 12f;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // fourth leg
        prevImage = null;
        angle = (float) 165 / 6;
        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            angle2 += 85f;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel cardModel = redCardList.get(i + 5);

            image.setImageResource(cardModel.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i + 45);

            if (prevImage == null) {
                x = (float) (centerX - radius * Math.sin(radiance));
                y = (float) (centerY + screenWidth * 0.0371 - radius * Math.cos(radiance));
                rotation = 80f;
            } else {
                x = (float) (prevImage.getX() - radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() - angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            container.addView(image);
            prevImage = image;
        }

        // tooth
        params = new RelativeLayout.LayoutParams(50, 80);
        prevImage = null;
        for (int i = 0; i < 5; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades_1);

            if (prevImage == null) {
                x = (float) (centerX - screenWidth * 0.01);
                y = (float) (centerY - screenHeight * 0.065);
                rotation = -40f;
                image.setZ(1);
            } else if (i == 1) {
                x = prevImage.getX() + 30;
                y = prevImage.getY() + 15;
                rotation = 0f;
            } else if (i == 2) {
                x = prevImage.getX() + 30;
                y = prevImage.getY() - 15;
                rotation = 40f;
            } else if (i == 3) {
                x = prevImage.getX() + 10;
                y = prevImage.getY() - 40;
                rotation = -10f;
            } else {
                x = prevImage.getX() - 80;
                y = prevImage.getY();
                rotation = 10f;
                image.setZ(1);
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);

            heartContainer.addView(image);
            prevImage = image;
        }
        animate();
    }

    private void animate() {
        heartContainer.animate()
                .alpha(1f)
                .setDuration(1500L)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}