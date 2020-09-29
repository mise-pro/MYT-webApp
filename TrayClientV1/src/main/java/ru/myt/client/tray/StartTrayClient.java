package ru.myt.client.tray;

import ru.myt.client.tray.data.IDataProvider;
import ru.myt.client.tray.data.RestfulDataProvider;

public class StartTrayClient {
    public static void main(String[] args){
        System.out.println("Application tray client is STARTing");

        IDataProvider dataProvider = RestfulDataProvider.getInstance();

        TrayGUI.getInstance().setDataProvider(dataProvider);
        TrayController.getInstance().setDataProvider(dataProvider);

        System.out.println("Application tray client has been STARTED");
    }

}
