package com.project.animations;

import android.animation.ValueAnimator;
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

public class FourCirclesAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container, circle1, circle2, circle3, circle4;
    RelativeLayout.LayoutParams params, layoutParams;
    ArrayList<CardModel> cardList;
    float radius;

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

        cardList = CardMethods.generateCards(13, 1);

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        radius = (float) (screenWidth * 0.1);

        // circle layouts
        circle1 = new RelativeLayout(this);

        container.addView(circle1, layoutParams);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        generateCircles();
    }

    private void generateCircles() {
        float x, y, rotation;
        float angle = (float) 360 / 13;

        ImageView prevImage = null;

        for (int i = 0; i < cardList.size(); i++) {
            float angle2 = angle * i;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);
            CardModel card = cardList.get(12 - i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            x = (float) (centerX + radius * Math.sin(radiance));
            y = (float) (centerY - radius * Math.cos(radiance));

            if (prevImage == null) {
                rotation = -180f;
            } else {
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImage = image;
            circle1.addView(image);
        }

        for (int i = 0; i < 13; i++) {
            View image = findViewById(i);

            float x2 = image.getX();
            float y2 = image.getY();

//            image.setX(centerX);
//            image.setY(centerY);

//            animateCircle2(image, i);
        }
    }

    private void animateCircle2(View image, int i) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, image.getRotation());
        valueAnimator.setDuration(1000L);

        valueAnimator.addUpdateListener(animation -> {
            float angle = (float) valueAnimator.getAnimatedValue();
            double radiance = Math.toRadians(angle);

            float x = (float) (centerX + radius * Math.sin(radiance));
            float y = (float) (centerY - radius * Math.cos(radiance));

            image.setX(x);
            image.setY(y);
            image.setRotation((float) valueAnimator.getAnimatedValue());
        });
        valueAnimator.start();
    }
}