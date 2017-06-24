package com.jimune.controller.page.actions.settings;

import com.jimune.controller.Constants;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Utils;

public class RunnableUp extends LaunchAction {

    public RunnableUp() {
        super(0, 6, KeyColor.GREEN_HIGH);
    }

    @Override
    public void press() {
        if (SharedVars.runnable_timeout + SharedVars.timeout_modifier > Constants.MAX_TIMEOUT) {
            SharedVars.runnable_timeout = Constants.MAX_TIMEOUT;
            SharedVars.ModifierBar.flash(3, 100, KeyColor.ORANGE_HIGH, KeyColor.YELLOW_HIGH);
        } else {
            SharedVars.runnable_timeout += SharedVars.timeout_modifier;
        }
        Logger.debug("New timeout: " + SharedVars.runnable_timeout);

        int diff = Constants.MAX_TIMEOUT - Constants.MIN_TIMEOUT;
        double percent = ((SharedVars.runnable_timeout - Constants.MIN_TIMEOUT + 0.0D) / diff) * 100.0D;
        double step = 100.0D / SharedVars.ModifierBar.getLength();
        double blocks = Math.floor(percent / step);
        Logger.debug(Utils.builder(diff, " - ", percent, " - ", step, " - ", blocks, " - ", SharedVars.ModifierBar.used()));
        double change = SharedVars.ModifierBar.used() - blocks;
        Logger.debug(change);

        if (-change > 0) {
            for (int i = 0; i < -change; i++) {
                SharedVars.ModifierBar.add();
            }
        } else if (-change < 0) {
            for (int i = SharedVars.ModifierBar.used(); i > blocks; i--) {
                SharedVars.ModifierBar.remove();
            }
        }
    }

    @Override
    public void release() {
    }

}
