package com.jimune.controller;

import com.jimune.controller.util.Bar;
import com.jimune.controller.util.KeyColor;

import java.awt.datatransfer.Transferable;
import java.io.File;

public class SharedVars {

    public static int mc_Speed = 1;
    public static Transferable[] system_Clipboard = new Transferable[32];
    public static File[] file_Dirs = new File[8];
    public static boolean settings_open = false;
    public static long runnable_timeout = 10;
    public static int timeout_modifier = 10;

    public static Bar ModifierBar = new Bar(0, 6, 2, KeyColor.YELLOW_HIGH);
    public static Bar TimeoutBar = new Bar(1, 6, 2, KeyColor.YELLOW_HIGH);

}
