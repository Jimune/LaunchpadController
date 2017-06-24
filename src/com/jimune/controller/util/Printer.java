package com.jimune.controller.util;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class Printer implements ClipboardOwner {

    private Clipboard c;

    public Printer() {
        c = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void pureSend(String message) {
        if (Constants.ROBOT != null) {
            StringSelection ss = new StringSelection(message);
            c.setContents(ss, this);

            Constants.ROBOT.keyPress(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
            Constants.ROBOT.keyPress(KeyEvent.VK_V);
            Constants.ROBOT.keyRelease(KeyEvent.VK_V);
            Constants.ROBOT.keyRelease(Main.isMac ? KeyEvent.VK_META : KeyEvent.VK_CONTROL);
            Constants.ROBOT.keyPress(KeyEvent.VK_ENTER);
            Constants.ROBOT.keyRelease(KeyEvent.VK_ENTER);
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

}