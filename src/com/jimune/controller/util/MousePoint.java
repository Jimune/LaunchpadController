package com.jimune.controller.util;

public class MousePoint {

    private int x, y;
    private boolean leftClick;
    private boolean rightClick;
    private boolean middleClick;

    public MousePoint(int x, int y, boolean leftClick, boolean rightClick, boolean middleClick) {
        this.x = x;
        this.y = y;
        this.leftClick = leftClick;
        this.rightClick = rightClick;
        this.middleClick = middleClick;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean leftClick() {
        return leftClick;
    }

    public boolean rightClick() {
        return rightClick;
    }

    public boolean middleClick() {
        return middleClick;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MousePoint) {
            MousePoint mp = (MousePoint) obj;

            return mp.x == this.x &&
                    mp.y == this.y &&
                    mp.leftClick == this.leftClick &&
                    mp.rightClick == this.rightClick &&
                    mp.middleClick == this.middleClick;
        }

        return false;
    }

    @Override
    public String toString() {
        return "x:" + x + "|y:" + y + "|Lclick:" + leftClick + "|Rclick:" + rightClick + "|Mclick:" + middleClick;
    }
}
