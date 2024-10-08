package com.project.animations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardDimension;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class PopCardAnimation extends AppCompatActivity {

    int cardWidth, cardHeight;
    int screenWidth, screenHeight;
    float centerX, centerY;
    Button resetBtn;
    RelativeLayout container;
    RelativeLayout.LayoutParams params;
    ArrayList<CardModel> cardList;

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

        int[] cardDimension = CardDimension.bigCardsParams(displaymetrics);
        cardWidth = cardDimension[0];
        cardHeight = cardDimension[1];

        centerX = ((float) screenWidth / 2) - ((float) cardWidth / 2);
        centerY = ((float) screenHeight / 2) - ((float) cardHeight / 2);

        cardList = CardMethods.allCards();

        params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });
        addCards();
    }

    private void addCards() {
        float x, y;
        float firstCardPosition = (float) (screenWidth * 0.2);

        for (int i = 0; i < cardList.size(); i++) {
            ImageView image = new ImageView(this);
            CardModel card = cardList.get(i);

            image.setImageResource(card.getResourceId(this));
            image.setId(i);

            if (i < 13) {
                x = firstCardPosition;
            } else if (i < 26) {
                x = firstCardPosition + cardWidth;
            } else if (i < 39) {
                x = firstCardPosition + cardWidth * 2;
            } else {
                x = firstCardPosition + cardWidth * 3;
            }
            y = (float) (screenHeight * 0.3);

            image.setX(x);
            image.setY(y);

            container.addView(image, params);
        }
        popCards();
    }

    private void popCards(){

    }
}