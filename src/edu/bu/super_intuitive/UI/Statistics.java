/**
 * @Author Hanyu Chen
 * @Description // Class for statistics
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statistics implements ActionListener {
    private final JFrame frame;
    private JButton button;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JLabel label4;
    private final JLabel label5;
    private final JLabel label6;
    private final JPanel panel;

    // Constructor
    public Statistics() {
        frame = new JFrame("Statistics");
        label1 = new JLabel("Mean: ");
        label2 = new JLabel("Medium: ");
        label3 = new JLabel("Standard deviation: ");
        label4 = new JLabel("70");
        label5 = new JLabel("80");
        label6 = new JLabel("9");
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(3, 2));
        panel.add(label1);
        panel.add(label4);
        panel.add(label2);
        panel.add(label5);
        panel.add(label3);
        panel.add(label6);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Add action listener
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
