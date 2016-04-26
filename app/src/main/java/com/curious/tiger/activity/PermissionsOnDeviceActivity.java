package com.curious.tiger.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PermissionsOnDeviceActivity extends BaseActivity {


    final static boolean IsNewerThanHONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
