package com.project.animations.utils;

import android.util.DisplayMetrics;

public class CardDimension {

    public static int[] getCardParams(DisplayMetrics displayMetrics) {
        int screenWidth = displayMetrics.widthPixels;

        int cardWidth = screenWidth * 3 / 30;
        int cardHeight = (int) (cardWidth * 1.4);

        return new int[]{cardWidth, cardHeight};
    }

    public static int[] smallCardsParams(DisplayMetrics displayMetrics) {
        int screenWidth = displayMetrics.widthPixels;

        int cardWidth = screenWidth * 3 / 40;
        int cardHeight = (int) (cardWidth * 1.4);

        return new int[]{cardWidth, cardHeight};
    }

    public static int[] bigCardsParams(DisplayMetrics displayMetrics) {
        int screenWidth = displayMetrics.widthPixels;

        int cardWidth = (int) (screenWidth * 0.15);
        int cardHeight = (int) (cardWidth * 1.4);

        return new int[]{cardWidth, cardHeight};
    }
}
