package com.curious.support.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.curious.support.data.NetworkState;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2016/3/16.
 */
public class StateUtils {

    public static boolean isInternetConnectionAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mActiveNetwork = manager.getActiveNetworkInfo();
        return mActiveNetwork.isConnectedOrConnecting();
    }

    public static boolean isInternetAccessible(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mActiveNetwork = manager.getActiveNetworkInfo();

        return mActiveNetwork != null && mActiveNetwork.isConnected();
    }


    public static NetworkState updateNetworkState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mActiveNetwork = manager.getActiveNetworkInfo();
        NetworkState state = new NetworkState();
        if (mActiveNetwork == null) {
            state.mIsMobileConnected = false;
            state.mIsWifiConnected = false;
        } else {
            state.mIsMobileConnected = mActiveNetwork.isConnected()
                    && mActiveNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            state.mIsWifiConnected = mActiveNetwork.isConnected()
                    && mActiveNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        }
        return state;
    }


    public static String getDeviceIP(boolean needIPv4) {
        try {
            Enumeration<NetworkInterface> availableInterface = NetworkInterface.getNetworkInterfaces();
            while (availableInterface.hasMoreElements()) {
                NetworkInterface currentInterface = availableInterface.nextElement();

                Enumeration<InetAddress> availableAddresses = currentInterface.getInetAddresses();

                while (availableAddresses.hasMoreElements()) {
                    InetAddress inetAddress = availableAddresses.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {

                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = inetAddress instanceof Inet4Address;

                        if (isIPv4) {
                            if (needIPv4) {
                                return hostAddress;
                            }
                        } else {
                            if (!needIPv4) {
                                int delim = hostAddress.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, delim).toUpperCase();
                            }


                        }


                    }

                }

            }


        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }
}
