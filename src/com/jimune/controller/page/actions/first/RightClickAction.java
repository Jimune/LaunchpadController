package com.jimune.controller.page.actions.first;

import com.jimune.controller.Constants;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;

import java.awt.event.InputEvent;

public class RightClickAction extends LaunchAction {

    public RightClickAction() {
        super(8, 5, KeyColor.RED_HIGH);
    }

    @Override
    public void press() {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            Constants.ROBOT.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    @Override
    public void release() {

    }

}
