package com.curious.tiger.loader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.support.v4.content.AsyncTaskLoader;

import com.curious.support.logger.Log;
import com.curious.tiger.data.MPermission;
import com.curious.tiger.data.MPermissionGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PermissionLoader extends AsyncTaskLoader<List<MPermissionGroup>> {

    private static final String TAG = "PermissionLoader";
    private PackageManager mPm;
    private List<MPermissionGroup> mData;

    public PermissionLoader(Context context) {
        super(context);
        mPm = getContext().getPackageManager();
    }

    @Override
    public List<MPermissionGroup> loadInBackground() {
        List<MPermissionGroup> mTempData = new ArrayList<>();
        List<PermissionGroupInfo> groupInfos = mPm.getAllPermissionGroups(PackageManager.GET_META_DATA);

        for (PermissionGroupInfo info : groupInfos) {
            MPermissionGroup group = new MPermissionGroup();
            group.mGroupInfo = info;
            //TODO assign a icon to indicate the usage of this group

            List<PermissionInfo> permissionInfoList = null;
            try {
                permissionInfoList = mPm.queryPermissionsByGroup(info.name, PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "loadInBackground()->", e);
            }


            if (permissionInfoList == null || permissionInfoList.isEmpty()) {
                continue;
            }

            for (PermissionInfo pInfo : permissionInfoList) {
                MPermission p = new MPermission();
                p.mPermissionInfo = pInfo;
                //TODO assign a icon to indicate the protection level


                group.mPermissionList.add(p);
            }

            mTempData.add(group);

        }


        return mTempData;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }


    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
