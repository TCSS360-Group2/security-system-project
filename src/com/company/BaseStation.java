package com.company;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public final class BaseStation extends Device {
    HashMap<String, Peripheral> peripheralMap = new HashMap<>();
    PingTask pingWorker = new PingTask(this);
    Timer pingTimer = new Timer();
    PropertyChangeSupport observed;
    String alertString;
    BaseStation() {
        super("BS");
        System.out.println(this.getID());
        this.pingTimer.schedule(this.pingWorker, 3000, 3000);
        observed = new PropertyChangeSupport(this);
    }

    public synchronized boolean register(Peripheral peripheral) {
        if (this.getState() == DeviceState.Off) { return false; }
        if (peripheral == null) { return false; }
        if (this.peripheralMap.containsValue(peripheral)) {  return true; }

        this.peripheralMap.put(peripheral.getID(), peripheral);
        System.out.printf("Registered %s with %s.\n", peripheral.getID(), this.getID());
        return true;
    }



    public void alert(Peripheral per) {
        alertString = this.getID() + " got alert from"  + per.getID();
        System.out.printf("%s got alert from %s.\n", this.getID(), per.getID());
        observed.firePropertyChange("Alert","",this.alertString);

    }

    protected void init() { }

    private synchronized void pingPeripherals() {
        if (this.getState() == DeviceState.Off) { return; }

        ArrayList<String> toRemove = new ArrayList<>();

        this.peripheralMap.forEach((peripheralID, peripheral) -> {
            System.out.printf("%s is attempting to ping %s... ", this.getID(), peripheralID);
            if (!ping(peripheral)) {
                toRemove.add(peripheralID);
                System.out.println("Failure.");
            } else {
                System.out.println("Success!");
            }
        });

        System.out.println();

        toRemove.forEach(peripheralID -> {
            this.peripheralMap.remove(peripheralID);
        });
    }

    private boolean ping(Peripheral peripheral) {
        return peripheral.pong();
    }

    private class PingTask extends TimerTask {
        BaseStation owner;

        PingTask(BaseStation owner) {
            this.owner = owner;
        }

        public void run() {
            owner.pingPeripherals();
        }

        public void end() {
            this.owner.pingTimer.cancel();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener observer){
        observed.addPropertyChangeListener(observer);
    }

}
