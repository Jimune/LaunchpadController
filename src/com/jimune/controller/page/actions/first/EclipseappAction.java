package com.jimune.controller.page.actions.first;

import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Program;
import com.jimune.controller.util.Utils;

public class EclipseappAction extends LaunchAction {

    Program prog;

    public EclipseappAction() {
        super(1, 0, KeyColor.ORANGE_HIGH);

        prog = new Program("U:\\Codes\\eclipse Luna\\eclipse.exe");
    }

    @Override
    public void press() {
        Logger.debug("Attempting to toggle program...");
        prog.toggleProgram();

        if (LaunchPageHandler.curPage.getAction(1, 1) != null) return;

        if (LaunchPageHandler.curPage.addAction(new LaunchAction(1, 1, KeyColor.GREEN_HIGH) {

            @Override
            public void press() {
                Utils.printClassVars(prog.getClass(), prog);
            }

            @Override
            public void release() {

            }

        })) {
            Logger.debug("Added new key to current page!");
        } else {
            Logger.warn("Failed to add new key to current page!");
        }
    }

    @Override
    public void release() {

    }
}
