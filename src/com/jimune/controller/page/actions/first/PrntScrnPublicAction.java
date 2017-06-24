package com.jimune.controller.page.actions.first;

import com.jimune.controller.Constants;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;

import java.awt.event.KeyEvent;

public class PrntScrnPublicAction extends LaunchAction {

    public PrntScrnPublicAction() {
        super(8, 7, KeyColor.YELLOW_HIGH);
    }

    @Override
    public void press() {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.keyPress(KeyEvent.VK_SHIFT);
            Constants.ROBOT.keyPress(KeyEvent.VK_PRINTSCREEN);
            Constants.ROBOT.keyRelease(KeyEvent.VK_SHIFT);
            Constants.ROBOT.keyRelease(KeyEvent.VK_PRINTSCREEN);
        }
    }

    @Override
    public void release() {

    }

}
