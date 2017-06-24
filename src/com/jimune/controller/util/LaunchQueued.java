package com.jimune.controller.util;

import com.jimune.controller.page.actions.LaunchQueue;

public class LaunchQueued {

    String id;
    LaunchQueue queue;
    long addTime;

    public LaunchQueued(String id, LaunchQueue queue, long addTime) {
        this.id = id;
        this.queue = queue;
        this.addTime = addTime;
    }

    public String getID() {
        return id;
    }

    public LaunchQueue getLaunchAction() {
        return queue;
    }

    public long getTimeAdded() {
        return addTime;
    }
}
