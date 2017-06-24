package com.jimune.controller.page.actions.cp;

import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Popup;
import com.jimune.controller.util.Utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CopyPasteV extends LaunchAction {

    int col;

    public CopyPasteV(int column) {
        super(column, 5, KeyColor.GREEN_MID);

        this.col = column;
    }

    @Override
    public void press() {
        try {
            Transferable trans = SharedVars.system_Clipboard[col];

            if (trans != null) {
                if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);

                    if (text.length() > 0) {
                        Object[] parts = text.split("\n");
                        new Popup(Utils.builder("CopyPaste column ", col), parts).show();
                    } else {
                        new Popup(Utils.builder("Error column ", col), "Nothing is set to this CopyPaste point!").show();
                    }
                } else {
                    new Popup(Utils.builder("Error column ", col), "Unsupported data type or empty clipboard!").show();
                }
            } else {
                new Popup(Utils.builder("Error column ", col), "Nothing is set to this CopyPaste point!").show();
            }
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {

    }
}
