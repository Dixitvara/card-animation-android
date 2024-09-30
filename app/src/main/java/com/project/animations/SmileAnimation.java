package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.tv.TvView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;
import com.project.animations.utils.MyAnim;

import java.util.ArrayList;

public class SmileAnimation extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    int screenWidth, screenHeight;
    int centerX, centerY;
    int cardHeight, cardWidth;

    ArrayList<CardModel> cardList;
    ArrayList<View> views;
    RelativeLayout.LayoutParams params;
    final int TOTAL_CARDS = 35;

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

        int[] cardParams = CardDimension.smallCardsParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        centerX = (screenWidth / 2) - (cardWidth / 2);
        centerY = (screenHeight / 2) - (cardHeight / 2);

        cardList = CardMethods.generateSelectedCards(TOTAL_CARDS, new int[] {0,1,2});
        views = new ArrayList<>();
        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        generateCards();
    }

    private void generateCards() {
        int circleSize = 26;
        float x, y;
        float rotation;
        float radius = (float) (screenWidth * 0.09);
        float angle = (float) 360 / circleSize;

        ImageView prevImg = null;

        //  circle
        for (int i = 0; i < circleSize; i++) {
            CardModel card = cardList.get(i);
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            float angle2 = (float) 360 / circleSize * i;
            double radians = Math.toRadians(angle2);

            if (prevImg == null) {
                x = (float) (screenWidth * 0.83);
                y = centerY;
                rotation = 5f;
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
            views.add(image);
        }

        // smile lips
        for (int i = 2; i < 11; i++) {
            float radius2 = (float) (screenWidth * 0.05);
            float angle2 = angle * (i + 1);
            float radiance = (float) Math.toRadians(angle2);

            CardModel card = cardList.get(i);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(24 + i);

            if (i == 2) {
                x = (float) (screenWidth * 0.6);
                y = (float) (screenHeight * 0.55);
                rotation = 45f;
            } else {
                x = (float) (prevImg.getX() - radius2 * Math.sin(radiance));
                y = (float) (prevImg.getY() + radius2 * Math.cos(radiance));
                rotation = prevImg.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;

            // eyes
            if (i == 9) {
                image.setX((float) (screenWidth * 0.3));
                image.setY((float) (screenHeight * 0.4));
                image.setImageResource(R.drawable.clubs1);
                image.setRotation(-10f);
            }
            if (i == 10) {
                image.setX((float) (screenWidth * 0.6));
                image.setY((float) (screenHeight * 0.4));
                image.setImageResource(R.drawable.clubs1);
                image.setRotation(10f);
            }
            container.addView(image);
            views.add(image);
        }

        // calling the animation
        for (int i = 0; i < TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            if (i < 33) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
                image.setY(screenHeight);
            } else {
                image.setX(i == 33 ? -cardWidth : screenWidth);
                image.setY((float) (screenHeight * 0.4));
            }
            animateCard(image, x1, y1, i);
        }
    }

    private void animateCard(ImageView image, float x, float y, int i) {
        image.animate()
                .translationX(x)
                .translationY(y)
                .setStartDelay(30L * i)
                .setDuration(400L)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == TOTAL_CARDS - 1) {
                        scaleUpAnimation();
                    }
                })
                .start();
    }

    private void scaleUpAnimation() {
        ArrayList<View> viewsList = MyAnim.sortAndDirectCards(views,4);
        for (int i = 0; i < viewsList.size(); i++) {

            View view = viewsList.get(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.3f);

            scaleX.setDuration(500L);
            scaleY.setDuration(500L);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            scaleX.setInterpolator(new DecelerateInterpolator());
            scaleY.setInterpolator(new DecelerateInterpolator());

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(45L * (i));
            animatorSet.start();
            int finalI = i;
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalI == cardList.size() -1 )
                        outAnimation();
                }
            });
        }
    }

    private void outAnimation() {
        MyAnim.translateXTo(container, screenWidth, 800L);
    }

}