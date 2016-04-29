package com.curious.tiger.data;

import android.content.Context;
import android.content.pm.PermissionGroupInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class MPermissionGroup {

    public PermissionGroupInfo mGroupInfo;

    public List<MPermission> mPermissionList = new ArrayList<>();

    public Drawable mGroupIcon;

    private CharSequence mName;

    private CharSequence mDescription;


    public CharSequence getName() {
        return mName;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void extract(Context context) {
        mGroupIcon = mGroupInfo.loadIcon(context.getPackageManager());
        mName = mGroupInfo.loadDescription(context.getPackageManager());
        mDescription = mGroupInfo.name;

    }
}
