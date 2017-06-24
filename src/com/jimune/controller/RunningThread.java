package com.jimune.controller;

import com.jimune.controller.page.actions.LaunchQueue;
import com.jimune.controller.util.LaunchQueued;

import java.util.HashSet;
import java.util.Set;

public class RunningThread extends Thread {

    boolean running;
    Set<LaunchQueued> queue = new HashSet<LaunchQueued>();

    public RunningThread(long timeout) {
        this.running = false;
        SharedVars.runnable_timeout = timeout;
    }

    public void start() {
        running = true;
        super.start();
    }

    @SuppressWarnings("deprecation")
    public void end() {
        running = false;
        super.stop();
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(SharedVars.runnable_timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //long now = System.currentTimeMillis();

            for (LaunchQueued queue : queue) {
                queue.getLaunchAction().action();
                //queue.getLaunchAction().held(now - queue.getTimeAdded());
            }
        }
    }

    public void addAction(String identifier, LaunchQueue action) {
        if (getQueue(identifier) == null) {
            queue.add(new LaunchQueued(identifier, action, System.currentTimeMillis()));
        }
    }

    public void removeAction(String identifier) {
        LaunchQueued queued = getQueue(identifier);

        if (queued != null) {
            queue.remove(queued);
        }
    }

    public LaunchQueued getQueue(String id) {
        for (LaunchQueued queued : queue) {
            if (queued.getID().equals(id)) {
                return queued;
            }
        }

        return null;
    }

    public void increaseTimeOut(int time) {
        if (SharedVars.runnable_timeout + time > Constants.MAX_TIMEOUT) {
            SharedVars.runnable_timeout = Constants.MAX_TIMEOUT;
        } else {
            SharedVars.runnable_timeout += time;
        }
    }

    public void decreaseTimeOut(int time) {
        if (SharedVars.runnable_timeout - time < Constants.MIN_TIMEOUT) {
            SharedVars.runnable_timeout = Constants.MIN_TIMEOUT;
        } else {
            SharedVars.runnable_timeout -= time;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
