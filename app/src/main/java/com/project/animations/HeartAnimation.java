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
    int TOTAL_CARDS = 6;
    int Radius = 200;

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
        for (int i = 1; i <= TOTAL_CARDS; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            int resourceId;
            resourceId = this.getResources().getIdentifier("hearts_" + i, "drawable", this.getPackageName());

            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);

            cardList.add(resourceId);
            container.addView(image);
        }
        setPositions();
    }

    private void setPositions() {
        int cardGap = (int) (screenWidth * 0.035);

        int x = screenWidth / 2 - cardWidth / 2;
        int y = screenHeight / 4;

        float r = -40f;

        for (int i = 1; i <= cardList.size(); i++) {
            ImageView image = findViewById(i);

            image.setX(x += 30);
            image.setY(y -= 5);
            image.setRotation(r += 10);
        }
    }
}