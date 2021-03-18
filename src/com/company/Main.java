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
        MainGUI gui = MainGUI.newGUI("Security Sys", baseStation, peripheralController);
        peripheralController.registerAll(baseStation);
        peripheralPollTimer.schedule(peripheralController, 1000, 1000);
    }
}

