package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SensorPanel extends JPanel {

    private JLabel name;
    public JLabel status;
    private JLabel connection;
    private JPanel mainPanel;
    public JButton powerOffButton;
    private Peripheral peripheral;
    private BaseStation station;

    /**
     * The a simple JPanel to represent the sensors
     *
     * */
    public SensorPanel(String name, String status, String connection, Peripheral peripheral, BaseStation station){
        this.peripheral = peripheral;
        this.station= station;
        this.name.setText(name);
        this.status.setText(status);
        this.connection.setText(connection);
        add(mainPanel);
        setupActions();

    }
    /**
     * A method to set up all the action listeners for the sensor panels
     * */
    public void setupActions(){
        powerOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(powerOffButton.getText().equals("Power Off")) {
                    powerOffButton.setText("Power On");
                    powerOffButton.setBackground(new Color(179,241,157));
                    peripheral.setDeviceState(DeviceState.Off);
                    peripheral.buttonPress();
                }
                else{
                    powerOffButton.setText("Power Off");
                    powerOffButton.setBackground(new Color(255,0,9));
                    peripheral.buttonPress();
                    peripheral.setDeviceState(DeviceState.Home);
                    station.register(peripheral);
                }
            }
        });
    }

    //This is for testing purposes
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SensorPanel("Test","test","test", new SmokeSensor(), new BaseStation()));
        frame.setVisible(true);
    }

}
