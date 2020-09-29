package ru.myt.core;

import ru.myt.data.settings.EnvSettings;

import java.util.ArrayList;

public class EventManager {

    private static EventManager instance;
    private ArrayList<Event> eventStorage;

    private EventManager() {
        eventStorage = new ArrayList<>();
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    //the idea is to store all events for all device at global arraylist.
    //and once received eventId for the device immediately pop this elem from storage
    public Event getEventByDevice(String deviceId, String lastSuccessProcessedEventId) {
        markAsDelivered(deviceId, lastSuccessProcessedEventId);
        if (!EnvSettings.DEBUG_MODE) {
            removeDelivered();
        }
        Event foundedEvent = null;

        for (Event event : eventStorage) {
            if ((event.getDestinationDeviceId().equalsIgnoreCase(deviceId)) &&
                    (!event.getUniqueId().equals(lastSuccessProcessedEventId)) &&
                    !event.isDelivered()) {
                foundedEvent = event;
                break;
            }
        }
        return foundedEvent;
    }

    public void pushEvent(Event event) {
        if (event.getEventType().toString().equalsIgnoreCase(EventTypes.DEVICE_ACTIVATION.toString()) ||
                event.getEventType().toString().equalsIgnoreCase(EventTypes.DEVICE_DEACTIVATION.toString())) {
            clearAllEventsForThisDevice(event.getDestinationDeviceId());
        }
        eventStorage.add(event);
        if (EnvSettings.DEBUG_MODE) {
            System.out.println("EVENT_MANAGER: New msg (" + event.getEventType() + ") added to device " + event.getDestinationDeviceId());
        }
    }

    private void markAsDelivered(String deviceId, String lastSuccessProcessedEventId) {
        for (Event event : eventStorage)
            if ((event.getDestinationDeviceId().equalsIgnoreCase(deviceId)) &&
                    (event.getUniqueId().equalsIgnoreCase(lastSuccessProcessedEventId)) &&
                    !event.isDelivered()) {
                event.markEventAsDelivered();
                if (EnvSettings.DEBUG_MODE) {
                    System.out.println("EVENT_MANAGER: msg (" + event.getEventType() + ") delivered to device " + event.getDestinationDeviceId());
                }
                break;
            }
    }

    private void removeDelivered() {
        ArrayList<Event> eventsToRemove = new ArrayList<>();
        for (Event event : eventStorage)
            if (event.isDelivered()) {
                eventsToRemove.add(event);
            }
        eventStorage.removeAll(eventsToRemove);
        eventStorage.trimToSize(); //mark: may be to often??
    }

    private void clearAllEventsForThisDevice(String deviceId) {
        ArrayList<Event> eventsToRemove = new ArrayList<>();
        for (Event event : eventStorage)
            if (event.getDestinationDeviceId().equalsIgnoreCase(deviceId)) {
                eventsToRemove.add(event);
            }
        eventStorage.removeAll(eventsToRemove);
        eventStorage.trimToSize();
    }

}
