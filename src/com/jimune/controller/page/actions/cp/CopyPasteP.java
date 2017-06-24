package com.jimune.controller.page.actions.cp;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;

import java.awt.event.KeyEvent;

public class CopyPasteP extends LaunchAction {

    int col;

    public CopyPasteP(int column) {
        super(column, 6, KeyColor.RED_HIGH);

        this.col = column;
    }

    @Override
    public void press() {
        if (SharedVars.system_Clipboard[col] != null) {
            Constants.TOOLKIT.getSystemClipboard().setContents(SharedVars.system_Clipboard[col], null);

            if (Constants.ROBOT != null) {
                Constants.ROBOT.keyPress(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
                Constants.ROBOT.keyPress(KeyEvent.VK_V);
                Constants.ROBOT.keyRelease(KeyEvent.VK_V);
                Constants.ROBOT.keyRelease(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
            }
        }
    }

    @Override
    public void release() {

    }

}
