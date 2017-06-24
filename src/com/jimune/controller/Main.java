package com.jimune.controller;

import com.jimune.controller.event.LaunchpadHandler;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Printer;
import com.jimune.controller.util.Screen;
import com.jimune.controller.util.Utils;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

public class Main {

    public static Receiver out;
    public boolean running = true; // Set true to activate.
    public static Main instance;
    public static RunningThread queue;
    public static boolean startup = false;
    public static int attempt = 1;
    public static boolean isMac = true;

    public LaunchpadHandler handler;
    public MidiDevice device;
    public Printer printer;
    public MidiInputReceiver in;

    public Main() {
        instance = this;
        startup = true;
        String osName = System.getProperty("os.name", "unknown");
        isMac = osName.startsWith("mac") || osName.startsWith("darwin");
        start();
    }

    public void start() {
        Logger.info("Checking for devices...");
        checkDevices();
        Utils.resetPad();
        Logger.info("Connected!");

        if (running) {
            Logger.info("Setting up handler...");

            handler = new LaunchpadHandler();
            handler.addListener(new LpadListener());
            printer = new Printer();

            Logger.info("Loading and setting up pages...");

            LaunchPageHandler.setupSettings();
            loadPages();

            Logger.info("Starting timer...");
            queue = new RunningThread(10L);
            queue.start();

            Screen.enableButtons(); // Activate the buttons on the pad
            startup = false;

            Logger.info("All done!");

            if (Constants.DEBUG) {
                int spacer = 17;

                try {
                    for (Field f : Main.class.getFields()) {
                        String splitter = "";

                        for (int i = 0; i < spacer - f.getName().length(); i++) {
                            splitter += " ";
                        }

                        Logger.info(Utils.builder(f.getName(), splitter + ": ", f.get(this)));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Utils.resetPad();
        }
    }

    public void stop(boolean internal) {
        if (!internal) {
            Utils.resetPad();
            out.close();
            device.close();
        }

        unloadPages();
        handler = null;
        queue = null;
        printer = null;
    }

    private static void loadPages() {
        LaunchPageHandler.createPages();
//        for (LaunchPage lp : LaunchPageHandler.pages) {
//            for (LaunchAction la : lp.actions) {
//                la.postInit();
//            }
//        }
    }

    private static void unloadPages() {
        LaunchPageHandler.destroyPages();
    }

    private void checkDevices() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (int i = 0; i < infos.length; i++) {
            try {
                device = MidiSystem.getMidiDevice(infos[i]);

                if (device.getDeviceInfo().getName().contains("Launchpad S")) {
                    Logger.info(Utils.builder("Located device ", device.getDeviceInfo().getName()));
                    Logger.info("Setting up connection...");
                    List<Transmitter> transmitters = device.getTransmitters();

                    for (int j = 0; j < transmitters.size(); j++) {
                        transmitters.get(j).setReceiver(new MidiInputReceiver(instance, device));
                    }

                    Logger.info("Activating connection...");
                    device.open();

                    if (in == null && device.getTransmitter().getReceiver() == null) {
                        in = new MidiInputReceiver(instance, device);
                        device.getTransmitter().setReceiver(in);
                        Logger.info("Connecting device transmitter to receiver...");
                    } else {
                        Logger.info("Connecting transmitter to device receiver...");
                        out = device.getReceiver();
                    }
                } else {
                    continue;
                }
            } catch (MidiUnavailableException e) {
                try {
                    Logger.info("Connecting transmitter to device receiver...");
                    out = device.getReceiver();
                } catch (MidiUnavailableException e1) {
                    Logger.info("Could not connect to device receiver!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PrintStream stream = new PrintStream(new Screen());
        System.setOut(stream);
        System.setErr(stream);

        new Main();
    }
}
