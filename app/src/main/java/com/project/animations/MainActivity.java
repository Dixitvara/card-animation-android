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
    Button btn5, btn6;

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

        btn1.setOnClickListener(v -> startActivity(new Intent(this, SpadesAnimation.class)));
        btn2.setOnClickListener(v -> startActivity(new Intent(this, HeartAnimation.class)));
        btn3.setOnClickListener(v -> startActivity(new Intent(this, DiamondAnimation.class)));
        btn4.setOnClickListener(v -> startActivity(new Intent(this, ClubAnimation.class)));
        btn5.setOnClickListener(v -> startActivity(new Intent(this, CircleAnimation.class)));
        btn6.setOnClickListener(v -> startActivity(new Intent(this, SemiCircleAnimation.class)));
    }
}