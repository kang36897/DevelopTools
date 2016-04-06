package com.curious.tiger.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListShowCaseActivity extends BaseActivity {

    private ListView mListView;
    private ActivityInfo[] mActivityInfoArray;
    private String[] mItemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);
        setContentView(mListView);

        PackageManager pm = getPackageManager();
        try {
            String packageName = getApplicationInfo().packageName;
            PackageInfo packageInfo = pm.getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);

            ActivityInfo[] tempActivityInfoArray = packageInfo.activities;
            if (tempActivityInfoArray == null) {
                finish();
                return;
            }

            int size = tempActivityInfoArray.length - 1;
            int index = 0;
            mItemData = new String[size];
            mActivityInfoArray = new ActivityInfo[size];
            for (int i = 0; i < tempActivityInfoArray.length; i++) {
                if (tempActivityInfoArray[i].name.contains(getLocalClassName())) {
                    continue;
                }

                mActivityInfoArray[index] = tempActivityInfoArray[i];
                int resId = tempActivityInfoArray[i].labelRes;

                if (resId == 0) {
                    mItemData[index] = tempActivityInfoArray[i].name;
                    index++;
                    continue;
                }

                mItemData[index] = getResources().getString(resId);
                index++;
            }

            mListView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, mItemData));
            mListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    ActivityInfo activityInfo = mActivityInfoArray[position];
                    Intent intent = new Intent();
                    intent.setClassName(activityInfo.packageName,
                            activityInfo.name);

                    startActivity(intent);
                }
            });

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
