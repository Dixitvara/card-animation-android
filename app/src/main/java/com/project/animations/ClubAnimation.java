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

public class ClubAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int cardHeight, cardWidth;
    int TotalCards = 26;

    ArrayList<CardModel> cardList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        container = findViewById(R.id.container);
        resetBtn = findViewById(R.id.resetBtn);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        System.out.println("-} " + screenWidth);
        System.out.println("-} " + screenHeight);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        cardList = CardMethods.generateCards(TotalCards, 0);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCards();
    }

    private void generateCards() {
        float x, y;
        float rotation, firstCardRotation = 0f;

        ImageView prevImg = null;

        long duration = 300L;
        long startDelay = 30L;

        float radius = 60f;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        int halfCircleCardCount = 13;

        for (int i = 0; i < halfCircleCardCount; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            container.addView(image);

            float angle2 = (float) (210 / halfCircleCardCount) * i;
            float radiance = (float) Math.toRadians(angle2);

            if (prevImg == null) {
                x = (float) screenWidth / 4;
                y = (float) (screenHeight * 0.4);
                rotation = firstCardRotation;
            } else {
                x = (float) (prevImg.getX() - radius * Math.cos(radiance));
                y = (float) (prevImg.getY() + radius * Math.sin(radiance));
                rotation = firstCardRotation - angle2;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }
        prevImg = null;

        ImageView firstImage = findViewById(0);

        float angle = (float) (-210 / halfCircleCardCount);
        for (int i = 13; i < 26; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setImageResource(card.getResourceId(this));
            image.setLayoutParams(params);
            image.setId(i);

            container.addView(image);

            int index = i - 13;

            float angle2 = (float) (-210 / halfCircleCardCount) * index;
            float radiance = (float) Math.toRadians(angle2);

            if (prevImg == null) {
                x = firstImage.getX();
                y = firstImage.getY();
                rotation = -110f;
            } else {
                x = (float) (prevImg.getX() - radius * Math.sin(radiance));
                y = (float) (prevImg.getY() - radius * Math.cos(radiance));
                rotation = prevImg.getRotation() - angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }

/*        for (int i = 0; i < TotalCards; i++) {
            ImageView image = findViewById(12 - i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);

            animateHeart(image, imageX, imageY, duration, startDelay, i);
        }*/

    }

    private void animateHeart(ImageView image, float x, float y, long duration, long startDelay, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .setStartDelay(startDelay * i)
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                }).start();
    }
}
