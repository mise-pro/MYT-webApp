package ru.myt.integration.simpleObject;

import ru.myt.core.Event;
import ru.myt.core.EventTypes;

public class SimpleEvent {

    private String uniqueId;
    private EventTypes eventType;
    private Object eventPayload;

    public SimpleEvent(){}

    public SimpleEvent(Event event){
        this.eventType = event.getEventType();
        this.eventPayload = event.getEventPayload();
        this.uniqueId = event.getUniqueId();
    }

    public EventTypes getEventType() {
        return eventType;
    }

    public void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    public Object getEventPayload() {
        return eventPayload;
    }

    public void setEventPayload(Object eventPayload) {
        this.eventPayload = eventPayload;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "SimpleEvent{" +
                "uniqueId='" + uniqueId + '\'' +
                ", eventType=" + eventType +
                ", eventObj=" + eventPayload +
                '}';
    }
}
