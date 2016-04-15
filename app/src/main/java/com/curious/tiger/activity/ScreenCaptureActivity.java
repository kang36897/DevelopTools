package com.curious.tiger.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.curious.support.logger.Log;
import com.curious.tiger.R;
import com.curious.tiger.utils.ImageUtils;
import com.curious.tiger.utils.StorageUtils;

/**
 * Created by lulala on 14/4/16.
 */
public class ScreenCaptureActivity extends BaseActivity {
    final static String TAG = "ScreenCaptureActivity";
    private Button mCaptureBtn;
    private ImageView mTargetView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_capture);

        mCaptureBtn = (Button) findViewById(R.id.btn_capture);
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int exitValue = ImageUtils.captureScreenByCmd(StorageUtils
                        .getOutputMediaFile(StorageUtils.MEDIA_TYPE_IMAGE,
                                StorageUtils.SCREEN_SHOT_FOLDER));
                Log.d(TAG, "exitValue = " + exitValue);

            }
        });

        mTargetView = (ImageView) findViewById(R.id.target);

    }
}
