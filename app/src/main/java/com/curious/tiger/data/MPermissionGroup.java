package com.curious.tiger.data;

import android.content.pm.PermissionGroupInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class MPermissionGroup {

    public PermissionGroupInfo mGroupInfo;

    public List<MPermission> mPermissionList = new ArrayList<>();

    public int mGroupIcon;
}
