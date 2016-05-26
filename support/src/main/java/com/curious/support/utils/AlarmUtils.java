package com.curious.support.utils;

import android.app.AlarmManager;
import android.content.Context;

/**
 * Created by Administrator on 2016/5/26.
 */
public class AlarmUtils {
    public static void startAlarm(Context context, AlarmConfig config) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (config.isExact()) {
            alarmManager.setRepeating(config.getType(),
                    config.getTriggerAtMillis(),
                    config.getIntervalMillis(),
                    config.getOperation());

        } else {
            alarmManager.setInexactRepeating(config.getType(),
                    config.getTriggerAtMillis(),
                    config.getIntervalMillis(),
                    config.getOperation());
        }

    }


    public static void cancelAlarm(Context  context, AlarmConfig config){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(config.getOperation());
    }


}
