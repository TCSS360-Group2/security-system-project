package com.company;

public class GlassBreakSensor extends Peripheral{
    public static int threshold = 80;
    private int alarmCounter;

    GlassBreakSensor() {
        super(PeripheralType.GlassBreakSensor);
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
        if(this.getState().equals(DeviceState.Off)){
            this.setIsEnabled(false);
        } else {
            this.setIsEnabled(true);
        }

    }
}
