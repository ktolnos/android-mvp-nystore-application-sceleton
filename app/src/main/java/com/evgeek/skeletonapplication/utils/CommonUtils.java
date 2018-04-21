package com.evgeek.skeletonapplication.utils;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeek on 10/14/17.
 */

public class CommonUtils {

    public static <E> List<E> union(List<? extends E> a, List<? extends E> b) {
        List<E> es = new ArrayList<E>();
        es.addAll(a);
        es.addAll(b);
        return es;
    }

    @Nullable
    public static Resources getResources() {
        return null;
    }


    public static boolean checkStringsEqual(@Nullable String s1, @Nullable String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        return s1.equals(s2);
    }
}
