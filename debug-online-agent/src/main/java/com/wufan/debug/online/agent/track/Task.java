package com.wufan.debug.online.agent.track;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Task implements Delayed {

    private String uuid;
    private long outTime;

    private long delayTime;

    public Task(String uuid, long outTime) {
        this.outTime = outTime;
        delayTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(outTime, TimeUnit.SECONDS);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    public String getUuid() {
        return uuid;
    }
}
