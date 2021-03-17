package com.company;

public class WaterSensor extends Peripheral{
    public static int threshold = 50;

    WaterSensor() {
        super(PeripheralType.WaterSensor);
    }

    public void poll(int sensorData) {
        if (this.getIsOn() && (sensorData > threshold))
            alert();
    }
    protected void init(){
        this.buttonPress();
        this.setIsEnabled(true);
    }
}
