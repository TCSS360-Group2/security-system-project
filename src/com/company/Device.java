package com.company;

import java.util.zip.DeflaterInputStream;

enum DeviceState {
    Home,
    Away,
    Disabled
}

public abstract class Device {
    private String ID;
    private static int deviceCounter = 0;
    private DeviceState state = DeviceState.Disabled;

    protected Device(String deviceSymbol) {
        ++Device.deviceCounter;
        encodeID(deviceSymbol);
    }

    public String getID() {
        return this.ID;
    }

    public DeviceState getState() {
        return this.state;
    }

    protected void setDeviceState(DeviceState newState) {
        this.state = newState;
    }

    protected abstract void init();

    private void encodeID(String deviceSymbol) {
        this.ID = String.format("%s-%d", deviceSymbol, Device.deviceCounter);
    }
}
