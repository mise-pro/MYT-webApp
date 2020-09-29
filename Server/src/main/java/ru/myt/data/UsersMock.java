package ru.myt.data;

import ru.myt.core.entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class UsersMock {

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        User user = new User("XXX");
        UserSettings userSettings = new UserSettings(10, 45, 3);
        Route route = new Route("s2000009", "c10743", 1);
        user.setUserSettings(userSettings);
        user.addRoute(route);
        user.addDevice(new TrayDevice("XXX_e7185469-v1")); //v1
        user.addDevice(new TrayDevice("XXX_a383d93k-v2")); //v2
        user.addDevice(new TrayDevice("XXX_b6dk39f2"));
        users.add(user);

        user = new User("YYY");
        userSettings = new UserSettings(7, 20, 2);
        route = new Route("s2000009", "c10743", 2);
        user.setUserSettings(userSettings);
        user.addRoute(route);
        user.addDevice(new TrayDevice("YYY_e7185469"));
        user.addDevice(new TrayDevice("YYY_a383d93k"));
        user.addDevice(new TrayDevice("YYY_b6dk39f2"));
        users.add(user);

        return users;
    }
}
