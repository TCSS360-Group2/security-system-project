package com.company;

public class MotionSensor extends Peripheral {
    MotionSensor() {
        super(PeripheralType.MotionSensor);
    }

    @Override
    public void poll(int sensorData) {
        
    }
}
