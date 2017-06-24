package com.jimune.controller.page;

import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Utils;

public abstract class LaunchAction {

    public int key;
    public KeyColor color;

    public LaunchAction(int key, KeyColor color) {
        this.key = key;
        this.color = color;
    }

    public LaunchAction(int x, int y, KeyColor color) {
        this((y * 16) + x, color);
    }

    public abstract void press();

    public abstract void release();

    public void setColor(KeyColor color) {
        this.color = color;

        Utils.setPadKeyColor(this.key, this.color);
    }
}
