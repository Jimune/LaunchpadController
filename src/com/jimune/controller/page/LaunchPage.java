package com.jimune.controller.page;

import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Utils;

import java.util.ArrayList;

public class LaunchPage {

    public int thisPage;
    public ArrayList<LaunchAction> actions = new ArrayList<LaunchAction>();

    public LaunchPage(LaunchAction... action) {
        for (LaunchAction a : action) {
            if (getAction(a.key) == null) {
                actions.add(a);
            } else {
                Logger.err("LaunchAction at key " + a.key + " for LaunchPage " + this.thisPage + " already exists!");
            }
        }
    }

    public boolean addAction(LaunchAction action) {
        Logger.debug("Adding new action to current page...");
        LaunchAction a2 = getAction(action.key);

        if (a2 == null) {
            Logger.debug("Key not in use yet!");
            actions.add(action);
            Utils.refreshPage();
            Logger.debug("Added key!");
            return true;
        } else {
            Logger.err("LaunchAction at key " + action.key + " for LaunchPage " + this.thisPage + " already exists!");
        }

        return false;
    }

    public LaunchAction getAction(int x, int y) {
        return getAction((y * 16) + x);
    }

    public LaunchAction getAction(int key) {
        for (LaunchAction action : actions) {
            if (key == action.key) {
                return action;
            }
        }

        return null;
    }

    public boolean removeAction(int x, int y) {
        return removeAction((y * 16 + x));
    }

    public boolean removeAction(int key) {
        LaunchAction tempAction = null;
        for (LaunchAction action : actions) {
            if (key == action.key) {
                tempAction = action;
            }
        }

        if (tempAction != null) {
            return actions.remove(tempAction);
        }

        return false;
    }
}
