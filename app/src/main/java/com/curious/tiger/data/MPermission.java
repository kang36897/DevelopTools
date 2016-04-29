package com.curious.tiger.data;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/4/26.
 */
public class MPermission {
    public PermissionInfo mPermissionInfo;

    private CharSequence mName;

    public CharSequence getName() {
        return mName;
    }

    public void extract(Context context) {
        mName = mPermissionInfo.name;
    }

}
