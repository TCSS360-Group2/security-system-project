package com.company;

public class TemperatureSensor extends Peripheral{
    public static int threshold = 100;

    TemperatureSensor() {
        super(PeripheralType.TemperatureSensor);
    }

    public void poll(int sensorData) {
        if (sensorData > threshold)
            alert();
    }
}
