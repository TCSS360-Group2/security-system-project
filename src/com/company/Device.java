package com.company;

public abstract class Device {
    private String ID;
    private static int deviceCounter = 0;
    private boolean hasPower = false;

    protected Device(String deviceSymbol) {
        ++Device.deviceCounter;
        encodeID(deviceSymbol);
    }

    public String getID() {
        return this.ID;
    }

    public boolean hasPower() {
        return this.hasPower;
    }

    protected void toggle() {
        if (this.hasPower) {
            this.activate();
        } else {
            this.deactivate();
        }
    }

    protected void activate() {
        this.hasPower = true;
    }

    protected void deactivate() {
        this.hasPower = false;
    }

    protected abstract void init();

    private void encodeID(String deviceSymbol) {
        this.ID = String.format("%s-%d", deviceSymbol, Device.deviceCounter);
    }
}
