package com.company;

public class COsensor extends Peripheral{
    public static int threshold = 80;

    COsensor() {
        super(PeripheralType.COsensor);
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
