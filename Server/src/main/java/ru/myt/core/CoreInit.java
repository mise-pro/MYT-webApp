package ru.myt.core;

import ru.myt.data.DataProvider;
import ru.myt.integration.adapters.TrayAdapter;

public class CoreInit {

    private static boolean isCoreInitialized = false;
    public static void main(String args[]) {
        checkCore();
    }

    public static void checkCore(){
        if (!isCoreInitialized){
        startCore();}
    }

    public static void startCore(){
        System.out.println("Application core is STARTing");
        new SyncTimer();
        RouteController.getInstance();
        Router.getInstance();
        TrayAdapter.getInstance();
        DeviceManager.getInstance();
        EventManager.getInstance();
        DataProvider.getInstance();
        isCoreInitialized=true;
        System.out.println("Application core has been STARTED");
    }

}
