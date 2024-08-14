package com.project.animations;

import static values.values.CARD_HEIGHT;
import static values.values.CARD_WIDTH;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TriangleAnimation extends AppCompatActivity {

    int screenWidth, screenHeight;
    final int TOTAL_CARDS = 12;
    Button resetBtn;
    ArrayList<Integer> cardList;
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        cardList = new ArrayList<>();

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // generating cards
        generateCards();

    }

    private void generateCards() {
        int resourceId;
        float cardXCenter = (float) CARD_WIDTH / 2;
        float cardYCenter = (float) CARD_HEIGHT / 2;
        int x, y;
        x = y = 0;

        for (int i = 1; i <= TOTAL_CARDS; i++) {
            if (i <= 13)
                resourceId = this.getResources().getIdentifier("s" + i, "drawable", this.getPackageName());
            else
                resourceId = this.getResources().getIdentifier("d" + (i - 13), "drawable", this.getPackageName());
            cardList.add(resourceId);

            ImageView image = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(CARD_WIDTH, CARD_HEIGHT);

            image.setId(i);
            image.setImageResource(resourceId);
            image.setLayoutParams(params);

            if (x != 0 && y != 0) {
                image.setX(x += 25);
                image.setY(y += 25);
//                image.setRotation(35);
            } else {
                image.setX((float) screenWidth / 2 - cardXCenter);
                x = (int) image.getX();
                image.setY((float) screenHeight / 4 - cardYCenter);
                y = (int) image.getY();
            }
            System.out.println("==> x :" + x);
            System.out.println("==> y :" + y);

//            image.setX((float) screenWidth / 2 - (float) CARD_WIDTH / 2);
//            image.setY(screenHeight);

            container.addView(image);

        }
    }
}