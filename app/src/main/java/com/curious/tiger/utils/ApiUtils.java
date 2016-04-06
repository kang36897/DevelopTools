package com.curious.tiger.utils;

import android.os.Build;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ApiUtils {
    public final static boolean IS_NEWER_THAN_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
}
