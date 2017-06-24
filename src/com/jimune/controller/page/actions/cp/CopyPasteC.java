package com.jimune.controller.page.actions.cp;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.KeyColor;

import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;

public class CopyPasteC extends LaunchAction {

    int col;

    public CopyPasteC(int column) {
        super(column, 7, KeyColor.ORANGE_MID);
        this.col = column;
    }

    @Override
    public void press() {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.keyPress(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
            Constants.ROBOT.keyPress(KeyEvent.VK_C);
            Constants.ROBOT.keyRelease(KeyEvent.VK_C);
            Constants.ROBOT.keyRelease(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
        }

        Clipboard cb = Constants.TOOLKIT.getSystemClipboard();

        if (cb != null) {
            SharedVars.system_Clipboard[col] = cb.getContents(null);
            super.setColor(KeyColor.ORANGE_HIGH);
            LaunchPageHandler.curPage.getAction(col, 6).setColor(KeyColor.GREEN_HIGH);
        }
    }

    @Override
    public void release() {

    }
}
