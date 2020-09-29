package ru.myt.core;

import ru.myt.core.entity.Device;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;

public class Router {
    private static Router instance;

    private Router() {
    }

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public void sendMessage(Event event, User user) {
        String eventName = event.getEventType().toString();

        if (eventName.equalsIgnoreCase(EventTypes.NEW_TRAIN_SELECTED.toString())) {
            RouteController.getInstance().setSelectedTrain((String) event.getEventPayload(),
                    UserManager.getInstance().getUserByDeviceId(event.getDestinationDeviceId()));
            return;
        }

        if (user==null){
            System.out.println("EXCEPTION: no right :if: founded!");
            return;
        }

        //:user: required for "if"'s below

        if (eventName.equalsIgnoreCase(EventTypes.DEVICE_ACTIVATION.toString())) {
            UserManager.getInstance().addActiveUserByDeviceId(event.getDestinationDeviceId());
            EventManager.getInstance().pushEvent(event);

            RouteController.getInstance().getFullRouteInfoForDevice(user, event.getDestinationDeviceId());
            return;
        }

        if (eventName.equalsIgnoreCase(EventTypes.DEVICE_DEACTIVATION.toString())) {
            UserManager.getInstance().removeActiveUserByDeviceId(event.getDestinationDeviceId());
            EventManager.getInstance().pushEvent(event);
            return;
        }



        if (eventName.equalsIgnoreCase(EventTypes.EVENT_CLEAR_NOTIF.toString())) {
            NotificationManager.getInstance().clearNotifications(user);
            return;
        }

        if ((eventName.equalsIgnoreCase(EventTypes.NOTIF.toString())) ||
                (eventName.equalsIgnoreCase(EventTypes.NEAREST_TRAINS_CHANGED.toString())) ||
                (eventName.equalsIgnoreCase(EventTypes.AVAILABLE_TRAINS_CHANGED.toString())) ||
                (eventName.equalsIgnoreCase(EventTypes.NEW_TIME_FOR_SELECTED_TRAIN.toString()))) {

            if (event.getDestinationDeviceId()==null){
                for (Device activeDevice : DeviceManager.getInstance().getActiveDevicesForDevice(user)) {
                    Event independentEvent = new Event (event.getEventType(),event.getEventPayload(),activeDevice.getDeviceID());
                    EventManager.getInstance().pushEvent(independentEvent);
                }
            }
            else {
                EventManager.getInstance().pushEvent(event);
            }


        }
        if (eventName.equalsIgnoreCase(EventTypes.CREATE_NOTIFICATION_FOR_SELECTED_TRAIN.toString())) {
            Train currentTrain = (Train) event.getEventPayload();
            NotificationManager.getInstance().createNotification(user, currentTrain);
            return;
        }

    }

    public Event receiveMessage(String deviceId, String lastSuccessProcessedEventId) {
            DeviceManager.getInstance().updateDeviceLastActionTime(deviceId);
        return EventManager.getInstance().getEventByDevice(deviceId, lastSuccessProcessedEventId);
    }
}

