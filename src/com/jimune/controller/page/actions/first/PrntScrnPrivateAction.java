package com.jimune.controller.page.actions.first;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;

import java.awt.event.KeyEvent;

public class PrntScrnPrivateAction extends LaunchAction {

    public PrntScrnPrivateAction() {
        super(8, 6, KeyColor.GREEN_HIGH);
    }

    @Override
    public void press() {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.keyPress(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
            Constants.ROBOT.keyPress(KeyEvent.VK_PRINTSCREEN);
            Constants.ROBOT.keyRelease(KeyEvent.VK_PRINTSCREEN);
            Constants.ROBOT.keyRelease(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
        }
    }

    @Override
    public void release() {

    }

}
