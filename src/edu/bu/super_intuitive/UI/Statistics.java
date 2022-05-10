package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statistics implements ActionListener {
    private JFrame frame;
    private JButton button;
    private JLabel label1, label2, label3, label4, label5, label6;
    private JPanel panel;

    public Statistics() {
        frame = new JFrame("Statistics");
        button = new JButton("Back");
        label1 = new JLabel("Mean: ");
        label2 = new JLabel("Medium: ");
        label3 = new JLabel("Standard deviation: ");
        label4 = new JLabel("70");
        label5 = new JLabel("80");
        label6 = new JLabel("9");
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(3, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener(this);
        panel.add(label1);
        panel.add(label4);
        panel.add(label2);
        panel.add(label5);
        panel.add(label3);
        panel.add(label6);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CourseView frame = null;
        try {
            frame = new CourseView(null);
        } catch (OperationFailed ex) {
            throw new RuntimeException(ex);
        }
        frame.setVisible(true);
        this.frame.dispose();
    }
}
