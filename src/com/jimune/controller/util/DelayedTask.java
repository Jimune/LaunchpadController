package com.jimune.controller.util;

public class DelayedTask extends Thread {

    private int[] keys;
    private long delay, duration;

    public DelayedTask(long delay, long duration, int... keys) {
        this.keys = keys;
        this.delay = delay;
        this.duration = duration;

        this.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i : keys) {
            Utils.keyClick(i, duration);
        }
    }
}
