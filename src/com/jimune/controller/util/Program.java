package com.jimune.controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Program extends Thread {

    ProcessBuilder processBuilder;
    Process process;
    String name;

    boolean activate = false;
    boolean disable = false;

    public Program(String pathToProgram) {
        this.name = pathToProgram.substring(pathToProgram.lastIndexOf("\\") + 1);
        this.processBuilder = new ProcessBuilder(pathToProgram);
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (activate) {
                activate = false;
                open();
            } else if (disable) {
                disable = false;
                close();
            }
        }
    }

    public void outputData() {
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;

        try {
            while ((line = br.readLine()) != null) {
                Logger.info(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleProgram() {
        if (process != null) {
            disable = true;
        } else {
            activate = true;
        }
    }

    private void open() {
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (isActive()) {
            process.destroy();
            process = null;
        } else {
            process = null;
            open();
        }
    }

    public boolean isActive() {
        if (process != null) {
            return process.isAlive();
        }

        return false;
    }

    public String getProgramName() {
        return name;
    }

    @Override
    public String toString() {
        return Utils.builder("Process: ", process, " | builder: ", processBuilder, " | name: ", name, " | activate: ", activate, " | disable: ", disable, " | isActive: ", isActive());
    }
}
