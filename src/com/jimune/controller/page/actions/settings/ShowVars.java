package com.jimune.controller.page.actions.settings;

import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Utils;

public class ShowVars extends LaunchAction {

    public ShowVars() {
        super(7, 7, KeyColor.ORANGE_HIGH);
    }

    @Override
    public void press() {
        Utils.printClassVars(SharedVars.class, null);
    }

    @Override
    public void release() {
    }

}
