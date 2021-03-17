package com.company;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Siren extends Peripheral implements PropertyChangeListener {
    Siren() {
        super(PeripheralType.Siren);
    }

    @Override
    public void poll(int sensorData) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //if(this.getIsEnabled())
        System.out.println(evt.getNewValue() + " Siren");
    }
    public void init(){
        this.setIsEnabled(true);

    }

}
