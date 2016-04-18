package com.curious.tiger.utils;

import com.curious.support.logger.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lulala on 18/4/16.
 */
public class CmdUtils {
    final static String TAG = "CmdUtils";

    // Executes UNIX command.
    public static String exec(final String command) {
        Process mProcess = null;
        try {
            mProcess = Runtime.getRuntime().exec(command);
            InputStreamReader reader = new InputStreamReader(mProcess.getInputStream());
            StringBuilder outBuffer = new StringBuilder();

            int count;
            char[] buff = new char[1024];
            while ((count = reader.read(buff)) > 0) {
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


    public synchronized static void logError(InputStream errorStream) {
        byte[] buff = new byte[512];
        int count = 0;
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {

            while ((count = errorStream.read(buff)) != -1) {
                arrayOutputStream.write(buff, 0, count);
            }
            arrayOutputStream.flush();
            if (arrayOutputStream.toByteArray().length > 0)
                Log.d(TAG, arrayOutputStream.toString());
            arrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "logError()->", e);
        } finally {
            if (errorStream != null) {
                try {
                    errorStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "logError()->", e);
                }
            }
        }
    }
}
