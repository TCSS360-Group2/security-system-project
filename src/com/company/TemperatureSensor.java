package com.company;

public class TemperatureSensor extends Peripheral{
    public static int threshold = 100;

    TemperatureSensor(PeripheralType peripheralType) {
        super(peripheralType);
    }

    protected void poll(int sensorData) {
        if (sensorData > threshold)
            alert(getID());
    }
}
