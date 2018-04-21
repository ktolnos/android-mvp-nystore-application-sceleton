package com.evgeek.skeletonapplication.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SystemUtils {
    public static int getScreenOrientation() {
        return Resources.getSystem().getConfiguration().orientation;
    }

    public static boolean isTablet() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        return diagonalInches >= 6.5;
    }

    public static float screenWidthDP() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float xDP = metrics.widthPixels / metrics.density;
        return xDP;
    }
}
