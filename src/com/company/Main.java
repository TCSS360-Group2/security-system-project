package com.company;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        BaseStation baseStation = new BaseStation();
        baseStation.init();

        ArrayList<Peripheral> peripherals = new ArrayList<>();
        peripherals.add(new MotionSensor());
        peripherals.add(new MotionSensor());
        peripherals.add(new MotionSensor());

        for (Peripheral peripheral : peripherals) {
            peripheral.init();
            peripheral.registerWithBaseStation(baseStation);
        }

        class Events extends TimerTask {
            int eventCounter = 0;
            public void run() {
                switch (this.eventCounter) {
                    case 0:
                        MotionSensor sensor = new MotionSensor();
                        peripherals.add(sensor);
                        sensor.registerWithBaseStation(baseStation);
                        break;
                    case 1:
                        peripherals.get(1).deactivate();
                        break;
                    case 2:
                        peripherals.get(3).activate();
                        break;
                    case 3:
                        peripherals.get(1).activate();
                        break;
                    default:
                        timer.cancel();
                }

                ++this.eventCounter;
            }
        }

        timer.schedule(new Events(), 4500, 4500);
    }
}

