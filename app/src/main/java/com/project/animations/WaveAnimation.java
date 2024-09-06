package com.project.animations;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class WaveAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    int cardWidth, cardHeight;
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

        cardList = CardMethods.allCards();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardDimension = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateAllCards();
    }

    private void generateAllCards() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);
        ImageView prevImage = null;
        for (int i = 1; i <= cardList.size(); i++) {
            CardModel card = cardList.get(i - 1);

            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));

            float x, y;
            if (i <= 13) {
                image.setId(i);
                x = i == 1 ? -3f : prevImage.getX() + (float) screenWidth / 13;
                y = i == 1 ? (float) (screenHeight * 0.3) - (float) cardHeight / 2 : prevImage.getY();
            } else if (i <= 26) {
                image.setId(i + 13);
                x = i == 14 ? -3f : prevImage.getX() + (float) screenWidth / 13;
                y = i == 14 ? (float) (screenHeight * 0.5) - (float) cardHeight / 2 : prevImage.getY();
            } else if (i <= 39) {
                image.setId(i - 13);
                x = i == 27 ? -3f : prevImage.getX() + (float) screenWidth / 13;
                y = i == 27 ? (float) (screenHeight * 0.4) - (float) cardHeight / 2 : prevImage.getY();
            } else {
                image.setId(i);
                x = i == 40 ? -3f : prevImage.getX() + (float) screenWidth / 13;
                y = i == 40 ? (float) (screenHeight * 0.6) - (float) cardHeight / 2 : prevImage.getY();
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;

            container.addView(image);
        }

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = findViewById(i + 1);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);

            addingCardAnimation(image, x1, y1, i);
        }
    }

    private void addingCardAnimation(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(300L)
                .setStartDelay(50L * i)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (i == cardList.size() - 1) {
                            waveAnimation();
                        }
                    }
                })
                .start();
    }

    private void waveAnimation() {
        long delay = 40L;
        long duration = 500L;

        for (int i = 1; i <= 52; i++) {
            ImageView image = findViewById(i);

            ObjectAnimator animator = ObjectAnimator.ofFloat(image, "translationY", image.getY() - 60f)
                    .setDuration(duration);

            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setRepeatCount(5);
            animator.setRepeatMode(ValueAnimator.REVERSE);

            if (i <= 13)
                animator.setStartDelay(delay * i);
            else if (i <= 26)
                animator.setStartDelay(delay * (i - 13));
            else if (i <= 39)
                animator.setStartDelay(delay * (i - 26));
            else
                animator.setStartDelay(delay * (i - 39));

            animator.start();
        }
    }
}