package com.company;

public class SmokeSensor extends Peripheral{
    public static int threshold = 40;

    SmokeSensor() {
        super(PeripheralType.SmokeSensor);
    }
    //this method is a poll method
    public void poll(int sensorData) {
        if (this.getIsOn() && (sensorData > threshold))
            alert();
    }
    protected void init(){
        this.buttonPress();
        this.setIsEnabled(true);
    }
}
