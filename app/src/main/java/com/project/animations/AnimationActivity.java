package com.project.animations;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class AnimationActivity extends AppCompatActivity {

    Button resetBtn;
    RelativeLayout container;
    ImageView image;
    float x = 10f;
    float y = 0f;
    LinkedList<ImageView> cards;
    Handler handler;
    int height, width;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        resetBtn = findViewById(R.id.resetBtn);
        container = findViewById(R.id.container);

        cards = new LinkedList<>();
        handler = new Handler();
        mediaPlayer = MediaPlayer.create(this, R.raw.card_shuffle);

        // reset button
        resetBtn.setOnClickListener(v -> {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        });

        // getting device height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // set those in variables
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        Random random = new Random();

        Class<R.drawable> drawableClass = R.drawable.class;
        Field[] fields = drawableClass.getFields();

        System.out.println("==> " + Arrays.toString(fields));
        System.out.println("==> " + fields.length);

        for (int i = 0; i < 30; i++) {
            int randomInt = random.nextInt(fields.length);
            image = new ImageView(this);
            RelativeLayout.LayoutParams imgParam = new RelativeLayout.LayoutParams(100, 150);

            try {
                image.setImageResource(fields[randomInt].getInt(drawableClass));
//            image.setImageResource(R.drawable.kh);
            cards.add(image);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            image.setX((float) width / 2);
            image.setY(height);
            image.setLayoutParams(imgParam);

            container.addView(image);
        }
        animateCard();
    }

    public void animateCard() {
        for (int i = 0; i < cards.size(); i++) {
            int finalI = i;
            handler.postDelayed(() ->
                            animate(cards.get(finalI))
                    , 100L * i);
        }
    }

    public void animate(ImageView image) {
        mediaPlayer.start();
        if (x >= width) {
            y += 30;
            x = 10;
        }
        image.animate()
                .translationX(x)
                .translationY(y)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        x += 200;
    }
}