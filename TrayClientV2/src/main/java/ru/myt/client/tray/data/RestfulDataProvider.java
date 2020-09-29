package ru.myt.client.tray.data;

import ru.myt.client.tray.settings.TraySettings;
import ru.myt.core.EventTypes;
import ru.myt.core.IUserNotificationEvents;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.UserSettings;
import ru.myt.data.settings.EnvSettings;
import ru.myt.integration.simpleObject.SimpleEvent;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RestfulDataProvider implements IDataProvider {

    private static RestfulDataProvider instance;
    private String lastSuccessProcessedEventId;
    private static IUserNotificationEvents subscriber;

    public static RestfulDataProvider getInstance() {
        if (instance == null) {
            instance = new RestfulDataProvider();
        }
        return instance;
    }

    private RestfulDataProvider() {
        lastSuccessProcessedEventId = null;
    }

    public void subscribe(IUserNotificationEvents subscriber) {
        RestfulDataProvider.subscriber = subscriber;
    }

    public void setSelectedTrain(String deviceId, String newTrainId) {
        RestClient.getInstance().setSelectedTrain(deviceId, newTrainId);
    }

    public void activateDevice(String deviceId) {
        RestClient.getInstance().activateDevice(deviceId);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                processNextEvent(lastSuccessProcessedEventId);
            }
        }, 100, 100);
    }

    @Override
    public void deactivateDevice(String deviceId) {
        RestClient.getInstance().deactivateDevice(deviceId);
    }

    private SimpleEvent getNextEvent(String lastSuccessProcessedEventId) {
        return RestClient.getInstance().getNextEvent(TraySettings.DEVICE_ID, lastSuccessProcessedEventId);
    }

    public void processNextEvent(String lastSuccessProcessedEventId) {
        SimpleEvent event = getNextEvent(lastSuccessProcessedEventId);
        if (event == null) {
            return;
        }
        try {
            if (event.getEventType().toString().equalsIgnoreCase(EventTypes.DEVICE_ACTIVATION.toString())) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("TRAYCLIENT - INIT_RESP received");
                }

                UserSettings userSettings = (UserSettings) event.getEventPayload();
                subscriber.userSettingsEvent(userSettings);
                this.lastSuccessProcessedEventId = event.getUniqueId();
                return;
            }

            if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NOTIF.toString())) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("TRAYCLIENT - NOTIF received");
                }
                String text = (String) event.getEventPayload();
                subscriber.textNotification(text);
                this.lastSuccessProcessedEventId = event.getUniqueId();
                return;
            }

            if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NEAREST_TRAINS_CHANGED.toString())) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("TRAYCLIENT - NEAREST_TRAINS_CHANGED received");
                }
                ArrayList<Train> nearestTrains = (ArrayList<Train>) event.getEventPayload();
                subscriber.nearestTrainsChangedEvent(nearestTrains);
                this.lastSuccessProcessedEventId = event.getUniqueId();
                return;
            }

            if (event.getEventType().toString().equalsIgnoreCase(EventTypes.AVAILABLE_TRAINS_CHANGED.toString())) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("TRAYCLIENT - AVAILABLE_TRAINS_CHANGED received");
                }
                ArrayList<Train> availableTrains = (ArrayList<Train>) event.getEventPayload();
                subscriber.availableTrainsChangedEvent(availableTrains);
                this.lastSuccessProcessedEventId = event.getUniqueId();
                return;
            }

            if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NEW_TIME_FOR_SELECTED_TRAIN.toString())) {
                if (EnvSettings.DEBUG_MODE) {
                    System.out.println("TRAYCLIENT - NEW_TIME_FOR_SELECTED_TRAIN received");
                }
                int time = (Integer) event.getEventPayload();
                subscriber.selectedTrainNotification(time);
                this.lastSuccessProcessedEventId = event.getUniqueId();
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION - while processing not null event");
        }
    }

}
