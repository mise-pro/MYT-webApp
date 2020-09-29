package ru.myt.core;

import ru.myt.core.entity.Device;
import ru.myt.core.entity.User;
import ru.myt.data.settings.EnvSettings;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceManager {

    private static DeviceManager instance;
    private ArrayList<Device> activeDevices;
    //todo use set instead of list

    private DeviceManager() {
        activeDevices = new ArrayList<>();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deactivateNonActiveDevices();
            }
        }, 10 * 5000, 10 * 5000);

    }

    private void deactivateNonActiveDevices() {
        for (Device device : activeDevices) {
            if (device.getLastActionTime() + 1000 * 30 < System.currentTimeMillis()) //deactivate if more than 30s timeout
            {
                deactivateDevice(device.getDeviceID());
                // may be should send deactivation event to event manager
                if (EnvSettings.DEBUG_MODE) {
                    System.out.println("DEVICE_MANAGER: Device deactivated by timeout: " + device.getDeviceID());

                }
                break;
            }
        }
    }

    public static DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }


    protected ArrayList<Device> getActiveDevicesForDevice(User user) {
        ArrayList<Device> activeUserDevices = new ArrayList<>();
        for (Device device : user.getDevices()) {
            if (device.isActive()) {
                activeUserDevices.add(device);
            }
        }
        return activeUserDevices;
    }

    public void activateDevice(User user, String deviceIdForActivate) {

        ArrayList<Device> allUserDevices = user.getDevices();
        for (Device device : allUserDevices) {
            if (device.getDeviceID().equalsIgnoreCase(deviceIdForActivate)) {
                device.activate();
                activeDevices.add(device);
            }
        }
    }

    public void deactivateDevice(String deviceIdForActivate) {
        Device deviceToDeactivate = null;
        for (Device device : activeDevices) {
            if (device.getDeviceID().equalsIgnoreCase(deviceIdForActivate)) {
                device.deactivate();
                deviceToDeactivate = device;
            }
        }
        activeDevices.remove(deviceToDeactivate);
    }

    public boolean checkIfUserHaveAnyActiveDevice(User user) {
        ArrayList<Device> allUserDevices = user.getDevices();
        for (Device device : allUserDevices) {
            if (device.isActive()) {
                return true;
            }
        }
        return false;
    }

    public void updateDeviceLastActionTime(String deviceId) {
        for (Device device : activeDevices) {
            if (device.getDeviceID().equalsIgnoreCase(deviceId)) {
                device.updateLastActionTime();
            }
        }
    }
}
