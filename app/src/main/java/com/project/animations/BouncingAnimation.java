package com.project.animations;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.utils.CardDimension;

import java.util.Stack;


public class BouncingAnimation extends AppCompatActivity {
    Button resetBtn;
    RelativeLayout container;
    final int TOTAL_CARDS = 52;
    Stack<Integer> spadesCards, diamondCards;
    Stack<Integer> heartCards, clubCards;
    Handler handler;
    int cardHeight, cardWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        clubCards = new Stack<>();
        diamondCards = new Stack<>();
        heartCards = new Stack<>();
        spadesCards = new Stack<>();
        handler = new Handler();

        // reset button
        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        cardWidth = cardParams[0];
        cardHeight = cardParams[1];

        // generating cards
        generateCards();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // popping cards
                popSpadesCard();
            }
        }, 1000);
    }

    private void generateCards() {
        int resourceId;

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            image.setId(i);
            image.setLayoutParams(params);

            if (i <= 13) {
                resourceId = this.getResources().getIdentifier("spades_" + i, "drawable", this.getPackageName());
                spadesCards.push(resourceId);
                image.setX(50);
            } else if (i <= 26) {
                resourceId = this.getResources().getIdentifier("clubs_" + (i - 13), "drawable", this.getPackageName());
                heartCards.push(resourceId);
                image.setX(200);
            } else if (i <= 39) {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 26), "drawable", this.getPackageName());
                clubCards.push(resourceId);
                image.setX(350);
            } else {
                resourceId = this.getResources().getIdentifier("diamonds_" + (i - 39), "drawable", this.getPackageName());
                diamondCards.push(resourceId);
                image.setX(500);
            }
            image.setImageResource(resourceId);

            container.addView(image);
        }
    }

    private void bouncingAnimation() {

    }

    private void popSpadesCard() {
        if (spadesCards.isEmpty()) return;
        int id = spadesCards.pop();
        ImageView image = findViewById(id);
        container.removeView(image);
        System.out.println("==> removed : " + id);
        System.out.println("==> image : " + image);
        System.out.println("==> size : " + spadesCards.size());
    }

    private void popHeartCard() {

    }

    private void popClubCard() {

    }

    private void popDiamondCard() {

    }

}