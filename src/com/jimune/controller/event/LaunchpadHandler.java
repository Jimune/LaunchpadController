package com.jimune.controller.event;

import java.util.ArrayList;

public class LaunchpadHandler {

    ArrayList<LaunchpadListener> listeners = new ArrayList<LaunchpadListener>();

    public void addListener(LaunchpadListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void sendEvent(LaunchpadEvent event) {
        for (LaunchpadListener lst : listeners) {
            lst.keyPressedEvent(event);
        }
    }
}
