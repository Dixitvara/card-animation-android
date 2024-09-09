package com.project.animations;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class TwoCircleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardList;
    RelativeLayout.LayoutParams params;
    RelativeLayout container;
    Button resetBtn;

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

        cardList = CardMethods.generateCards(26, 0);

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        createCircles();

    }

    private void createCircles() {
        float radius = (float) (screenWidth * 0.12);
        float angle = (float) 360 / 13;
        float x, y;

        float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;

        for (int i = 0; i < cardList.size(); i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2;
            float radiance;

            if (i < 13) {
                angle2 = angle * i;
                radiance = (float) Math.toRadians(angle2);
                y = (float) (screenHeight * 0.3 - radius * Math.cos(radiance));
            } else {
                angle2 = angle * (i - 13);
                radiance = (float) Math.toRadians(angle2);
                y = (float) (screenHeight * 0.6 - radius * Math.cos(radiance));
            }
            x = (float) (centerX + radius * Math.sin(radiance));

            image.setX(x);
            image.setY(y);
            image.setRotation(angle2);
            container.addView(image);
        }

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX(centerX);
            image.setY(screenHeight);

            animateCircle(image, x1, y1, i);
        }
    }

    private void animateCircle(ImageView image, float x1, float y1, int i) {
        image.animate()
                .translationX(x1)
                .translationY(y1)
                .setDuration(300L)
                .setStartDelay(50L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x1);
                    image.setY(y1);
                    if (i == cardList.size() - 1)
                        rotateAnimation();
                })
                .start();
    }

    private void rotateAnimation() {
        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i);

            ObjectAnimator animator = ObjectAnimator.ofFloat(
                            image,
                            "rotation",
                            720
                    )
                    .setDuration(4000L);

            animator.setInterpolator(new DecelerateInterpolator());
//            animator.start();

            image.animate()
                    .rotationBy(720f)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(5000L)
                    .setStartDelay(0L)
                    .start();
        }
    }
}