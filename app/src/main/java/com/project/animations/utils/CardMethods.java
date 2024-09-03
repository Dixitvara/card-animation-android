package com.project.animations.utils;

import com.project.animations.models.CardModel;

import java.util.ArrayList;

public class CardMethods {
    private static final String[] array = {"clubs", "spades", "diamonds", "hearts"};

    public static ArrayList<CardModel> generateCards(int cardSize, int index) {
        ArrayList<CardModel> cardList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            String currentSuite = array[index];
            String color = "black";

            if (index > 1)
                color = "red";

            for (int j = 1; j <= 13; j++) {
                CardModel card = new CardModel(currentSuite, color, j);
                cardList.add(card);
                if (cardList.size() == cardSize) break;
            }
        }
        return cardList;
    }
}
