package com.jimune.controller.page.actions.speed;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.actions.LaunchQueue;
import com.jimune.controller.util.KeyColor;

import java.awt.event.InputEvent;

public class RightClickHoldAction extends LaunchAction implements LaunchQueue {

    boolean held = false;

    public RightClickHoldAction() {
        super(3, 0, KeyColor.GREEN_HIGH);
    }

    @Override
    public void press() {
        if (!held) {
            Main.queue.addAction("RightClickSpeed", this);
            held = true;
        } else if (held) {
            Main.queue.removeAction("RightClickSpeed");
            held = false;
        }
    }

    @Override
    public void release() {
    }

    @Override
    public void action() {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            Constants.ROBOT.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    @Override
    public void held(long ms_duration) {
    }
}