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
            group.extract(getContext());

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
                p.extract(getContext());

                group.mPermissionList.add(p);
            }

            mTempData.add(group);

        }


        return mTempData;
    }


    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        //TODO register some listen for data change event

        if (takeContentChanged() || mData == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }


    @Override
    public void onCanceled(List<MPermissionGroup> data) {
        super.onCanceled(data);
        onReleaseResources(data);
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<MPermissionGroup> data) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }

    @Override
    protected void onReset() {
        super.onReset();


        // Ensure the loader is stopped
        onStopLoading();


        if (mData != null) {
            onReleaseResources(mData);
            mData = null;
        }

        //TODO unregister the listen sensitive to data change event


    }

    @Override
    public void deliverResult(List<MPermissionGroup> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }

        List<MPermissionGroup> oldData = mData;
        mData = data;


        if (isStarted()) {
            super.deliverResult(data);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldData != null) {
            onReleaseResources(oldData);
        }
    }
}
