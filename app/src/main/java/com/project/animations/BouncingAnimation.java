package com.project.animations;

import static values.values.CARD_HEIGHT;
import static values.values.CARD_WIDTH;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Stack;


public class BouncingAnimation extends AppCompatActivity {
    Button resetBtn;
    RelativeLayout container;
    ArrayList<Integer> cardList;
    final int TOTAL_CARDS = 52;
    int screenWidth, screenHeight;
    Stack<Integer> spadesCards, diamondCards;
    Stack<Integer> heartCards, clubCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cardList = new ArrayList<>();
        clubCards = new Stack<>();
        diamondCards = new Stack<>();
        heartCards = new Stack<>();
        spadesCards = new Stack<>();

        // getting device screenHeight and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // set those in variables
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        // reset button
        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        // generating cards
        generateCards();
    }

    private void generateCards() {
        int resourceId;

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            CardView image = new CardView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CARD_WIDTH, CARD_HEIGHT);

            image.setId(i);
            image.setLayoutParams(params);

            if (i <= 13) {
                resourceId = this.getResources().getIdentifier("s" + i, "drawable", this.getPackageName());
                spadesCards.push(resourceId);
                image.setX(50);
            } else if (i <= 26) {
                resourceId = this.getResources().getIdentifier("s" + (i - 13), "drawable", this.getPackageName());
                heartCards.push(resourceId);
                image.setX(200);
            } else if (i <= 39) {
                resourceId = this.getResources().getIdentifier("s" + (i - 26), "drawable", this.getPackageName());
                clubCards.push(resourceId);
                image.setX(350);
            } else {
                resourceId = this.getResources().getIdentifier("s" + (i - 39), "drawable", this.getPackageName());
                diamondCards.push(resourceId);
                image.setX(500);
            }

            cardList.add(resourceId);
            image.setBackgroundResource(resourceId);

            container.addView(image);
        }
    }
}