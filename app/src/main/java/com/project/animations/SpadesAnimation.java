package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;


public class SpadesAnimation extends AppCompatActivity {

    int screenWidth, screenHeight, cardWidth, cardHeight;
    RelativeLayout container;
    int cards = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        container = findViewById(R.id.container);

        int[] cardParam = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParam[0];
        cardHeight = cardParam[1];

        for (int i = 1; i <= cards; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("spades_" + i, "drawable", this.getPackageName());
            else if (i <= 26) {
                resourceId = this.getResources().getIdentifier("spades_" + (i - 13), "drawable", this.getPackageName());
            } else {
                resourceId = this.getResources().getIdentifier("spades_" + (i - 26), "drawable", this.getPackageName());
            }
            image.setId(i);
            image.setLayoutParams(params);
            image.setImageResource(resourceId);

            container.addView(image);
        }
        animation();
    }

    private void animation() {
        float Radius = (float) (screenWidth * 0.060);
        float radius = (int) (screenWidth * 0.075);
        float rotation, rotate2 = 90;
        float staticAngle = (float) 180 / 10;

        ImageView prevImg = null;

        float x, y;
        int duration = 300;
        long startDelay = 50L;

        for (int i = 1; i <= cards; i++) {
            ImageView image = findViewById(i);

            float angle = (float) (180 / 10) * i - 1;
            double radians = Math.toRadians(angle);

            float angle2 = (float) (80 / 10) * i - 1;
            double radians2 = Math.toRadians(angle2);

            // right part of heart
            if (i == 1) {
                x = (float) screenWidth / 2 - (float) cardWidth / 2;
                y = (float) ((float) screenHeight * 0.6);
                rotation = -45;
            } else {
                rotation = i == 2 ? -45 : prevImg.getRotation();
                if (i <= 10) {
                    x = (float) (prevImg.getX() - Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() + Radius * Math.cos(radians));
                    rotation += 15;
                    image.setRotation(prevImg.getRotation() + staticAngle);
                } else if (i < 14) {
                    x = ((float) (prevImg.getX() - radius * Math.cos(radians2)) - 3);
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation += 6;
                } else {
                    x = (float) (prevImg.getX() - radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation = (prevImg.getRotation() + 11);
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }
/*        for (int i = 20; i <= 38; i++) {
            ImageView image = findViewById(i);

            int index = i - 19;

            float angle = (float) 180 / 10 * index;
            double radians = Math.toRadians(angle);

            float angle2 = (float) 50 / 10 * index;
            double radians2 = Math.toRadians(angle2);

            // right part of heart
            if (index == 1) {
                x = (float) screenWidth / 2 - (float) cardWidth / 2;
                y = (float) ((float) screenHeight * 0.6);
                rotation = 45;
            } else {
                rotation = index == 2 ? 45f : prevImg.getRotation();
                if (index <= 10) {
                    x = (float) (prevImg.getX() + Radius * Math.sin(radians));
                    y = (float) (prevImg.getY() + Radius * Math.cos(radians));
                    rotation -= 15;
                } else if (index < 14) {
                    x = ((float) (prevImg.getX() + radius * Math.cos(radians2)) + 3);
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation -= 6;
                } else {
                    x = (float) (prevImg.getX() + radius * Math.cos(radians2));
                    y = (float) (prevImg.getY() - radius * Math.sin(radians2));
                    rotation = (prevImg.getRotation() - 11);
                }
            }
            image.setX(x);
            image.setY(y);
            image.setRotation(rotation);
            prevImg = image;
        }*/

/*        for (int i = 1; i <= 19; i++) {
            ImageView image = findViewById(i);
            float imageX = image.getX();
            float imageY = image.getY();

            image.setX((float) screenWidth / 2);
            image.setY(screenHeight);

            animateHeart(image, imageX, imageY, duration, startDelay, i - 1);
        }*/
    }

    private void animateHeart(ImageView image, float imageX, float imageY, int duration, long startDelay, int i) {
        image.animate()
                .translationX(imageX)
                .translationY(imageY)
                .setDuration(duration)
                .setInterpolator(new DecelerateInterpolator())
                .setStartDelay(startDelay * i)
                .withEndAction(() -> {
                    image.setX(imageX);
                    image.setY(imageY);
                })
                .start();
    }
}
