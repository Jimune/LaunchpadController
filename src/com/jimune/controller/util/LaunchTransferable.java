package com.jimune.controller.util;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class LaunchTransferable implements Transferable {

    Clipboard cb;

    public LaunchTransferable(Clipboard selection) {
        this.cb = selection;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        for (DataFlavor df : cb.getAvailableDataFlavors()) {
            if (df.equals(flavor)) {
                return this.cb;
            }
        }

        return null;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return cb.getAvailableDataFlavors();
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (DataFlavor df : cb.getAvailableDataFlavors()) {
            if (df.equals(flavor)) {
                return true;
            }
        }

        return false;
    }

}
