package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Timer eventTimer = new Timer();
        Timer peripheralPollTimer = new Timer();
        BaseStation baseStation = new BaseStation();
        baseStation.init();
        PeripheralController peripheralController = new PeripheralController();

        peripheralController.addPeripheral(Peripheral.PeripheralType.COsensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.WaterSensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.SmokeSensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.TemperatureSensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.MotionSensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.GlassBreakSensor);
        peripheralController.addPeripheral(Peripheral.PeripheralType.EntrySensor);
        //peripheralController.addPeripheral(Peripheral.PeripheralType.Siren);


        peripheralController.registerAll(baseStation);

        class Events extends TimerTask {
            int eventCounter = 0;
            public void run() {
                switch (this.eventCounter) {
                    case 0:
                        Peripheral peripheral =
                                peripheralController.addPeripheral(Peripheral.PeripheralType.MotionSensor);
                        peripheral.registerWithBaseStation(baseStation);
                        break;
                    case 1:
                        peripheralController.peripherals.get(1).setDeviceState(DeviceState.Off);
                        break;
                    case 2:
                        peripheralController.peripherals.get(3).setDeviceState(DeviceState.Home);
                        break;
                    case 3:
                        peripheralController.peripherals.get(1).setDeviceState(DeviceState.Home);
                        break;
                    default:
                        eventTimer.cancel();
                }

                ++this.eventCounter;
            }
        }

        eventTimer.schedule(new Events(), 4500, 4500);
        peripheralPollTimer.schedule(peripheralController, 1000, 1000);
    }
}

class PeripheralController extends TimerTask {
    public ArrayList<Peripheral> peripherals = new ArrayList<>();

    public Peripheral addPeripheral(Peripheral.PeripheralType peripheralType) {
        Peripheral peripheral = Peripheral.newPeripheral(peripheralType);
        peripheral.init();
        this.peripherals.add(peripheral);
        return peripheral;
    }

    public void registerAll(BaseStation baseStation) {
        this.peripherals.forEach(peripheral -> {
            peripheral.registerWithBaseStation(baseStation);
        });
    }

    public void run() {
        Random r = new Random();
        peripherals.forEach(peripheral -> {
            // normal dist, mean 0, sd 1
            double rawValue = r.nextGaussian();
            rawValue = rawValue > 0 ? rawValue : 0;
            int sensorData = Math.min((int)(rawValue * 50), 100);
            System.out.printf("%s : %d\n", peripheral.getID(), sensorData);
            peripheral.poll(sensorData);
        });
    }
}

