package com.company;

public class SmokeSensor extends Peripheral{
    public static int threshold = 200;

    SmokeSensor(PeripheralType peripheralType) {
        super(peripheralType);
    }

    protected void poll(int sensorData) {
        if (sensorData > threshold)
            alert(getID());
    }
}
