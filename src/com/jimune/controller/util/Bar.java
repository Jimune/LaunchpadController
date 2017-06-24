package com.jimune.controller.util;

public class Bar {

    int x;
    int length;
    int bottom_offset;
    KeyColor color;
    int[] y;
    int used = 0;

    public Bar(int x, int length, int bottom_offset, KeyColor color) {
        this.x = x;
        this.length = length;
        this.bottom_offset = bottom_offset;
        this.color = color;
        this.y = new int[length];

        for (int i = 0; i < length; i++) {
            this.y[i] = -1;
        }
    }

    public int getX() {
        return x;
    }

    public int getLength() {
        return length;
    }

    public int used() {
        return used;
    }

    public void add() {
        if (used == length) return;

        for (int i = 0; i < length; i++) {
            if (y[i] < 0) {
                y[i] = 7 - bottom_offset - i;
                used++;
                update();
                return;
            }
        }
    }


    public void remove() {
        if (used == 0) return;

        for (int i = length - 1; i >= 0; i--) {
            if (y[i] >= 0) {
                y[i] = -1;
                used--;
                update();
                return;
            }
        }
    }

    public void update() {
        for (int i = 0; i < length; i++) {
            if (y[i] < 0) {
                Logger.debug(Utils.builder(x, " : ", y[i], " : ", KeyColor.OFF));
                Utils.setPadKeyColor(x, 7 - bottom_offset - i, KeyColor.OFF);
            } else {
                Logger.debug(Utils.builder(x, " : ", y[i], " : ", color));
                Utils.setPadKeyColor(x, y[i], color);
            }
        }
    }

    public void flash(int amount, long off_duration, KeyColor color_on, KeyColor color_off) {
        for (int j = 0; j < amount; j++) {
            for (int i = 0; i < length; i++) {
                Logger.debug(Utils.builder(x, " : ", y[i], " : ", color_off));
                Utils.setPadKeyColor(x, 7 - bottom_offset - i, color_off);
            }

            try {
                Thread.currentThread();
                Thread.sleep(off_duration);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < length; i++) {
                Logger.debug(Utils.builder(x, " : ", y[i], " : ", color_on));
                Utils.setPadKeyColor(x, 7 - bottom_offset - i, color_on);
            }

            try {
                Thread.currentThread();
                Thread.sleep(off_duration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        update();
    }

    @Override
    public String toString() {
        return Utils.builder("x: ", x, "|length: ", length, "|bottom_offset: ", bottom_offset, "|color: ", color, "|used: ", used);
    }
}
