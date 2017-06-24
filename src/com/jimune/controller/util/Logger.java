package com.jimune.controller.util;

import com.jimune.controller.Constants;

public class Logger {

    private enum SysoutType {
        RAW(""),
        INFO("[INFO]"),
        WARNING("[WARNING]"),
        SEVERE("[SEVERE]"),
        ERROR("[ERROR]"),
        BROKEN("[BROKEN]"),
        DEBUG("[DEBUG]");

        String prefix;

        SysoutType(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public String toString() {
            return prefix;
        }
    }

    private static void log(SysoutType type, Object... text) {
        for (Object line : text) {
            Screen.write(Utils.builder(type.toString(), " ", String.valueOf(line), "\n"));
        }
    }

    public static void info(Object... text) {
        log(SysoutType.INFO, text);
    }

    public static void err(Object... text) {
        log(SysoutType.ERROR, text);
    }

    public static void warn(Object... text) {
        log(SysoutType.WARNING, text);
    }

    public static void severe(Object... text) {
        log(SysoutType.SEVERE, text);
    }

    public static void broken(Object... text) {
        log(SysoutType.BROKEN, text);
    }

    public static void debug(Object... text) {
        if (Constants.DEBUG) {
            log(SysoutType.DEBUG, text);
        }
    }

}
