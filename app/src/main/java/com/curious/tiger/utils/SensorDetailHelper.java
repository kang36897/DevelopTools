package com.curious.tiger.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.Spec;
import android.view.View;
import android.widget.TextView;

import com.curious.tiger.data.MSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public class SensorDetailHelper {

    public static View inflate(Context context, int ColumnCount, int rowCount,
                               SensorDetailViewHolder holder) {
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setUseDefaultMargins(true);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(ColumnCount);
        gridLayout.setRowCount(rowCount);


        for (int i = 0; i < rowCount; i += 1) {

            Spec keyRowSpec = GridLayout.spec(i, GridLayout.RIGHT);
            Spec keyColumnSpec = GridLayout.spec(0, GridLayout.CENTER);

            TextView keyView = new TextView(context);
            holder.mKeyView.add(keyView);
            gridLayout.addView(keyView,
                    new GridLayout.LayoutParams(keyRowSpec, keyColumnSpec));


            Spec valueRowSpec = GridLayout.spec(i, GridLayout.FILL);
            Spec valueColumnSpec = GridLayout.spec(1, GridLayout.CENTER);

            TextView valueView = new TextView(context);
            holder.mValueView.add(valueView);
            gridLayout.addView(valueView,
                    new GridLayout.LayoutParams(valueRowSpec, valueColumnSpec));

        }

        gridLayout.setTag(holder);

        return gridLayout;
    }


    public static void populate(SensorDetailViewHolder holder, MSensor detail) {
        if (detail.mProperties.isEmpty()) {
            return;
        }


        int i = 0;

        for (Map.Entry<String, String> item : detail.mProperties.entrySet()) {
            holder.mKeyView.get(i).setText(item.getKey());
            holder.mValueView.get(i).setText(item.getValue());
            i++;
        }

        if (holder.mKeyView.size() == detail.mProperties.size()) {
            return;
        }


        for (; i < holder.mKeyView.size(); i++) {
            holder.mKeyView.get(i).setText(null);
            holder.mValueView.get(i).setText(null);
        }


    }


    public static MSensor extract(Sensor sensor) {
        String description = sensor.toString();
        String[] properties = description.split(",");
        MSensor temp = new MSensor();

        if (properties.length > MSensor.MAX_PROPERTIES_COUNT) {
            MSensor.MAX_PROPERTIES_COUNT = properties.length;
        }

        for (int i = 0; i < properties.length; i++) {

            String[] entry = properties[i].split("=");
            String key = entry[0].replace("[\\{\\}]", "").trim();
            String value;
            if (entry.length > 1) {
                value = entry[1].replaceAll("\"", "").trim();
            } else {
                value = "";
            }
            temp.mProperties.put(key, value);
        }
        return temp;

    }


    public static List<MSensor> convert(List<Sensor> originData) {

        List<MSensor> sensors = new ArrayList<>();
        for (Sensor s : originData) {
            sensors.add(extract(s));
        }

        return sensors;
    }


    public static class SensorDetailViewHolder {
        public List<TextView> mKeyView = new ArrayList<>();
        public List<TextView> mValueView = new ArrayList<>();
    }

}
