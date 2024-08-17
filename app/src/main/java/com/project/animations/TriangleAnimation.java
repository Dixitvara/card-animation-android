package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;

import java.util.ArrayList;

public class TriangleAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    final int TOTAL_CARDS = 40;
    Button resetBtn;
    ArrayList<Integer> cardList;
    RelativeLayout container;
    int cardWidth, cardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        cardList = new ArrayList<>();

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

        // generating cards
        generateCards();

    }

    private void generateCards() {
        int resourceId;
        float cardXCenter = (float) cardWidth / 2;

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("spades_" + i, "drawable", this.getPackageName());
            else if (i <= 26)
                resourceId = this.getResources().getIdentifier("spades_" + (i - 13), "drawable", this.getPackageName());
            else if (i <= 39)
                resourceId = this.getResources().getIdentifier("spades_" + (i - 26), "drawable", this.getPackageName());
            else
                resourceId = this.getResources().getIdentifier("spades_" + (i - 39), "drawable", this.getPackageName());

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            cardList.add(resourceId);
            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);
            image.setX((float) screenWidth / 2 - cardXCenter);
            image.setY(screenHeight);

            if (i != 10 && i != 20 && i != 30 && i != 40) {
                container.addView(image);
            }
        }
        animateCards();
    }

    private void animateCards() {
        int x = 0, y = 0;
        int position = (int) (screenWidth * 0.035);

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            ImageView image = findViewById(i);

            if (i == 10 || i == 20 || i == 30 || i == 40) {
                continue;
            }

            float centerX = (float) screenWidth / 2 - (float) cardWidth / 2;

/*
            ObjectAnimator animator;
            ObjectAnimator animator2;

            AnimatorSet animatorSet = new AnimatorSet();

            animator = ObjectAnimator.ofFloat(image, "translationX", centerX, 0f);
            animator2 = ObjectAnimator.ofFloat(image, "translationY", screenHeight, 0f);

            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.setStartDelay(80 * i);

            animator.setDuration(300);
            animator2.setDuration(300);

            animatorSet.playTogether(animator, animator2);
            animatorSet.setDuration(100);

            if (x != 0 && y != 0) {
                if (i >= 11 && i <= 20) {
                    image.setRotation(45);

                    animator.setFloatValues(x -= position);
                    animator2.setFloatValues(y += position);

                    animatorSet.start();

                } else if (i >= 21 && i <= 30) {
                    image.setRotation(-45);

                    animator.setFloatValues(x -= position);
                    animator2.setFloatValues(y -= position);

                    animatorSet.start();

                } else if (i >= 31) {
                    image.setRotation(45);

                    animator.setFloatValues(x += position);
                    animator2.setFloatValues(y -= position);

                    animatorSet.start();
                } else {
                    image.setRotation(-45f);

                    animator.setFloatValues(x += position);
                    animator2.setFloatValues(y += position);

                    animatorSet.start();
                }
            } else {
                image.setRotation(-45f);

                animator.setFloatValues((float) screenWidth / 2);
                animator2.setFloatValues((float) screenHeight / 4);

                animatorSet.start();

                x = screenWidth / 2;
                y = screenHeight / 4;
            }
*/
            if (x != 0 && y != 0) {

            } else {
                image.animate()
                        .rotation(-45f)
                        .translationX((float) screenWidth / 2)
                        .translationY((float) screenHeight / 4)
                        .start();
                x = screenWidth / 2;
                y = screenHeight / 4;
            }
            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(50L * i)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }
    }
}