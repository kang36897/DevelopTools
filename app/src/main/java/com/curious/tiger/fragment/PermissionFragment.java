package com.curious.tiger.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.curious.tiger.R;
import com.curious.tiger.adapter.PermissionExpandableListAdapter;
import com.curious.tiger.data.LoaderConst;
import com.curious.tiger.data.MPermissionGroup;
import com.curious.tiger.loader.PermissionLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class PermissionFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<MPermissionGroup>> {

    private ExpandableListView mGroupList;
    private PermissionExpandableListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View contentView = inflater.inflate(R.layout.component_permission_in_group, container, false);

        mGroupList = (ExpandableListView) container.findViewById(R.id.permission_group);


        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new PermissionExpandableListAdapter(getContext(), null);
        mGroupList.setAdapter(mAdapter);
        getLoaderManager().initLoader(LoaderConst.ID_AVAILABLE_PERMISSION, null, this);
    }


    @Override
    public Loader<List<MPermissionGroup>> onCreateLoader(int id, Bundle args) {
        return new PermissionLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<MPermissionGroup>> loader, List<MPermissionGroup> data) {
        mAdapter.swapData(data);

        if (isResumed()) {
            //TODO show data with animation
        } else {
            //TODO show data without animation
        }

    }

    @Override
    public void onLoaderReset(Loader<List<MPermissionGroup>> loader) {
        mAdapter.swapData(null);
    }
}
