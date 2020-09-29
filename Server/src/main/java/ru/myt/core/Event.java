package ru.myt.core;

import java.util.UUID;

public class Event {
    private String uniqueId;
    private EventTypes eventType;
    private Object eventPayload;
    private String destinationDeviceId;
    private EventStatus status;

    public Event(){}

    public Event(EventTypes eventType, Object eventPayload){
        this.eventType = eventType;
        this.eventPayload = eventPayload;
        this.uniqueId = UUID.randomUUID().toString();
        this.status = EventStatus.IN_TRANSIT;
    }

    public Event(EventTypes eventType, Object eventPayload, String destinationDeviceId){
        this.eventType = eventType;
        this.eventPayload = eventPayload;
        this.uniqueId = UUID.randomUUID().toString();
        this.status = EventStatus.IN_TRANSIT;
        this.destinationDeviceId = destinationDeviceId;
    }

    public EventTypes getEventType() {
        return eventType;
    }

    protected void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    public Object getEventPayload() {
        return eventPayload;
    }

    protected void setEventPayload(Object eventPayload) {
        this.eventPayload = eventPayload;
    }

    public String getDestinationDeviceId() {
        return destinationDeviceId;
    }

    public void setDestinationDeviceId(String detinationDeviceId) {
        this.destinationDeviceId = detinationDeviceId;
    }
    public String getUniqueId() {
        return uniqueId;
    }

    protected void markEventAsDelivered() {
        this.status = EventStatus.DELIVERED;
    }

    public boolean isDelivered() {
        if (status == EventStatus.DELIVERED)
        {
            return true;
        }

        return false;
    }

    protected EventStatus getStatus() {
        return status;
    }

    protected void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    protected void setStatus(EventStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "uniqueId='" + uniqueId +
                ", eventType=" + eventType +
                ", eventObj=" + eventPayload +
                ", destinationDeviceId=" + destinationDeviceId +
                ", status=" + status +
                '}';
    }
}
