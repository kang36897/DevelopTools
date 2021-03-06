package com.curious.tiger.adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.curious.tiger.R;
import com.curious.tiger.activity.BuiltInSensorsActivity;
import com.curious.tiger.utils.SensorHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class SensorDetailAdapter extends BaseAdapter {
    private List<Sensor> mData;
    private LayoutInflater mInflater;

    public SensorDetailAdapter(Context context, List<Sensor> sensors) {
        mInflater = LayoutInflater.from(context);
        mData = sensors;
    }

    public void swap(List<Sensor> sensors) {
        mData = sensors;
    }


    @Override
    public int getCount() {
        if (mData == null)
            return 0;

        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SensorDetailHolder holder;
        if (convertView == null) {
            holder = new SensorDetailHolder();
            convertView = mInflater.inflate(R.layout.component_sensor_detail, parent, false);
            convertView.setTag(holder);

            holder.mNameView = (TextView) convertView.findViewById(R.id.name);
            holder.mVendorView = (TextView) convertView.findViewById(R.id.vendor);
            holder.mTypeView = (TextView) convertView.findViewById(R.id.type);
            holder.mVersionView = (TextView) convertView.findViewById(R.id.version);

            holder.mResolutionView = (TextView) convertView.findViewById(R.id.resolution);
            holder.mPowerView = (TextView) convertView.findViewById(R.id.power);
            holder.mMaxRangeView = (TextView) convertView.findViewById(R.id.max_range);
            holder.mMinDelayView = (TextView) convertView.findViewById(R.id.min_delay);

        } else {
            holder = (SensorDetailHolder) convertView.getTag();
        }


        Sensor s = mData.get(position);

        holder.mNameView.setText(s.getName());
        holder.mVendorView.setText(s.getVendor());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            holder.mTypeView.setText(s.getStringType());
        } else {
            holder.mTypeView.setText(SensorHelper.getStringType(s.getType()));
        }

        holder.mVersionView.setText(String.valueOf(s.getVersion()));


        holder.mResolutionView.setText(String.valueOf(s.getResolution()));
        holder.mPowerView.setText(String.valueOf(s.getPower()));
        holder.mMaxRangeView.setText(String.valueOf(s.getMaximumRange()));
        holder.mMinDelayView.setText(String.valueOf(s.getMinDelay()));


        return convertView;
    }


    static class SensorDetailHolder {
        TextView mNameView;
        TextView mVendorView;
        TextView mVersionView;
        TextView mTypeView;

        TextView mResolutionView;
        TextView mPowerView;
        TextView mMaxRangeView;
        TextView mMinDelayView;
    }
}
