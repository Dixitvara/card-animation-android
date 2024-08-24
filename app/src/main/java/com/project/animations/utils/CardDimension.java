package com.project.animations.utils;

import android.util.DisplayMetrics;

public class CardDimension {

    public static float aspectRatio = 2f / 3f;

    public static int[] getCardParams(DisplayMetrics displayMetrics) {
        int screenWidth = displayMetrics.widthPixels;

        int cardWidth = (int) (screenWidth * 0.11);
        int cardHeight = (int) (cardWidth / aspectRatio);

        return new int[]{cardWidth, cardHeight};
    }
}
