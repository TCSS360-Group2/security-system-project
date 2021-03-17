package com.company;

public class SmokeSensor extends Peripheral{
    public static int threshold = 40;
    private int alarmCounter;

    SmokeSensor() {
        super(PeripheralType.SmokeSensor);
        alarmCounter = 0;
    }
    //this method is a poll method
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
