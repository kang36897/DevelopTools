package com.curious.tiger.activity;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.curious.tiger.R;

import java.util.List;

public class BuiltInSensorsActivity extends BaseActivity {

    private SensorManager mSensorManager;
    private List<Sensor> mData;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_sensors);
        mListView = (ListView) findViewById(R.id.listView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorDetailAdapter adapter = new SensorDetailAdapter();
        mListView.setAdapter(adapter);

        mData = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        adapter.notifyDataSetChanged();
    }

    class SensorDetailAdapter extends BaseAdapter {

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

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        android.R.layout.simple_list_item_1, null);
            }

            TextView detailView = (TextView) convertView;

            Sensor sensor = mData.get(position);
            detailView.setText(sensor.toString());

            return detailView;
        }

    }
}
