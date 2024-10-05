package com.project.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public class CastleAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    ArrayList<CardModel> cardListModel;
    ArrayList<View> cardsList;
    RelativeLayout.LayoutParams params;
    RelativeLayout container;
    Button resetBtn;
    float centerX, centerY;
    float radius;

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

        cardListModel = CardMethods.generateCards(8, 0);
        cardsList = new ArrayList<>();

        centerX = (float) screenWidth / 2 - (float) cardWidth / 2;
        centerY = (float) screenHeight / 2 - (float) cardHeight / 2;

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        radius = (float) (screenWidth * 0.1);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        generateShape();
    }

    private void generateShape() {
        ImageView prevImage = null;
        float x, y;
        float cardGapVertical = (float) (screenWidth * 0.0005);
        float cardGapHorizontal = (float) (screenWidth * 0.0005);

        // left
        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades_1);
            image.setId(i);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.2);
                y = (float) (screenHeight * 0.4);
            } else if (i < 4) {
                x = prevImage.getX();
                y = prevImage.getY() + cardHeight + cardGapVertical;
            } else if (i == 4) {
                x = prevImage.getX() + cardWidth + cardGapHorizontal;
                y = prevImage.getY();
            } else {
                x = prevImage.getX();
                y = prevImage.getY() - cardHeight - cardGapVertical;
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
            cardsList.add(image);
        }

        // right
        prevImage = null;
        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.spades_1);
            image.setId(i + 8);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.65);
                y = (float) (screenHeight * 0.4);
            } else if (i < 4) {
                x = prevImage.getX();
                y = prevImage.getY() + cardHeight + cardGapVertical;
            } else if (i == 4) {
                x = prevImage.getX() + cardWidth + cardGapHorizontal;
                y = prevImage.getY();
            } else {
                x = prevImage.getX();
                y = prevImage.getY() - cardHeight - cardGapVertical;
            }
            image.setX(x);
            image.setY(y);
            prevImage = image;
            container.addView(image);
            cardsList.add(image);
        }

        // top horizontal cards
        prevImage = null;
        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardListModel.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 16);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.21 - (double) cardWidth / 2);
                y = (float) (screenHeight * 0.4 - (double) cardHeight / 2);
            } else {
                x = (float) (prevImage.getX() + screenWidth * 0.089);
                y = prevImage.getY();
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(-90f);
            prevImage = image;

            container.addView(image);
            cardsList.add(image);
        }

        // middle half circle
        prevImage = null;
        float angle = (float) 180 / 6;

        for (int i = 0; i < 6; i++) {
            float angle2 = angle * i;
            float rotation;
            float radiance = (float) Math.toRadians(angle2);

            ImageView image = new ImageView(this);

            image.setLayoutParams(params);
            image.setImageResource(R.drawable.hearts1);
            image.setId(i + 24);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.280);
                y = (float) (screenHeight * 0.45);
                rotation = -75f;
            } else {
                x = (float) (prevImage.getX() + radius * Math.sin(radiance));
                y = (float) (prevImage.getY() - radius * Math.cos(radiance));
                rotation = prevImage.getRotation() + angle;
            }
            image.setX(x);
            image.setY(y);
            image.setZ(4);
            image.setRotation(rotation);
            prevImage = image;

            container.addView(image);
            cardsList.add(image);
        }

        // top verticals cards on horizontal cards
        cardListModel = CardMethods.generateCards(5, 1);
        prevImage = null;
        for (int i = 0; i < 5; i++) {
            ImageView image = new ImageView(this);

            CardModel card = cardListModel.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 30);

            if (prevImage == null) {
                x = (float) (screenWidth * 0.21 - ((double) cardWidth / 2) * 1.5);
                y = (float) (screenHeight * 0.4 - ((double) cardHeight / 2) * 1.5);
            } else {
                x = (float) (prevImage.getX() + cardWidth * 1.68);
                y = prevImage.getY();
            }
            image.setX(x);
            image.setY(y);
            image.setZ(2);
            prevImage = image;

            if (i == 2) {
                image.setVisibility(View.INVISIBLE);
            }
            container.addView(image);
            cardsList.add(image);
        }

        // flag and stand
        cardListModel = CardMethods.generateCards(8, 2);
        prevImage = null;
        for (int i = 0; i < 8; i++) {
            ImageView image = new ImageView(this);

            CardModel card = cardListModel.get(i);

            image.setLayoutParams(params);
            image.setImageResource(card.getResourceId(this));
            image.setId(i + 35);

            if (prevImage == null) {
                x = (float) screenWidth / 2 - (float) cardWidth / 2;
                y = (float) (screenHeight * 0.4 - (double) cardHeight / 2);
            } else if (i < 7) {
                x = prevImage.getX();
                y = (float) (prevImage.getY() - screenWidth * 0.0371);
            } else {
                x = (float) (prevImage.getX() + screenWidth * 0.01852);
                y = (float) (prevImage.getY() - screenWidth * 0.0371);
                image.setRotation(-90f);
                image.setImageResource(R.drawable.spades_1);
            }
            image.setX(x);
            image.setY(y);
            image.setZ(5);
            prevImage = image;

            container.addView(image);
            cardsList.add(image);
        }

        for (int i = 0; i < container.getChildCount(); i++) {
            ImageView image = findViewById(i);
            float x1 = image.getX();
            float y1 = image.getY();

            image.setAlpha(0f);

            alphaCards(image, x1, y1, i);
        }
    }

    private void alphaCards(ImageView image, float x, float y, int i) {
        image.animate()
                .alpha(1f)
                .setStartDelay(500L)
                .setDuration(800L)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    image.setX(x);
                    image.setY(y);
                    if (i == container.getChildCount() - 1) {
                        scaleUpAnimation();
                    }
                })
                .start();
    }

    private void scaleUpAnimation() {
        ArrayList<View> sortedCards = MyAnim.sortAndDirectCards(cardsList, 1);
        for (int i = 0; i < sortedCards.size(); i++) {
            View image = sortedCards.get(i);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(image, "scaleX", 1f, 1.3f)
                    .setDuration(500L);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(image, "scaleY", 1f, 1.3f)
                    .setDuration(500L);

            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);

            scaleX.setRepeatCount(1);
            scaleY.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setStartDelay(40L * i);
            animatorSet.start();
            int finalI = i;
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalI == sortedCards.size() - 1)
                        outAnimation();
                }
            });
        }
    }

    private void outAnimation() {
        MyAnim.translateYTo(container, screenHeight, 700L);
    }
}