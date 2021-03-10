package com.company;

public class WaterSensor extends Peripheral{
    public static int threshold = 50;

    WaterSensor() {
        super(PeripheralType.WaterSensor);
    }

    public void poll(int sensorData) {
        if (sensorData > threshold)
            alert();
    }
}
