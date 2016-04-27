package com.curious.tiger.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.curious.tiger.fragment.PermissionFragment;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PermissionsOnDeviceActivity extends BaseActivity {


    final static boolean IsNewerThanHONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (IsNewerThanHONEYCOMB) {
            if (savedInstanceState == null) {
                PermissionFragment fragment = new PermissionFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(android.R.id.content, fragment).commit();
            }
        } else {

            //TODO do the old fashion way
        }
    }
}
