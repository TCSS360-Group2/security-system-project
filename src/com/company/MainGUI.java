package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame{
    //Main Panels
    private JTabbedPane mainPanel;
    private JPanel panel1;


    //Settings Buttons
    private JButton disarmSystemButton;
    private JButton connectCameraButton;
    private JButton connectToASystemButton;
    private JButton sensorButton;
    private JButton setEmergencyContactInformationButton;
    private JPanel cameras;
    private JPanel burglaryPane;
    private JPanel environmentalPane;
    private JButton systemPowerButton;


    //Base Station
    private BaseStation station;

    public MainGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        setupActions();
    }
    /**
     * Sets up all the action listeners for the various buttons across the GUI
     * */
    public void setupActions(){
        station = new BaseStation();

        //Arm Disarm Listener: Changes button color and text, updates sensor text, eventually will update the system too
        disarmSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(disarmSystemButton.getText() == "Home Mode"){
                    disarmSystemButton.setText("Away Mode");
                    disarmSystemButton.setBackground(new Color(120,24,37));

                    //Change all the sensor values
                    for(Component component: burglaryPane.getComponents()){
                        if(component instanceof SensorPanel){
                            ((SensorPanel)component).status.setText("Status: Disabled");
                        }
                    }

                    //This is where I would tell the base station to arm/disarm the system
                }
                else{
                    disarmSystemButton.setText("Home Mode");
                    disarmSystemButton.setBackground(new Color(179,241,157));

                    //Change all the sensor values
                    for(Component component: burglaryPane.getComponents()){
                        if(component instanceof SensorPanel){
                            ((SensorPanel)component).status.setText("Status: Enabled");
                        }
                    }
                }
                repaint();

            }
        });
        //Connect/Disconnect Listener: Just adds a new sensor, there is no disconnect feature
        sensorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //First Select a sensor to add then select a name for the sensor
                Object[] options = {"Glassbreak", "Motion", "Smoke", "Water", "C02"};
                Object selected = JOptionPane.showInputDialog(panel1, "Choose a sensor", "Menu",JOptionPane.PLAIN_MESSAGE, null, options,options[0]);
                String selectedString = selected.toString();
                String sensorName = JOptionPane.showInputDialog(panel1, "Name your sensor", null);

                //Add the sensor to the status screen
                for(int i = 0; i < options.length; i++){
                    if(selectedString.equals(options[i].toString()) && i < 2){
                        burglaryPane.setLayout(new BoxLayout(burglaryPane, BoxLayout.PAGE_AXIS));
                        burglaryPane.add(new SensorPanel(sensorName,"Status: Enabled", "Connected"));
                        burglaryPane.revalidate();
                    }
                    else{
                        environmentalPane.setLayout(new BoxLayout(environmentalPane, BoxLayout.PAGE_AXIS));
                        environmentalPane.add(new SensorPanel(sensorName,null, "Connected"));
                        environmentalPane.revalidate();
                    }
                    mainPanel.revalidate();
                    repaint();
                    break;
                }


                mainPanel.revalidate();
                repaint();
                //Then register the sensor with the base station

            }
        });

        //Connect/Disconnect camera Listener: Adds a new placeholder for a camera, cannot delete cameras
        connectCameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cameras.setLayout(new BoxLayout(cameras, BoxLayout.PAGE_AXIS));
                String cameraName = JOptionPane.showInputDialog(panel1, "Name your camera", null);
                cameras.add(new JLabel(cameraName));
                cameras.add(new JButton("This is placeholder"));
                cameras.revalidate();
                mainPanel.revalidate();
                repaint();
            }
        });
    }


    /**
     * The main method to run the GUI
     * */
    public static void main(String[] args){
        JFrame frame = new MainGUI("Ninja Protection Services");
        frame.setVisible(true);
    }
}
