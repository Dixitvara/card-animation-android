package com.project.animations.utils;

import android.util.DisplayMetrics;

public class CardDimension {

    public static int[] getCardParams(DisplayMetrics displayMetrics) {
        int screenWidth = displayMetrics.widthPixels;

        int cardWidth = (int) (screenWidth * 0.1);
        int cardHeight = (int) (cardWidth * 1.4);

        return new int[]{cardWidth, cardHeight};
    }
}
