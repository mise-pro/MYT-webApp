package ru.myt.core;

import ru.myt.core.entity.*;
import ru.myt.data.DataProvider;

import java.util.ArrayList;

public class UserManager {
    private static UserManager instance;
    private ArrayList<User> activeUsers;

    private UserManager() {
        activeUsers = new ArrayList<>();
    }
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    protected ArrayList<User> getAllActiveUsers() {
        return activeUsers;
    }


    public User getUserByDeviceId(String deviceId) {

        //2 steps  : activeUser first, then allAvailable

        for (User user: activeUsers)
        {
            for (Device device: user.getDevices())
            {
                if (device.getDeviceID().equalsIgnoreCase(deviceId))
                {
                    return user;
                }
            }
        }

        for (User user: getAllUsers())
        {
            for (Device device: user.getDevices())
            {
                if (device.getDeviceID().equalsIgnoreCase(deviceId))
                {
                    return user;
                }
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers()
    {
        return DataProvider.getInstance().getAllUsers();
    }


    public void addActiveUserByDeviceId(String deviceIdForActivate) {
        User user = getUserByDeviceId(deviceIdForActivate);
        DeviceManager.getInstance().activateDevice(user, deviceIdForActivate);
        activeUsers.add(user);

    }
    protected void removeActiveUserByDeviceId(String deviceIdForActivate) {
        //todo надо сделать проверку, чтобы при удалении проверялось, что он там точно есть
        User user = getUserByDeviceId(deviceIdForActivate);
        DeviceManager.getInstance().deactivateDevice(deviceIdForActivate);
        if (!DeviceManager.getInstance().checkIfUserHaveAnyActiveDevice(user)){
        activeUsers.remove(user);}
    }

}
