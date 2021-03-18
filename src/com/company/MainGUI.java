package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MainGUI extends JFrame implements PropertyChangeListener {
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
    private JPanel notifications;


    //Base Station
    private BaseStation station;

    //Sensors
    private PeripheralController sensors;

    public MainGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
    }

    public void setBaseStation(BaseStation b) {
        this.station = b;
    }

    public void setPeripheralController(PeripheralController p) {
        this.sensors = p;
    }
    /**
     * Sets up all the action listeners for the various buttons across the GUI
     * */
    public void setupActions(){
        //Set up the base station
        station.setDeviceState(DeviceState.Away);
        //Set up alert listening for the GUI
        station.addPropertyChangeListener(this);
        //Set up alert listening for
        station.addPropertyChangeListener(new Siren());
        //Arm Disarm Listener: Changes button color and text, updates sensor text, eventually will update the system too
        disarmSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (station.getState() == DeviceState.Off) { return; }

                if(station.getState() == DeviceState.Home){
                    disarmSystemButton.setText("Away Mode");
                    disarmSystemButton.setBackground(new Color(255,0,8));
                    //Change the base station state
                    station.setDeviceState(DeviceState.Away);
                    sensors.setState(DeviceState.Away);
                    System.out.println(station.getState());

                    //Change all the sensor values
                    for(Component component: burglaryPane.getComponents()){
                        if(component instanceof SensorPanel){
                            ((SensorPanel)component).status.setText("Status: Disabled");
                        }
                    }
                }
                else{
                    disarmSystemButton.setText("Home Mode");
                    disarmSystemButton.setBackground(new Color(179,241,157));

                    //Change the base station state
                    station.setDeviceState(DeviceState.Home);
                    sensors.setState(DeviceState.Home);
                    System.out.println(station.getState());
                }
                repaint();
            }
        });
        //Connect/Disconnect Listener: Just adds a new sensor, there is no disconnect feature
        sensorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //First Select a sensor to add then select a name for the sensor
                String[] options = {"Entry", "Glassbreak", "Motion", "Smoke", "Water","Temperature", "CO"};
                Object selected = JOptionPane.showInputDialog(panel1, "Choose a sensor", "Menu",JOptionPane.PLAIN_MESSAGE, null, options,options[0]);
                String selectedString = selected.toString();
                String sensorName = JOptionPane.showInputDialog(panel1, "Name your sensor", null);


                //Then register the sensor with the base station
                Peripheral sensor = switch (selectedString) {
                    case "Motion" -> new MotionSensor();
                    case "Glassbreak" -> new GlassBreakSensor();
                    case "Entry" -> new EntrySensor();
                    case "Smoke" -> new SmokeSensor();
                    case "Water" -> new WaterSensor();
                    case "Temperature" -> new TemperatureSensor();
                    default -> new COsensor();
                };
                sensor.setDeviceState(DeviceState.Away);
                sensors.addPeripheral(sensor);
                station.register(sensor);
                sensor.registerWithBaseStation(station);
                //test alert
                sensor.alert();
                sensor.init();
                //Add the sensor to the status screen
                for(int i = 0; i < options.length; i++){
                    if(selectedString.equals(options[i]) && i <= 2){
                        burglaryPane.setLayout(new BoxLayout(burglaryPane, BoxLayout.PAGE_AXIS));
                        burglaryPane.add(new SensorPanel(sensorName,"Status: Enabled", "Connected",sensor,station));
                        burglaryPane.revalidate();
                        mainPanel.revalidate();
                        repaint();
                        break;
                    }
                    else if(i>2){
                        environmentalPane.setLayout(new BoxLayout(environmentalPane, BoxLayout.PAGE_AXIS));
                        environmentalPane.add(new SensorPanel(sensorName,"Status: Enabled", "Connected",sensor,station));
                        environmentalPane.revalidate();
                        mainPanel.revalidate();
                        repaint();
                        break;
                    }
                    else{
                        continue;
                    }
                }

                mainPanel.revalidate();
                repaint();
            }
        });

        //Connect/Disconnect camera Listener: Adds a new placeholder for a camera, cannot delete cameras
        connectCameraButton.addActionListener(e -> {
            cameras.setLayout(new BoxLayout(cameras, BoxLayout.PAGE_AXIS));
            String cameraName = JOptionPane.showInputDialog(panel1, "Name your camera", null);
            cameras.add(new JLabel(cameraName));
            cameras.add(new JButton("This is placeholder"));
            cameras.revalidate();
            mainPanel.revalidate();
            repaint();
        });

        //A listener for the main system power button
        systemPowerButton.addActionListener(e -> {
            if(systemPowerButton.getText().equals("Power Off")){
                systemPowerButton.setText("Power On");
                systemPowerButton.setBackground(new Color(179,241,157));
                updateAllSensorsPower("Power Off");

            }
            else{
                systemPowerButton.setText("Power Off");
                systemPowerButton.setBackground(new Color(255,0,9));
                updateAllSensorsPower("Power On");
            }
        });
    }
    /**
     * A method to update all of the visual features and device states for sensors on power state change
     * */
    public void updateAllSensorsPower(String state){
        if(state.equals("Power On")){
            for(Component component: burglaryPane.getComponents()){
                if(component instanceof SensorPanel){
                    ((SensorPanel)component).powerOffButton.setText("Power Off");
                    ((SensorPanel)component).powerOffButton.setBackground(new Color(255,0,9));
                }
            }
            for(Component component: environmentalPane.getComponents()){
                if(component instanceof SensorPanel){
                    ((SensorPanel)component).powerOffButton.setText("Power Off");
                    ((SensorPanel)component).powerOffButton.setBackground(new Color(255,0,9));
                }
            }

            for(Peripheral peripheral: sensors.peripherals){
                peripheral.setDeviceState(DeviceState.Home);
                peripheral.buttonPress();
            }
        }
        else{
            for(Component component: burglaryPane.getComponents()){
                if(component instanceof SensorPanel){
                    ((SensorPanel)component).powerOffButton.setText("Power On");
                    ((SensorPanel)component).powerOffButton.setBackground(new Color(179,241,157));
                }
            }
            for(Component component: environmentalPane.getComponents()){
                if(component instanceof SensorPanel){
                    ((SensorPanel)component).powerOffButton.setText("Power On");
                    ((SensorPanel)component).powerOffButton.setBackground(new Color(179,241,157));
                }
            }

            for(Peripheral peripheral: sensors.peripherals){
                peripheral.setDeviceState(DeviceState.Off);
                peripheral.buttonPress();
                station.register(peripheral);
            }
        }

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //For now just print the alert
        System.out.println( evt.getNewValue() + " in GUI");
        notifications.setLayout(new BoxLayout(notifications, BoxLayout.PAGE_AXIS));
        JLabel alert = new JLabel((String) evt.getNewValue());
        alert.setBackground(new Color(150,100,0));
        notifications.add(alert);
        notifications.revalidate();
        notifications.repaint();

    }

    /**
     * The main method to run the GUI
     * */
    public static MainGUI newGUI(String name, BaseStation bs, PeripheralController p){
        MainGUI frame = new MainGUI(name);
        frame.setBaseStation(bs);
        frame.setPeripheralController(p);
        frame.setupActions();
        frame.setVisible(true);
        return frame;
    }
}
