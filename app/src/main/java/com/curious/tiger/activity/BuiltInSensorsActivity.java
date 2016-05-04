package com.curious.tiger.activity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;

import com.curious.tiger.R;
import com.curious.tiger.adapter.SensorDetailAdapter;

public class BuiltInSensorsActivity extends BaseActivity {

    private SensorManager mSensorManager;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in_sensors);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorDetailAdapter adapter = new SensorDetailAdapter(this,
                mSensorManager.getSensorList(Sensor.TYPE_ALL));
        mListView.setAdapter(adapter);
    }

}
