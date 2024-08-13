package com.project.animations;

import static values.values.CARD_HEIGHT;
import static values.values.CARD_WIDTH;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AnimationActivity extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    //    ImageView image;
    float x = 10f;
    float y = 0f;
    int height, width;
    ArrayList<Integer> diamondCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        // reset button
        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        // getting device screenHeight and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // set those in variables
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        diamondCards = new ArrayList<>();
        int resourceId;

        for (int i = 1; i <= 26; i++) {
            if (i <= 13) {
                resourceId = this.getResources().getIdentifier("s" + i, "drawable", this.getPackageName());
            } else {
                resourceId = this.getResources().getIdentifier("s" + (i - 13), "drawable", this.getPackageName());
            }
            diamondCards.add(resourceId);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams imgParam = new RelativeLayout.LayoutParams(CARD_WIDTH, CARD_HEIGHT);

            image.setId(i);
            image.setImageResource(resourceId);
            image.setX((float) width / 2);
            image.setY(height);
            image.setLayoutParams(imgParam);

            container.addView(image);
        }
        animateCard();
    }

    public void animateCard() {
        for (int i = 0; i < diamondCards.size(); i++) {
            if (x >= width) {
                y += 30;
                x = 10;
            }

            ImageView image = findViewById(i + 1);

            image.animate()
                    .translationX(x)
                    .translationY(y)
                    .setStartDelay(100L * i)
                    .setDuration(300)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
            x += 150;
        }
    }
}