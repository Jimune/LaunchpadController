package com.jimune.controller.util;

public enum KeyState {

    PRESSED((byte) 127),
    RELEASED((byte) 0);

    byte state;

    KeyState(byte state) {
        this.state = state;
    }

    public byte getState() {
        return state;
    }

    public KeyState valueOf(byte state) {
        for (KeyState ks : KeyState.values()) {
            if (ks.getState() == state) {
                return ks;
            }
        }

        return null;
    }

}
