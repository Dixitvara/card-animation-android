package com.project.animations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    Button btn5, btn6, btn7, btn8;
    Button btn9, btn10, btn11, btn12;

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
    }
}