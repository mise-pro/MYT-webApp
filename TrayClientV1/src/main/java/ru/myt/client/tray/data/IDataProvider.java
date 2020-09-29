package ru.myt.client.tray.data;

import ru.myt.core.IUserNotificationEvents;

public interface IDataProvider {
     void setSelectedTrain(String deviceId, String newTrainId);
     void activateDevice(String deviceId);
     void deactivateDevice(String deviceId);
     void processNextEvent (String lastSuccessProcessedEventId);
     void subscribe(IUserNotificationEvents trayGUI);
}
