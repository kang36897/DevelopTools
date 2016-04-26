package com.curious.tiger.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.curious.tiger.data.MPermission;
import com.curious.tiger.loader.PermissionLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PermissionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MPermission>> {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(MPermission.ID_AVAILABLE_PERMISSION, null, this);
    }


    @Override
    public Loader<List<MPermission>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<MPermission>> loader, List<MPermission> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<MPermission>> loader) {

    }
}
