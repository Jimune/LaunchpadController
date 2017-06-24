package com.jimune.controller.page.actions.speed;

import com.jimune.controller.Main;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.actions.LaunchQueue;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Utils;

import java.awt.event.KeyEvent;

public class ShiftSpamAction extends LaunchAction implements LaunchQueue {

    private boolean active = false;

    public ShiftSpamAction() {
        super(0, 0, KeyColor.ORANGE_HIGH);
    }

    @Override
    public void press() {
        if (!active) {
            Main.queue.addAction("twerk", this);
            active = true;
        } else if (active) {
            Main.queue.removeAction("twerk");
            active = false;
        }
    }

    @Override
    public void release() {

    }

    @Override
    public void action() {
        Utils.keyClick(KeyEvent.VK_SHIFT, 50);
    }

    @Override
    public void held(long ms_duration) {

    }

}
