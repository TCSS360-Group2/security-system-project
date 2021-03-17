package com.company;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Peripheral extends Device {
    private BaseStation baseStation = null;

    private boolean isOn;
    private boolean isEnabled;
    private boolean isTriggered;
    protected int alarmDuration = 5;

    public enum PeripheralType {
        MotionSensor,
        WaterSensor,
        COsensor,
        SmokeSensor,
        TemperatureSensor,
        EntrySensor,
        GlassBreakSensor,
        Siren,

        }

    public static String getPeripheralTypeID(Peripheral.PeripheralType peripheralType) {
        return switch (peripheralType) {
            case MotionSensor -> "MO";
            case WaterSensor -> "WA";
            case COsensor -> "CO";
            case SmokeSensor -> "SM";
            case TemperatureSensor -> "TE";
            case EntrySensor -> "ES";
            case GlassBreakSensor -> "GB";
            case Siren -> "SR";
            default -> "";
        };
    }

    public static Peripheral newPeripheral(PeripheralType peripheralType) {
        return switch (peripheralType) {
            case MotionSensor -> new MotionSensor();
            case WaterSensor -> new WaterSensor();
            case COsensor -> new COsensor();
            case SmokeSensor -> new SmokeSensor();
            case TemperatureSensor -> new TemperatureSensor();
            case EntrySensor -> new EntrySensor();
            case GlassBreakSensor -> new GlassBreakSensor();
            case Siren -> new Siren();
        };
    }

    Peripheral(Peripheral.PeripheralType peripheralType) {
        super(Peripheral.getPeripheralTypeID(peripheralType));
    }

    public boolean pong() {
        return this.getState() != DeviceState.Off;
    }

    public void registerWithBaseStation(BaseStation baseStationRef) {
        if (baseStationRef == null) {
            return;
        }

        this.baseStation = baseStationRef;
        baseStationRef.register(this);
    }

    @Override
    protected void setDeviceState(DeviceState newState) {
        super.setDeviceState(newState);
        if (newState != DeviceState.Off) {
            this.registerWithBaseStation(this.baseStation);
        }
    }

    // update your sensor data here
    // sensor data will range from 0-100
    abstract public void poll(int sensorData);

    // call this in derived classes to alert the base station
    protected void alert() {
        if (this.baseStation != null) {
            this.baseStation.alert(this);
            this.setIsTriggered(true);
        }
    }

    protected void init() {
    }
    public void speaker(){
        System.out.println("beep");
    }
    public void redFlash(){
        System.out.println("red flash");
    }
    public void greenFlash(){
        System.out.println("green flash");
    }
    public void buttonPress() {
        if (!this.isOn) {
            this.isOn = true;
            System.out.println(this.getID() + " turned on");
            speaker();
            greenFlash();
        }else {
            this.isOn = false;
            System.out.println(this.getID() + " turned off");
            speaker();
            redFlash();
        }
    }
    public boolean getIsOn(){
        return isOn;
    }
    public boolean getIsEnabled(){
        return this.isEnabled;
    }
    public void setIsEnabled(boolean b){
        this.isEnabled = b;
    }
    public boolean getIsTriggered(){
        return this.isTriggered;
    }
    public void setIsTriggered(boolean b){ this.isTriggered = b; }


}
