package com.jimune.controller.page.actions.cp;

import com.jimune.controller.Constants;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.KeyColor;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CopyPasteR extends LaunchAction {

    int col;

    public CopyPasteR(int column) {
        super(column, 4, KeyColor.YELLOW_HIGH);

        this.col = column;
    }

    @Override
    public void press() {
        SharedVars.system_Clipboard[col] = null;
        Constants.TOOLKIT.getSystemClipboard().setContents(new Transferable() {

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                throw new UnsupportedFlavorException(flavor);
            }

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor arg0) {
                return false;
            }

        }, null);

        LaunchPageHandler.curPage.getAction(col, 7).setColor(KeyColor.ORANGE_MID);
        LaunchPageHandler.curPage.getAction(col, 6).setColor(KeyColor.RED_HIGH);
    }

    @Override
    public void release() {

    }

}
