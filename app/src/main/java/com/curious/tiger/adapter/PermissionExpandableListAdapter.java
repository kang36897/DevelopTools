package com.curious.tiger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.curious.tiger.R;
import com.curious.tiger.data.MPermission;
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
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.component_permission_group, parent, false);
            holder = new GroupViewHolder();
            convertView.setTag(holder);

            holder.mGroupView = convertView;
            holder.mIconView = (ImageView) convertView.findViewById(R.id.group_icon);
            holder.mNameView = (TextView) convertView.findViewById(R.id.group_name);
            holder.mDescriptionView = (TextView) convertView.findViewById(R.id.group_description);
            holder.mExpandableIndicator = (ImageView) convertView.findViewById(R.id.expand_indicator);

        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        MPermissionGroup group = mPermissionGroups.get(groupPosition);

        //TODO set group icon
        if (group.mGroupIcon != null) {
            holder.mIconView.setImageDrawable(group.mGroupIcon);
        }
        holder.mNameView.setText(group.getName());
        holder.mDescriptionView.setText(group.getDescription());

        //TODO set the expand indicator


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        PermissionViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.component_permission, parent, false);
            holder = new PermissionViewHolder();
            convertView.setTag(holder);

            holder.mItemView = convertView;
            holder.mPermissionView = (TextView) convertView.findViewById(R.id.permission_item);

        } else {
            holder = (PermissionViewHolder) convertView.getTag();
        }

        MPermission permission = mPermissionGroups.get(groupPosition).mPermissionList.get(childPosition);

        //TODO set the protect level icon

        holder.mPermissionView.setText(permission.getName());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void swapData(List<MPermissionGroup> data) {
        mPermissionGroups = data;
        notifyDataSetChanged();
    }


    static class GroupViewHolder {
        public View mGroupView;

        public ImageView mIconView;
        public TextView mNameView;
        public TextView mDescriptionView;

        public ImageView mExpandableIndicator;
    }


    static class PermissionViewHolder {
        public View mItemView;

        public TextView mPermissionView;
    }

}
