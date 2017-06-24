package com.jimune.controller.page.actions.speed;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.actions.LaunchQueue;
import com.jimune.controller.util.KeyColor;

import java.awt.event.InputEvent;

public class RightClickSpeedAction extends LaunchAction implements LaunchQueue {

    public RightClickSpeedAction() {
        super(2, 0, KeyColor.GREEN_HIGH);
    }

    @Override
    public void press() {
        Main.queue.addAction("RightClickSpeed", this);
    }

    @Override
    public void release() {
        Main.queue.removeAction("RightClickSpeed");
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
        //Redundant for its this class' purpose
    }

}
