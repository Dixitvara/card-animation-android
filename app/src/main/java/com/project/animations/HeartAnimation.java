package com.project.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;

import java.util.ArrayList;

public class HeartAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    int cardWidth, cardHeight;
    int TOTAL_CARDS = 10;
    int Radius;
    int centerX, centerY;

    // views
    Button resetBtn;
    RelativeLayout container;

    // lists
    ArrayList<Integer> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        cardList = new ArrayList<>();

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        centerX = screenWidth / 2;
        centerY = screenHeight / 4;

        Radius = (int) (screenWidth * 0.060);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        generateCard();

    }

    private void generateCard() {
        for (int i = 1; i <= 38; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("hearts_" + i, "drawable", this.getPackageName());
            else if (i <= 26) {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 13), "drawable", this.getPackageName());
            } else {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 26), "drawable", this.getPackageName());
            }

            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);
            image.setX((float) screenWidth / 2);
            image.setY((float) screenHeight);

            cardList.add(resourceId);
            container.addView(image);
        }
//        setPositions();
        animation();
    }

    private void animation() {
        int radius = (int) (screenWidth * 0.075);
        int rotation = -90;

        ImageView prevImg = null;
        ImageView returnedImg = null;

        int x, y;
        int duration = 500;
        int rotate = 0;

        for (int i = 1; i <= 19; i++) {
            ImageView image = findViewById(i);

            int angle = 180 / TOTAL_CARDS * i;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / TOTAL_CARDS * i;
            double radians2 = Math.toRadians(angle2);

            int startDelay = 100 * i;

            if (i == 1) {
                x = (int) (centerX - (float) cardWidth / 2 + 5);
                y = centerY + 5;
                rotate = -140;

                returnedImg = setImageXY(image, x, y, rotate);
                animateHeart(image, x, y, duration, startDelay, rotate, i);
            }

            if (i != 1) {
                if (i <= 10) {
                    x = (int) (prevImg.getX() + Radius * Math.sin(radians));
                    y = (int) (prevImg.getY() - Radius * Math.cos(radians));
                    rotation += 20;

                    returnedImg = setImageXY(image, x, y, rotation);
                    animateHeart(image, x, y, duration, startDelay, rotate, i);
                }
                if (i > 10 && i < 14) {
                    x = (int) ((float) (prevImg.getX() + radius * Math.cos(radians2)) - 5);
                    y = (int) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation += 6;

                    returnedImg = setImageXY(image, x, y, rotation);
                    animateHeart(image, x, y, duration, startDelay, rotate, i);
                }
                if (i > 13) {
                    x = (int) (prevImg.getX() + radius * Math.cos(radians2));
                    y = (int) (prevImg.getY() + radius * Math.sin(radians2));
                    rotation = (int) (prevImg.getRotation() + 11);

                    returnedImg = setImageXY(image, x, y, rotation);
                    animateHeart(image, x, y, duration, startDelay, rotate, i);
                }
            }
            prevImg = returnedImg;
        }


//        prevImg = null;
//        int rotation2 = 90;

/*        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);

            int index = i - 19;
            int angle = 180 / TOTAL_CARDS * index;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / TOTAL_CARDS * index;
            double radians2 = Math.toRadians(angle2);

            if (prevImg == null) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
                image.setY((float) screenHeight / 4);
                image.setRotation(rotation2);
            } else {
                image.setX((float) (prevImg.getX() - Radius * Math.sin(radians)));
                image.setY((float) (prevImg.getY() - Radius * Math.cos(radians)));
                image.setRotation(prevImg.getRotation() - 20);

                if (index > 10 && index < 14) {
                    image.setX((float) (prevImg.getX() - radius * Math.cos(radians2)) + 5);
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() - 6);
                }
                if (index > 13) {
                    image.setX((float) (prevImg.getX() - radius * Math.cos(radians2)));
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() - 11);
                }
            }
            prevImg = image;
        }*/
    }

    private ImageView setImageXY(ImageView image, int x, int y, int rotate) {
        image.setX(x);
        image.setY(y);
        image.setRotation(rotate);
        return image;
    }

    private ImageView animateHeart(ImageView image, float x, float y, int duration, int startDelay, float rotation, int i) {
//        image.animate()
//                .translationX(x)
//                .translationY(y)
//                .setStartDelay(startDelay)
//                .setDuration(duration)
//                .rotation(rotation)
//                .start();

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(image, "translationX", (float) screenWidth / 2, x)
                .setDuration(300L);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(image, "translationY", screenHeight, y)
                .setDuration(300L);

        animatorX.setInterpolator(new DecelerateInterpolator());
        animatorY.setInterpolator(new DecelerateInterpolator());

        animatorX.setStartDelay(50L * i);
        animatorY.setStartDelay(50L * i);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();

//        image.setX(x);
//        image.setY(y);
//        image.setRotation(rotation);

        return image;
    }

    private void setPositions() {
        int radius = (int) (screenWidth * 0.075);
        int rotation = -90;

        ImageView prevImg = null;

        for (int i = 1; i <= 19; i++) {
            ImageView image = findViewById(i);

            int angle = 180 / TOTAL_CARDS * i;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / TOTAL_CARDS * i;
            double radians2 = Math.toRadians(angle2);

            if (prevImg == null) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2 + 5);
                image.setY((float) screenHeight / 4 + 5);
                image.setRotation(-140);
            } else {
                image.setX((float) (prevImg.getX() + Radius * Math.sin(radians)));
                image.setY((float) (prevImg.getY() - Radius * Math.cos(radians)));
                image.setRotation(rotation += 20);

                if (i > 10 && i < 14) {
                    image.setX((float) (prevImg.getX() + radius * Math.cos(radians2)) - 5);
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() + 6);
                }
                if (i > 13) {
                    image.setX((float) (prevImg.getX() + radius * Math.cos(radians2)));
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() + 11);
                }
            }
            prevImg = image;
        }

        prevImg = null;
        int rotation2 = 90;

        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);

            int index = i - 19;
            int angle = 180 / TOTAL_CARDS * index;
            double radians = Math.toRadians(angle);

            int angle2 = 80 / TOTAL_CARDS * index;
            double radians2 = Math.toRadians(angle2);

            if (prevImg == null) {
                image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
                image.setY((float) screenHeight / 4);
                image.setRotation(rotation2);
            } else {
                image.setX((float) (prevImg.getX() - Radius * Math.sin(radians)));
                image.setY((float) (prevImg.getY() - Radius * Math.cos(radians)));
                image.setRotation(prevImg.getRotation() - 20);

                if (index > 10 && index < 14) {
                    image.setX((float) (prevImg.getX() - radius * Math.cos(radians2)) + 5);
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() - 6);
                }
                if (index > 13) {
                    image.setX((float) (prevImg.getX() - radius * Math.cos(radians2)));
                    image.setY((float) (prevImg.getY() + radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() - 11);
                }
            }
            prevImg = image;
        }
    }
}