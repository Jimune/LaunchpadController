package com.jimune.controller.util;

import com.jimune.controller.Constants;
import com.jimune.controller.Main;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPage;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.page.actions.cp.CopyPasteC;
import com.jimune.controller.page.actions.cp.CopyPasteP;
import com.jimune.controller.page.actions.cp.CopyPasteR;
import com.jimune.controller.page.actions.cp.CopyPasteV;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class Utils {

    private static long time = 0L;

    public static void keyClick(int key, long delay) {
        if (Constants.ROBOT != null) {
            Constants.ROBOT.keyPress(key);

            try {
                Thread.currentThread();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Constants.ROBOT.keyRelease(key);
        }
    }

    /**
     * cmd:</br>
     * 128, k, v = off</br>
     * 144, k, v = on</br>
     * 176, 0, 0 = reset</br>
     * 192, 0, m = set mode</br>
     */
    public static void tellPad(int cmd, int key, int velocity) {
        if (key < 0) {
            Logger.err("Tried to send data with key less than 0");
            return;
        }

        if (Main.out != null) {
            ShortMessage temp = null;

            try {
                temp = new ShortMessage(cmd, key, velocity);
            } catch (InvalidMidiDataException e1) {
                e1.printStackTrace();

                return;
            }

            Main.out.send(temp, time++);
        } else {
            if (Main.startup) {
                if (Main.attempt > 3) {
                    Logger.severe("Failed to start 3 times, shutting down in 5 seconds!");

                    try {
                        Thread.currentThread();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.exit(0);
                }

                Logger.warn("Output stream is null. Attempting to restart the program in 10 seconds!");

                try {
                    Thread.currentThread();
                    Thread.sleep(10000);
                    Logger.info("Restarting... Attempt " + Main.attempt);
                    Main.attempt++;
                    Main.instance.stop(true);
                    Main.instance.start();
                } catch (Exception e) {
                    Logger.severe("Failed to restart the program!");
                    e.printStackTrace();
                }
            } else {
                Logger.warn("Output stream is null.");
            }
        }
    }

    public static void refreshPage() {
        resetPad();
        setPadFromPage(LaunchPageHandler.curPage);
    }

    public static void resetPad() {
        tellPad(176, 0, 0);
    }

    public static void setPadKeyColor(int x, int y, KeyColor color) {
        tellPad(144, (y * 16) + x, color.getCode());
    }

    public static void setPadKeyColor(int key, KeyColor color) {
        tellPad(144, key, color.getCode());
    }

    public static void setPadFromPage(LaunchPage page) {
        resetPad();

        for (LaunchAction action : page.actions) {
            setPadKeyColor(action.key, action.color);
        }
    }

    public static Integer getSpecialKeyCode(String character) {
        switch (character) {
            case "l":
                return KeyEvent.VK_LEFT;
            case "r":
                return KeyEvent.VK_RIGHT;
            case "u":
                return KeyEvent.VK_UP;
            case "d":
                return KeyEvent.VK_DOWN;

            default:
                break;
        }

        return -1;
    }

    public static Integer getKeyCode(String character) {
        switch (character) {
            case " ":
                return KeyEvent.VK_SPACE;
            case "!":
                return KeyEvent.VK_EXCLAMATION_MARK;
            case ",":
                return KeyEvent.VK_COMMA;
            case ".":
                return KeyEvent.VK_PERIOD;
            case ";":
                return KeyEvent.VK_SEMICOLON;
            case ":":
                return KeyEvent.VK_COLON;
            case "@":
                return KeyEvent.VK_AT;

            default:
                break;
        }

        for (Field f : KeyEvent.class.getFields()) {
            String f_name = f.getName();

            if (f_name.startsWith("VK_")) {
                f_name = f_name.replaceFirst("VK_", "");

                if (f_name.equalsIgnoreCase(character)) {
                    try {
                        return f.getInt(KeyEvent.class);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return -1;
    }

    public static int[] strToKeyCodeArray(String line) {
        char[] chars = line.toCharArray();
        int[] temp = new int[chars.length];
        int[] temp2 = null;
        boolean special = false;
        int offset = 0, total = 0;

        for (int i = 0; i < chars.length; i++) {
            if (special) {
                temp[i - offset] = getSpecialKeyCode(chars[i] + "");
                total++;
                special = false;
            }

            if (chars[i] == '?') {
                special = true;
                offset++;
            } else {
                temp[i - offset] = getKeyCode(chars[i] + "");
                total++;
            }
        }

        temp2 = new int[total];

        for (int i = 0; i < total; i++) {
            temp2[i] = temp[i];
        }

        return temp2;
    }

    public static void pressKeyArray(int[] keys) {
        for (int i : keys) {
            keyClick(i, 10);
            try {
                Thread.currentThread();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String builder(Object... parts) {
        StringBuilder sb = new StringBuilder();

        for (Object o : parts) {
            sb.append(o);
        }

        return sb.toString();
    }

    public static double getPercent(double max, double min, double current) {
        Logger.debug((current / (max - min)) * 100);
        return (current / (max - min)) * 100;
    }

    public static int[] getYFromPercent(int range, int start_from_bottom, double percent) {
        int[] temp = new int[range];

        for (int i = 0; i < range; i++) {
            temp[i] = -1;
        }

        double steps = 100 / range;
        Logger.debug(steps);
        int returnamount = (int) Math.floor(percent / steps);
        Logger.debug(returnamount);

        for (int i = 0; i < returnamount; i++) {
            temp[i] = 7 - start_from_bottom - i;
            Logger.debug(temp[i]);
        }

        return temp;
    }

    public static void printClassVars(Class<?> clazz, Object ref) {
        try {
            boolean noaccess = false;
            for (Field f : clazz.getDeclaredFields()) {
                if (!f.isAccessible()) {
                    noaccess = true;
                    f.setAccessible(true);
                }

                Logger.debug(f);
                String splitter = "";

                for (int i = 0; i < Constants.SPACER - f.getName().length(); i++) {
                    splitter += " ";
                }

                Logger.debug(f.getType());

                if (ref != null) {
                    Logger.info(Utils.builder(f.getName(), splitter + ": ", f.get(ref)));
                } else {
                    Logger.info(Utils.builder(f.getName(), splitter + ": ", f.get(clazz)));
                }


                if (noaccess) {
                    f.setAccessible(false);
                    noaccess = false;
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void addCopyPaste(int page, int column) {
        LaunchPage lp = LaunchPageHandler.getPage(page);

        if (lp != null) {
            lp.addAction(new CopyPasteC(column));
            lp.addAction(new CopyPasteP(column));
            lp.addAction(new CopyPasteR(column));
            lp.addAction(new CopyPasteV(column));
        } else {
            Logger.warn(Utils.builder("Tried to add CopyPaste Function to non-existing page ", page, ", to column ", column, "!"));
        }
    }
}
