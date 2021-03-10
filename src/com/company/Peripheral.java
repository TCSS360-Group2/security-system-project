package com.company;

public abstract class Peripheral extends Device {
    private BaseStation baseStation = null;

    public enum PeripheralType {
        MotionSensor,
        WaterSensor,
        COsensor,
        SmokeSensor,
        TemperatureSensor,
    }

    public static String getPeripheralTypeID(Peripheral.PeripheralType peripheralType) {
        return switch (peripheralType) {
            case MotionSensor -> "MO";
            case WaterSensor -> "WA";
            case COsensor -> "CO";
            case SmokeSensor -> "SM";
            case TemperatureSensor -> "TE";
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
    protected void alert() {
        if (this.baseStation != null) {
            this.baseStation.alert(this.getID());
        }
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
