package ru.myt.core.entity;

public abstract class Device {
    private String deviceID;
    private DeviceStatus status;
    private long lastActionTime;

    public Device(String deviceID) {
        setDeviceID(deviceID);
        this.status = DeviceStatus.REGISTERED;
        updateLastActionTime();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    private DeviceStatus getStatus() {
        return status;
    }

    private void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public void activate () {
        setStatus(DeviceStatus.ACTIVE);
        updateLastActionTime();
    }

    public void deactivate () {
        setStatus(DeviceStatus.REGISTERED);
    }

    public boolean isActive () {
        if (getStatus().toString().equalsIgnoreCase(DeviceStatus.ACTIVE.toString()))
        {
            return true;
        }
        return false;
    }

    public long getLastActionTime() {
        return lastActionTime;
    }

    public void updateLastActionTime() {
        this.lastActionTime = System.currentTimeMillis();
    }
}
