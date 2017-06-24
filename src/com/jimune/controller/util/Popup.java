package com.jimune.controller.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Popup implements ActionListener {

    private JFrame frame = null;
    private JButton okButton = null;
    private Vector<Component> displayComponents = null;
    private int width = 0;

    public Popup(String title, Object... display) {
        this.displayComponents = new Vector<Component>();
        this.frame = new JFrame(title);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setActionCommand("ok");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 5;
        c.gridx = 0;
        c.gridy = 0;

        for (Object o : display) {
            if (o instanceof String) { // TODO Add other data types such as images
                String s = (String) o;
                displayComponents.add(new JLabel(s));
                if (s.length() * 8 > width) width = s.length() * 8;
            }
        }

        frame.setSize(width + 20, 16 * display.length + 96);

        for (Component comp : displayComponents) {
            panel.add(comp, c);
            c.gridy++;
        }

        c.fill = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy++;
        c.gridwidth = 1;
        c.gridx = 3;

        panel.add(okButton, c);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("ok")) {
            frame.dispose();
        }
    }

}
