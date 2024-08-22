package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
    int Radius = 35;

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

        generateCard();

    }

    private void generateCard() {
        for (int i = 1; i <= 19; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("hearts_" + i, "drawable", this.getPackageName());
            else {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 13), "drawable", this.getPackageName());
            }

            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);

            cardList.add(resourceId);
            container.addView(image);
        }
        setPositions();
    }

    private void setPositions() {
        int rotation = -35;

        ImageView prevImg = null;

        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);
            int angle = 180 / TOTAL_CARDS * i;
            double radians = Math.toRadians(angle);
            int angle2 = 80 / TOTAL_CARDS * i;
            double radians2 = Math.toRadians(angle2);

            if (prevImg == null) {
                image.setX((float) screenWidth / 2);
                image.setY((float) screenHeight / 4);
                image.setRotation(rotation);
            } else {
                image.setX((float) (prevImg.getX() + Radius * Math.sin(radians)));
                image.setY((float) (prevImg.getY() - Radius * Math.cos(radians)));
                image.setRotation(prevImg.getRotation() + 10);
                if (i > 5 && i < 10) {
                    image.setRotation(prevImg.getRotation() + 20);
                }

                if (i > 10) {
                    image.setX((float) (prevImg.getX() + Radius * Math.cos(radians2)));
                    image.setY((float) (prevImg.getY() + Radius * Math.sin(radians2)));
                    image.setRotation(prevImg.getRotation() + 5);
                }
            }

            System.out.println("--} sin : " + Math.sin(radians));
            System.out.println("--} cos : " + Math.cos(radians));
            prevImg = image;
        }
    }
}