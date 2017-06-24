package com.jimune.controller.util;

public enum KeyColor {

    OFF(76),
    RED_LOW(65), RED_MID(66), RED_HIGH(67),
    ORANGE_LOWEST(93), ORANGE_LOW(94), ORANGE_MID(111), ORANGE_HIGH(95),
    YELLOW_MID(110), YELLOW_HIGH(63),
    GREEN_LOW(92), GREEN_MID(108), GREEN_HIGH(124),
    LIME_LOW(109), LIME_1(125), LIME_2(126);

    int code;

    KeyColor(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
