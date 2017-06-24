package com.jimune.controller.event;

import com.jimune.controller.util.KeyState;

public class LaunchpadEvent {

    private byte command, key;
    private KeyState state;

    public LaunchpadEvent(byte command, byte key, KeyState state) {
        this.command = command;
        this.key = key;
        this.state = state;
    }

    public int getCommand() {
        return command;
    }

    public int getKey() {
        return key;
    }

    public KeyState getState() {
        return state;
    }

    public boolean keyIsPressed() {
        return state == KeyState.PRESSED;
    }
}
