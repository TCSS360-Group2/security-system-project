package com.company;

import javax.swing.*;
import java.awt.*;

public class SensorPanel extends JPanel {

    private JLabel name;
    public JLabel status;
    private JLabel connection;
    private JPanel mainPanel;

    /**
     * The a simple JPanel to represent the sensors
     *
     * */
    public SensorPanel(String name, String status, String connection){
        this.name.setText(name);
        this.status.setText(status);
        this.connection.setText(connection);
        add(mainPanel);


    }

    //This is for testing purposes
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SensorPanel("Test","test","test"));
        frame.setVisible(true);
    }

}
