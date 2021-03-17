package com.company;

public class TemperatureSensor extends Peripheral{
    public static int threshold = 80;
    private int alarmCounter;

    TemperatureSensor() {
        super(PeripheralType.TemperatureSensor);
        alarmCounter = 0;
    }

    public void poll(int sensorData) {
        if(this.getIsTriggered()){
            System.out.println(getID() + " alarm on");
            alarmCounter++;
            if(alarmCounter > alarmDuration){
                this.setIsTriggered(false);
                alarmCounter = 0;
            }
        } else if (this.getIsOn() && (sensorData > threshold))
            alert();
    }
    public void setIsTriggered(boolean b){
        super.setIsTriggered(b);
        if (b) {alarmCounter = 0;}
    }
    protected void init(){
        this.buttonPress();
        this.setIsEnabled(true);
    }

}
