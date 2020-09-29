package ru.myt.integration.adapters;

import ru.myt.core.*;
import ru.myt.core.entity.User;

public class TrayAdapter implements ITrainsCommands {

    private static TrayAdapter instance;

    private TrayAdapter() {
    }

    public static TrayAdapter getInstance() {
        if (instance == null) {
            instance = new TrayAdapter();
        }
        return instance;
    }

    public void setSelectedTrain(String deviceId, String newTrainId) {
        Event event = new Event (EventTypes.NEW_TRAIN_SELECTED, newTrainId);
        event.setDestinationDeviceId(deviceId);
        Router.getInstance().sendMessage(event, null);
    }

    public void activateDevice(String deviceId) {
        CoreInit.checkCore();
        User user = UserManager.getInstance().getUserByDeviceId(deviceId);
        Event event = new Event (EventTypes.DEVICE_ACTIVATION, user.getUserSettings(), deviceId);
        Router.getInstance().sendMessage(event, user); // send to myself via router
    }

    public void deactivateDevice(String deviceId) {
        Event event = new Event(EventTypes.DEVICE_DEACTIVATION,null, deviceId);
        Router.getInstance().sendMessage(event, UserManager.getInstance().getUserByDeviceId(deviceId));
    }

    public Event getNextEvent(String deviceId, String lastSuccessProcessedEventId) {
        return Router.getInstance().receiveMessage(deviceId, lastSuccessProcessedEventId);// may be null
    }
}
