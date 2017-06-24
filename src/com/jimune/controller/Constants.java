package com.jimune.controller;

import com.jimune.controller.util.Logger;

import java.awt.*;
import java.util.Random;

public class Constants {

    public static Robot ROBOT;
    public static final Random RANDOM = new Random();
    public static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    public static final boolean DEBUG = false;
    public static final int TIMEOUT_MODIFIER_MODIFIER = 10;
    public static final int MAX_TIMEOUT = 10000;
    public static final int MIN_TIMEOUT = 10;
    public static final int MAX_TIMEOUT_MODIFIER = 200;
    public static final int MIN_TIMEOUT_MODIFIER = 10;
    public static final int SPACER = 17;

    static {
        try {
            ROBOT = new Robot();
            ROBOT.setAutoDelay(10);
        } catch (AWTException e) {
            Logger.severe("The system did not allow for a low level control to be created.",
                    " This means you will not be able to parse animated text!");
            e.printStackTrace();
        }
    }
}
