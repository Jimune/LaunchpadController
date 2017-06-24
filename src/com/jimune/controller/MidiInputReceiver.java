package com.jimune.controller;

import com.jimune.controller.event.LaunchpadEvent;
import com.jimune.controller.util.KeyState;
import com.jimune.controller.util.Logger;
import com.jimune.controller.util.Utils;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

public class MidiInputReceiver implements Receiver {

    public MidiDevice dev;
    public Main m;

    public MidiInputReceiver(Main m, MidiDevice dev) {
        this.dev = dev;
        this.m = m;

        try {
            dev.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(MidiMessage msg, long timeStamp) {
        byte[] message = msg.getMessage();
        byte cmd = message[0];
        byte value = message[1];

        Logger.debug(cmd + "||" + value + "||" + message[2] + "||" + timeStamp);

        if (message[2] == 127) { // Key is pressed
            m.handler.sendEvent(new LaunchpadEvent(cmd, value, KeyState.PRESSED));
        } else if (message[2] == 0) { // Key is released
            m.handler.sendEvent(new LaunchpadEvent(cmd, value, KeyState.RELEASED));
        }
    }

    @Override
    public void close() {
    }

    @Override
    public String toString() {
        return Utils.builder(dev.toString(), " | ", dev.isOpen(), " | ", dev.getDeviceInfo());
    }

}
