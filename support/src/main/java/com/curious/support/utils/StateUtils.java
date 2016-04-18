package com.curious.support.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    public static String getIpAddress(boolean needIpv4) {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface face = interfaces.nextElement();
                Enumeration<InetAddress> addresses = face.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet4Address) {
                            if (needIpv4)
                                return address.getHostAddress();
                            else {
                                continue;
                            }
                        } else {

                            if (needIpv4) {
                                continue;
                            } else {
                                return address.getHostAddress();
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
