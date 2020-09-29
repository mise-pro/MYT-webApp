package ru.myt.core;

import ru.myt.core.entity.User;

import java.util.Timer;
import java.util.TimerTask;

public class SyncTimer {

    protected SyncTimer () {
        System.out.println("Timer Created");
        makeSyncAction();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                makeSyncAction();
            }
        }, 1 * 10 * 5000, 1 * 10 * 5000);
    }

    private void makeSyncAction () {
        /*if (EnvSettings.DEBUG_MODE) {
            System.out.println("Timer Updates");
        }*/
        try {
            for (User activeUser: UserManager.getInstance().getAllActiveUsers()) {
                RouteController.getInstance().checkRouteChangeForUser(activeUser);
            }
            NotificationManager.getInstance().processNotifications();
        }
        catch
        (Exception e) {
            System.out.println(e.toString());
        }
    }

}
