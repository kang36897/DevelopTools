package com.curious.support.utils;

import android.app.PendingIntent;
import android.os.SystemClock;

/**
 * Created by Administrator on 2016/5/26.
 */
public class AlarmConfig {
    public final static long DEFAULT_INTERVAL_MILLIS = 5 * 60 * 1000;


    private PendingIntent mOperation;

    private boolean isExact = false;

    private int type;

    private long triggerAtMillis;

    private long intervalMillis = DEFAULT_INTERVAL_MILLIS;

    public AlarmConfig() {
        triggerAtMillis = SystemClock.elapsedRealtime() + DEFAULT_INTERVAL_MILLIS;
    }


    public PendingIntent getOperation() {
        return mOperation;
    }

    public AlarmConfig setOperation(PendingIntent mOperation) {
        this.mOperation = mOperation;

        return this;
    }

    public boolean isExact() {
        return isExact;
    }

    public AlarmConfig setExact(boolean exact) {
        isExact = exact;
        return this;
    }

    public int getType() {
        return type;
    }

    public AlarmConfig setType(int type) {
        this.type = type;
        return this;
    }

    public long getTriggerAtMillis() {
        return triggerAtMillis;
    }

    public AlarmConfig setTriggerAtMillis(long triggerAtMillis) {
        this.triggerAtMillis = triggerAtMillis;
        return this;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public AlarmConfig setIntervalMillis(long intervalMillis) {
        this.intervalMillis = intervalMillis;
        return this;
    }
}
