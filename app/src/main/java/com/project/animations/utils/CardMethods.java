package com.project.animations.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.animations.models.CardModel;

import java.util.ArrayList;

public class CardMethods {

    static int[] cardDimension = CardDimension.getCardParams(new DisplayMetrics());
    public static int cardWidth = cardDimension[0];
    public static int cardHeight = cardDimension[1];

    public static RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth, cardHeight);

    private static final String[] suites = {"clubs", "diamonds", "spades", "hearts"};

    public static ArrayList<CardModel> generateCards(int cardSize, int index) {
        ArrayList<CardModel> cardList = new ArrayList<>();

        for (int i = 0; i < suites.length; i++) {
            String currentSuite = suites[index];
            String color = "black";

            if (index == 1 || index == 3)
                color = "red";

            for (int j = 1; j <= 13; j++) {
                CardModel card = new CardModel(currentSuite, color, j);
                cardList.add(card);
                if (cardList.size() == cardSize) return cardList;
            }
        }
        return cardList;
    }

    public static ArrayList<CardModel> allCards() {
        ArrayList<CardModel> cardList = new ArrayList<>();

        for (int i = 0; i < suites.length; i++) {
            String color = i == 1 || i == 3 ? "red" : "black";

            for (int j = 1; j <= 13; j++) {
                CardModel card = new CardModel(suites[i], color, j);
                cardList.add(card);
            }
        }
        return cardList;
    }

    public static ArrayList<CardModel> generateSelectedCards(int cardSize, int[] index) {
        ArrayList<CardModel> cardList = new ArrayList<>();

        for (int i = 0; i < suites.length; i++) {
            String currentSuite = suites[index[i]];
            String color = index[i] == 1 || index[i] == 3 ? "red" : "black";

            for (int j = 1; j <= 13; j++) {
                CardModel card = new CardModel(currentSuite, color, j);
                cardList.add(card);
                if (cardList.size() == cardSize) return cardList;
            }
        }
        return cardList;
    }

    public static ArrayList<View> allCardsImages(Context context) {
        ArrayList<View> cardList = new ArrayList<>();

        for (int i = 0; i < suites.length; i++) {
            String color = i == 1 || i == 3 ? "red" : "black";

            for (int j = 1; j <= 13; j++) {
                ImageView image = new ImageView(context);
                CardModel card = new CardModel(suites[i], color, j);

                image.setImageResource(card.getResourceId(context));
                image.setLayoutParams(params);

                cardList.add(image);
            }
        }
        return cardList;
    }
}