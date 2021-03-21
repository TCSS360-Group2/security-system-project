package com.company;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Timer peripheralPollTimer = new Timer();
        BaseStation baseStation = new BaseStation();
        baseStation.init();
        PeripheralController peripheralController = new PeripheralController();
        MainGUI gui = MainGUI.newGUI("Security Sys", baseStation, peripheralController);
        peripheralController.registerAll(baseStation);
        peripheralPollTimer.schedule(peripheralController, 1000, 1000);
    }
}

