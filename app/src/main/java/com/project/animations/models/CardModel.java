package com.project.animations.models;

import android.content.Context;
import android.content.res.Resources;

public class CardModel {
    String suite;
    String color;
    int number;

    public CardModel(String suite, String color, int number) {
        this.suite = suite;
        this.color = color;
        this.number = number;
    }

    public String getSuite() {
        return suite;
    }

    public int getNumber() {
        return number;
    }

    public int getResourceId(Context context) {
        String resourceName = getSuite().toLowerCase() + getNumber();
        Resources resources = context.getResources();
        return resources.getIdentifier(resourceName, "drawable", context.getPackageName());
    }
}
