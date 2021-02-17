package com.company;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        BaseStation baseStation = new BaseStation();

        Peripheral peripherals[] = {
                new MotionSensor(),
                new MotionSensor(),
                new MotionSensor(),
        };

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
                        baseStation.register(sensor);
                        break;
                    case 1:
                        peripherals[1].deactivate();
                        break;
                    case 2:
                        // Delay
                        break;
                    case 3:
                        peripherals[1].activate();
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

