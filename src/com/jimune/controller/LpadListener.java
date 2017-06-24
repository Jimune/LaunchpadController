package com.jimune.controller;

import com.jimune.controller.event.LaunchpadEvent;
import com.jimune.controller.event.LaunchpadListener;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPage;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.Screen;
import com.jimune.controller.util.Utils;

public class LpadListener implements LaunchpadListener {

    private boolean pause = false;

    @Override
    public void keyPressedEvent(LaunchpadEvent event) {
        int key = event.getKey();

        if (!pause) {
            if (event.getCommand() == -80) { //Top bar buttons
                if (event.keyIsPressed()) {
                    if (key == 104) { //Top bar 1st button -- Open / Close settings
                        if (SharedVars.settings_open) {
                            Utils.setPadFromPage(LaunchPageHandler.curPage);
                            SharedVars.settings_open = false;
                        } else {
                            Utils.setPadFromPage(LaunchPageHandler.settings);
                            SharedVars.settings_open = true;

                            SharedVars.ModifierBar.update();
                            SharedVars.TimeoutBar.update();
                        }
                    } else if (key == 105) { //Top bar 2nd button -- Open / Close console
                        Screen.toggleVisible();
                    } else if (key == 106) { //Top bar 3rd button -- Previous page
                        LaunchPageHandler.previousPage();
                    } else if (key == 107) { //Top bar 4th button -- Next page
                        LaunchPageHandler.nextPage();
                    } else if (key == 110) { //Top bar user2 button -- Pause
                        pause = true;
                        Utils.resetPad();
                        Screen.setVisible(false);
                    } else if (key == 111) { //Top bar last button -- Close program
                        Main.instance.stop(false);
                        System.exit(0);
                    }
                }
                return;
            } else if (event.getCommand() == -112) { //Any other button
                LaunchPage page;

                if (SharedVars.settings_open) {
                    page = LaunchPageHandler.settings;
                } else {
                    page = LaunchPageHandler.curPage;
                }

                if (page != null && page.thisPage > 0) {
                    LaunchAction action = page.getAction(key);

                    if (action != null) {
                        if (event.keyIsPressed()) {
                            action.press();
                        } else {
                            action.release();
                        }
                    }
                }
            }
        } else if (pause) {
            if (event.getCommand() == -80) { //Top bar buttons
                if (event.keyIsPressed()) {
                    if (key == 110) { //Top bar user1 button -- Resume
                        pause = false;
                        Utils.setPadFromPage(LaunchPageHandler.curPage);
                    } else if (key == 111) { //Top bar last button -- Close program
                        Main.instance.stop(false);
                        System.exit(0);
                    }
                }
            }
        }
    }
}
