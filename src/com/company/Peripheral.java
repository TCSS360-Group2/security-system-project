package com.company;

public abstract class Peripheral extends Device {
    private BaseStation baseStation = null;

    public enum PeripheralType {
        MotionSensor,
        WaterSensor,
    }

    public static String getPeripheralTypeID(Peripheral.PeripheralType type) {
        return switch (type) {
            case MotionSensor -> "MS";
            case WaterSensor -> "WS";
            default -> "";
        };
    }

    Peripheral(Peripheral.PeripheralType peripheralType) {
        super(Peripheral.getPeripheralTypeID(peripheralType));
    }

    public boolean pong() {
        return this.hasPower();
    }

    @Override
    protected void activate() {
        super.activate();
        this.registerWithBaseStation(this.baseStation);
    }

    // call this in derived classes to alert the base station
    protected void alert(String peripheralID) {
        // not implemented yet
    }

    // update your sensor data here
    abstract public void poll(int sensorData);

    protected void init() {
        this.activate();
    }

    public void registerWithBaseStation(BaseStation baseStationRef) {
        if (baseStationRef == null) { return; }

        this.baseStation = baseStationRef;
        baseStationRef.register(this);
    }
}
