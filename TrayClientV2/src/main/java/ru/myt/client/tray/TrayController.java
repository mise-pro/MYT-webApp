package ru.myt.client.tray;

import ru.myt.client.tray.data.IDataProvider;
import ru.myt.client.tray.settings.TraySettings;
import ru.myt.core.entity.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayController extends MouseAdapter implements ActionListener {
    private static TrayController instance;
    private static IDataProvider dataProvider;

    private TrayController() {
    }

    public static TrayController getInstance() {
        if (instance == null) {
            instance = new TrayController();
        }
        return instance;
    }

    public void setDataProvider(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
        dataProvider.activateDevice(TraySettings.DEVICE_ID);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() instanceof JMenuItem) {
            JMenuItem selectedItem = (JMenuItem) evt.getSource();
            if (TraySettings.DEBUG_MODE) {
                System.out.println("Selected TRAY GUI element: " + selectedItem.getName());
            }
            if (selectedItem.getName().equalsIgnoreCase("COMMAND-showAvailableTrains")) {
                TrayGUI.getInstance().showAvailableTrainsCommand();
                return;
            }
            if (selectedItem.getName().equalsIgnoreCase("COMMAND-Exit")) {
                System.out.println("Application is terminating...");
                dataProvider.deactivateDevice(TraySettings.DEVICE_ID);
                System.exit(0);
                return;
            }
            dataProvider.setSelectedTrain(TraySettings.DEVICE_ID, selectedItem.getName());
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            Train doubleClickedTrain = (Train) list.getModel().getElementAt(index);
            dataProvider.setSelectedTrain(TraySettings.DEVICE_ID, doubleClickedTrain.getUniqueID());
        }
    }




}
