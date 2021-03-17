package com.company;

public class EntrySensor extends Peripheral {
    public static int threshold = 75;
    private int alarmCounter;

    EntrySensor() {
        super(PeripheralType.EntrySensor);
        alarmCounter=0;
    }

    public void poll(int sensorData) {
        if(this.getIsTriggered()){
            System.out.println(getID() + " alarm on");
            alarmCounter++;
            if(alarmCounter > alarmDuration){
                this.setIsTriggered(false);
                alarmCounter = 0;
            }
        } else if (this.getIsOn() && (sensorData > threshold) && getIsEnabled())
            alert();
    }
    public void setIsTriggered(boolean b){
        super.setIsTriggered(b);
        if (b) {alarmCounter = 0;}
    }
    protected void init(){
        this.buttonPress();
        this.setIsTriggered(false);
        if(this.getState().equals(DeviceState.Away)){
            this.setIsEnabled(true);
        } else {
            this.setIsEnabled(false);
        }
    }
}
