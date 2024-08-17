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

public class AnimationActivity extends AppCompatActivity {

    private static final int TOTAL_NUMBERS_OF_CARD = 26;
    Button resetBtn;
    RelativeLayout container;
    float x = 10f;
    float y = 0f;
    int screenWidth, screenHeight;
    ArrayList<Integer> diamondCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        diamondCards = new ArrayList<>();

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        // reset button
        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int[] cardParams = CardDimension.getCardParams(displayMetrics);
        int cardWidth = cardParams[0];
        int cardHeight = cardParams[1];

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        int resourceId;

        for (int i = 1; i <= TOTAL_NUMBERS_OF_CARD; i++) {
            if (i <= 13) {
                resourceId = this.getResources().getIdentifier("spades_" + i, "drawable", this.getPackageName());
            } else {
                resourceId = this.getResources().getIdentifier("hearts_" + (i - 13), "drawable", this.getPackageName());
            }
            diamondCards.add(resourceId);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams imgParam = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

            image.setId(i);
            image.setBackgroundResource(resourceId);
            image.setX((float) screenWidth / 2 - (float) cardWidth / 2);
            image.setY(screenHeight);
            image.setLayoutParams(imgParam);

            container.addView(image);
        }
        animateCard();
    }

    public void animateCard() {
        for (int i = 0; i < diamondCards.size(); i++) {
            if (x >= screenWidth) {
                y += 30;
                x = 10;
            }

            ImageView image = findViewById(i + 1);

            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(50L * i)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
            x += 150;
        }
    }
}