package com.company;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Timer peripheralPollTimer = new Timer();
        PeripheralController peripheralController = new PeripheralController();

        BaseStation baseStation = new BaseStation();
        baseStation.init();
        peripheralController.registerAll(baseStation);


        MainGUI gui = MainGUI.newGUI("Security Sys", baseStation, peripheralController);

        peripheralPollTimer.schedule(peripheralController, 1000, 1000);
    }
}

