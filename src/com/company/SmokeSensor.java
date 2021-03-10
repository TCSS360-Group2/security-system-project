package com.company;

public class SmokeSensor extends Peripheral{
    public static int threshold = 200;

    SmokeSensor() {
        super(PeripheralType.SmokeSensor);
    }

    public void poll(int sensorData) {
        if (sensorData > threshold)
            alert();
    }
}
