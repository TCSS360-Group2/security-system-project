package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

class PeripheralController extends TimerTask {
    public ArrayList<Peripheral> peripherals = new ArrayList<>();

    public Peripheral addPeripheral(Peripheral.PeripheralType peripheralType) {
        Peripheral peripheral = Peripheral.newPeripheral(peripheralType);
        peripheral.init();
        this.peripherals.add(peripheral);
        return peripheral;
    }

    public void addPeripheral(Peripheral p) {
        this.peripherals.add(p);
    }

    public void registerAll(BaseStation baseStation) {
        this.peripherals.forEach(peripheral -> {
            peripheral.registerWithBaseStation(baseStation);
        });
    }

    public void setState(DeviceState state) {
        this.peripherals.forEach(peripheral -> {
            peripheral.setDeviceState(state);
        });
    }

    public void run() {
        Random r = new Random();
        peripherals.forEach(peripheral -> {
            // normal dist, mean 0, sd 1
            double rawValue = r.nextGaussian();
            rawValue = rawValue > 0 ? rawValue : 0;
            int sensorData = Math.min((int) (rawValue * 50), 100);
            System.out.printf("%s : %d\n", peripheral.getID(), sensorData);
            peripheral.poll(sensorData);
        });
    }
}
