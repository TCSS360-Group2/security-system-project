package com.company;

public class COsensor extends Peripheral{
    public static int threshold = 80;

    COsensor(PeripheralType peripheralType) {
        super(peripheralType);
    }

    protected void poll(int sensorData) {
        if (sensorData > threshold)
            alert(getID());
    }
}
