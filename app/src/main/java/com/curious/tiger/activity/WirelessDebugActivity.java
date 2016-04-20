package com.curious.tiger.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.curious.support.data.NetworkState;
import com.curious.support.utils.StateUtils;
import com.curious.tiger.R;

/**
 * Created by lulala on 20/4/16.
 */
public class WirelessDebugActivity extends BaseActivity {

    private TextView mIpBoard;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wireless_debug);

        mIpBoard = (TextView) findViewById(R.id.ip_board);

        NetworkState nState = StateUtils.updateNetworkState(this);
        if (nState.mIsWifiConnected) {
            mIpBoard.setText(getString(R.string.current_ip, StateUtils.getDeviceIP(true)));
        }

    }
}
