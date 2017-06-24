package com.jimune.controller.util;

import com.jimune.controller.Constants;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

public class Macro implements Runnable {

    private String macroName;
    private List<MousePoint> mousePoints;
    private int at;
    private boolean running;

    public Macro(String name) {
        macroName = name;
        mousePoints = new ArrayList<MousePoint>();
        at = 0;
        running = false;
    }

    public boolean addMousePoint(MousePoint mp) {
        if (!mousePoints.contains(mp)) {
            mousePoints.add(mp);
            return true;
        }

        return false;
    }

    public void activate() {
        if (!running) {
            at = 0;
            running = true;
            new Thread(this).run();
        }
    }

    @Override
    public void run() {
        while (running) {
            if (at >= mousePoints.size()) break;

            MousePoint mp = mousePoints.get(at);
            Constants.ROBOT.mouseMove(mp.getX(), mp.getY());
            if (mp.leftClick()) Constants.ROBOT.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            if (mp.rightClick()) Constants.ROBOT.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            if (mp.middleClick()) Constants.ROBOT.mousePress(InputEvent.BUTTON3_DOWN_MASK);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                running = false;
                Logger.severe("Something went wrong while sleeping the macro between mouse clicks!", Utils.builder(" Terminating macro" + macroName + "!"));
                e.printStackTrace();
                break;
            }

            if (mp.leftClick()) Constants.ROBOT.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            if (mp.rightClick()) Constants.ROBOT.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
            if (mp.middleClick()) Constants.ROBOT.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                running = false;
                Logger.severe("Something went wrong while sleeping the macro between mouse points!", Utils.builder(" Terminating macro" + macroName + "!"));
                e.printStackTrace();
                break;
            }

            at++;
        }

        running = false;
    }
}
