package com.project.animations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    Button btn5, btn6, btn7, btn8;
    Button btn9, btn10, btn11, btn12;
    Button btn13, btn14, btn15, btn16;
    Button btn17, btn18, btn19, btn20;
    Button btn21, btn22, btn23, btn24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
        btn21 = findViewById(R.id.btn21);

        btn1.setOnClickListener(v -> startActivity(new Intent(this, SpadesAnimation.class)));
        btn2.setOnClickListener(v -> startActivity(new Intent(this, HeartAnimation.class)));
        btn3.setOnClickListener(v -> startActivity(new Intent(this, DiamondAnimation.class)));
        btn4.setOnClickListener(v -> startActivity(new Intent(this, ClubAnimation.class)));
        btn5.setOnClickListener(v -> startActivity(new Intent(this, CircleAnimation.class)));
        btn6.setOnClickListener(v -> startActivity(new Intent(this, SemiCircleAnimation.class)));
        btn7.setOnClickListener(v -> startActivity(new Intent(this, WaveAnimation.class)));
        btn8.setOnClickListener(v -> startActivity(new Intent(this, TwoCircleAnimation.class)));
        btn9.setOnClickListener(v -> startActivity(new Intent(this, EightCircleAnimation.class)));
        btn10.setOnClickListener(v -> startActivity(new Intent(this, TestingClass.class)));
        btn11.setOnClickListener(v -> startActivity(new Intent(this, SmileAnimation.class)));
        btn12.setOnClickListener(v -> startActivity(new Intent(this, SwordAnimation.class)));
        btn13.setOnClickListener(v -> startActivity(new Intent(this, CastleAnimation.class)));
        btn14.setOnClickListener(v -> startActivity(new Intent(this, InOutAnimation.class)));
        btn15.setOnClickListener(v -> startActivity(new Intent(this, SmileTwoAnimation.class)));
        btn16.setOnClickListener(v -> startActivity(new Intent(this, SpiderAnimation.class)));
        btn17.setOnClickListener(v -> startActivity(new Intent(this, SpiderTwoAnimation.class)));
        btn18.setOnClickListener(v -> startActivity(new Intent(this, FourCirclesAnimation.class)));
        btn19.setOnClickListener(v -> startActivity(new Intent(this, ScreenRectangleAnimation.class)));
        btn20.setOnClickListener(v -> startActivity(new Intent(this, TriangleAnimation.class)));
        btn21.setOnClickListener(v -> startActivity(new Intent(this, PopCardAnimation.class)));
    }
}