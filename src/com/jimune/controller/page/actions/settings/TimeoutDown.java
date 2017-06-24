package com.jimune.controller.page.actions.settings;

import com.jimune.controller.Constants;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.Bar;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Utils;

public class TimeoutDown extends LaunchAction {

    public TimeoutDown() {
        super(1, 7, KeyColor.RED_HIGH);
        SharedVars.TimeoutBar = new Bar(1, 6, 2, KeyColor.YELLOW_HIGH);
    }

    @Override
    public void press() {
        if (SharedVars.timeout_modifier - Constants.TIMEOUT_MODIFIER_MODIFIER < Constants.MIN_TIMEOUT_MODIFIER) {
            SharedVars.timeout_modifier = Constants.MIN_TIMEOUT_MODIFIER;
            SharedVars.TimeoutBar.flash(3, 100, KeyColor.ORANGE_HIGH, KeyColor.YELLOW_HIGH);
        } else {
            SharedVars.timeout_modifier -= Constants.TIMEOUT_MODIFIER_MODIFIER;
        }
        Logger.debug("New timeout: " + SharedVars.timeout_modifier);

        int diff = Constants.MAX_TIMEOUT_MODIFIER - Constants.MIN_TIMEOUT_MODIFIER;
        double percent = ((SharedVars.timeout_modifier - Constants.MIN_TIMEOUT_MODIFIER + 0.0D) / diff) * 100.0D;
        double step = 100.0D / SharedVars.TimeoutBar.getLength();
        double blocks = Math.floor(percent / step);
        Logger.debug(Utils.builder(diff, " - ", percent, " - ", step, " - ", blocks, " - ", SharedVars.TimeoutBar.used()));
        double change = SharedVars.TimeoutBar.used() - blocks;
        Logger.debug(change);

        if (-change > 0) {
            for (int i = 0; i < -change; i++) {
                SharedVars.TimeoutBar.add();
            }
        } else if (-change < 0) {
            for (int i = SharedVars.TimeoutBar.used(); i > blocks; i--) {
                SharedVars.TimeoutBar.remove();
            }
        }
    }

    @Override
    public void release() {
    }

}
