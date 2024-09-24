package com.project.animations.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

public class MyAnim {
    public static void translateXTo(ViewGroup layout, float translateTo, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                        layout,
                        "translationX",
                        translateTo
                )
                .setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public static void translateYTo(ViewGroup layout, float translateTo, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                        layout,
                        "translationY",
                        translateTo
                )
                .setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public static ArrayList<View> sortAndDirectCards(ArrayList<View> views, int direction) {
        views.sort((view1, view2) -> {
            int[] location1 = new int[2];
            int[] location2 = new int[2];

            view1.getLocationOnScreen(location1);
            view2.getLocationOnScreen(location2);

            switch (direction) {
                case 1:
                    // top to bottom
                    return Integer.compare(location1[1], location2[1]);
                case 2:
                    // right to left
                    return Integer.compare(location2[0], location1[0]);
                case 3:
                    // bottom to top
                    return Integer.compare(location2[1], location1[1]);
                case 4:
                    // left to right
                    return Integer.compare(location1[0], location2[0]);
            }
            return 0;
        });
        return views;
    }
}
