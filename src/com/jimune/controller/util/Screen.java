package com.jimune.controller.util;

import com.jimune.controller.Main;
import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchPageHandler;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

public class Screen extends OutputStream implements ActionListener {

    private static JTextArea ta = null;
    private static JFrame console = null;
    private static JButton[] buttons = new JButton[3];

    public Screen() {
        console = new JFrame("Output");
        console.setSize(1024, 512);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("monospaced", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(ta);
        sp.setAutoscrolls(true);
        ((DefaultCaret) ta.getCaret()).setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        JScrollBar sb = new JScrollBar();
        sb.setAutoscrolls(true);
        sp.setVerticalScrollBar(sb);

        buttons[0] = new JButton("Force Kill");
        buttons[0].addActionListener(this);
        buttons[0].setActionCommand("launch_force_kill");
        buttons[0].setEnabled(false);
        buttons[1] = new JButton("Settings");
        buttons[1].addActionListener(this);
        buttons[1].setActionCommand("launch_settings");
        buttons[1].setEnabled(false);
        buttons[2] = new JButton("Retry Connection");
        buttons[2].addActionListener(this);
        buttons[2].setActionCommand("restart");
        buttons[2].setEnabled(false);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = console.getHeight() - sp.getHeight() - 90;
        c.ipadx = console.getWidth() - sp.getWidth() - 20;

        panel.add(sp, c);

        c.ipadx = 100;
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(buttons[0], c);
        c.gridx = 1;
        panel.add(buttons[1], c);
        c.gridx = 2;
        //panel.add(buttons[2], c);


        console.add(panel);
        console.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        console.setVisible(true);
    }

    public static boolean isVisible() {
        return console.isVisible();
    }

    public static void setVisible(boolean visible) {
        console.setVisible(visible);
    }

    public static void toggleVisible() {
        setVisible(!isVisible());
    }

    public static void enableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(true);
        }
    }

    @Override
    public void write(int b) throws IOException {
        ta.append(String.valueOf((char) b));
        ta.setCaretPosition(ta.getDocument().getLength());
    }

    public static void write(String text) {
        ta.append(text);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();

        if (cmd.equalsIgnoreCase("launch_force_kill")) {
            Main.instance.stop(false);
            System.exit(0);
        } else if (cmd.equalsIgnoreCase("launch_settings")) {
            if (SharedVars.settings_open) {
                Utils.setPadFromPage(LaunchPageHandler.curPage);
                SharedVars.settings_open = false;
            } else {
                Utils.setPadFromPage(LaunchPageHandler.settings);
                SharedVars.settings_open = true;

                SharedVars.ModifierBar.update();
                SharedVars.TimeoutBar.update();
            }
        } else if (cmd.equalsIgnoreCase("restart")) {
            Main.instance.stop(false);
            ta.setText(null);
            Main.instance.start();
            LaunchPageHandler.curPage = LaunchPageHandler.getPage(0);
        }
    }

}
