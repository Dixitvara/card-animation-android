package com.project.animations;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView randomAnimationCv, stackedCardAnimationCv;
    CardView triangleAnimation, bouncingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomAnimationCv = findViewById(R.id.randomCardAnimationCV);
        stackedCardAnimationCv = findViewById(R.id.stackedCardAnimationCV);
        triangleAnimation = findViewById(R.id.triangleAnimation);
        bouncingAnimation = findViewById(R.id.bouncingAnimation);

        stackedCardAnimationCv.setOnClickListener(v -> {
            startActivity(new Intent(this, AnimationActivity.class));
        });

        randomAnimationCv.setOnClickListener(v -> {
            startActivity(new Intent(this, CircleAnimation.class));
        });

        triangleAnimation.setOnClickListener(v -> {
            startActivity(new Intent(this, TriangleAnimation.class));
        });

        bouncingAnimation.setOnClickListener(v -> {
            startActivity(new Intent(this, BouncingAnimation.class));
        });
    }
}