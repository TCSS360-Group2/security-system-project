package com.company;

public class WaterSensor extends Peripheral{
    public static int threshold = 50;

    WaterSensor(PeripheralType peripheralType) {
        super(peripheralType);
    }

    protected void poll(int sensorData) {
        if (sensorData > threshold)
            alert(getID());
    }


}
