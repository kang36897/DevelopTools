package com.curious.tiger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.curious.tiger.R;
import com.curious.tiger.data.MPermissionGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class PermissionExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<MPermissionGroup> mPermissionGroups;
    private LayoutInflater mInflater;

    public PermissionExpandableListAdapter(Context context, List<MPermissionGroup> mData) {
        mContext = context;
        mPermissionGroups = mData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return mPermissionGroups == null ? 0 : mPermissionGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mPermissionGroups.get(groupPosition).mPermissionList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mPermissionGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mPermissionGroups.get(groupPosition).mPermissionList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition << 32 | childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.component_permission_group, parent, false);
        } else {

        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void swapData(List<MPermissionGroup> data) {
        mPermissionGroups = data;
        notifyDataSetChanged();

    }


}
