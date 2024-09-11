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

public class SmileAnimation extends AppCompatActivity {

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

        cardList = CardMethods.generateCards(40, 1);
        params = new RelativeLayout.LayoutParams(80, 100);

        generateCards();

    }

    private void generateCards() {
        float x, y;
        float rotation;
        float radius = (float) (screenWidth * 0.06);
        float angle = (float) 360 / 40;

        ImageView prevImg = null;


        for (int i = 1; i <= cardList.size(); i++) {
            CardModel card = cardList.get(i - 1);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);


            int angle2 = 360 / 40 * (i - 1);
            double radians = Math.toRadians(angle2);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.84);
                y = centerY;
                rotation = 0f;
            } else {
                x = (float) (prevImg.getX() - radius * Math.sin(radians));
                y = (float) (prevImg.getY() + radius * Math.cos(radians));
                rotation = prevImg.getRotation() + angle;
            }

            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
            container.addView(image);

        }

        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);

            animateCard(image, x1, y1, i);
        }

    }

    private void animateCard(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setStartDelay(30L * i)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        image.setX(x);
                        image.setY(y);
                    }
                })
                .start();
    }
}