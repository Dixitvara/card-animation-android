package com.project.animations;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.animations.models.CardModel;
import com.project.animations.utils.CardMethods;

import java.util.ArrayList;

public class ClubAnimation extends AppCompatActivity {

    RelativeLayout container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        container = findViewById(R.id.container);

        ArrayList<CardModel> cardList = CardMethods.generateCards(1, 0);

        for(int i = 0; i < cardList.size(); i++){

        }
    }
}
