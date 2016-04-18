package com.curious.tiger.utils;

import com.curious.support.logger.Log;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lulala on 18/4/16.
 */
public class CmdUtils {
    final static String TAG = "CmdUtils";

    public static String exec(final String command) {
        Process mProcess = null;
        try {
            mProcess = Runtime.getRuntime().exec(command);
            InputStreamReader reader = new InputStreamReader(mProcess.getInputStream());
            StringBuilder outBuffer = new StringBuilder();

            int count;
            char[] buff = new char[1024];
            while ((count = reader.read(buff)) != -1) {
                outBuffer.append(buff, 0, count);
            }

            reader.close();
            mProcess.waitFor();

            return outBuffer.toString();

        } catch (IOException e) {
            Log.e(TAG, "exec()->" + command, e);
        } catch (InterruptedException e) {
            Log.e(TAG, "exec()->" + command, e);
        } finally {
            if (mProcess != null) {
                mProcess.destroy();
            }
        }

        return "";
    }

}
