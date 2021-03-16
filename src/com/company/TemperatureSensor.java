package com.company;

public class TemperatureSensor extends Peripheral{
    public static int threshold = 80;

    TemperatureSensor() {
        super(PeripheralType.TemperatureSensor);
    }

    public void poll(int sensorData) {
        if (this.getIsOn() && (sensorData > threshold))
            alert();
    }
}
