package com.company;

public abstract class Peripheral extends Device {
    private BaseStation baseStation = null;
    private boolean connectedToBaseStation = false;

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

    protected void init() {
        this.activate();
    }

    public void registerWithBaseStation(BaseStation baseStationRef) {
        if (baseStationRef == null) { return; }

        this.baseStation = baseStationRef;
        if (baseStationRef.register(this)) {
            this.connectedToBaseStation = true;
        }
    }
}
