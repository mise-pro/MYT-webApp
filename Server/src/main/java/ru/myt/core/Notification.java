package ru.myt.core;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;

public class Notification {

    private Train currentTrain;
    private boolean shouldBeNotified = false;
    private Map notificationMap = new HashMap<Integer, Boolean>();//<minutes before Train>,<already notified?>
    private int nearestNotificationInMinutes = 0;
    private User user;

    protected Notification(User user, Train selectedTrain) {
        this.currentTrain = selectedTrain;
        this.user=user;

        System.out.println(String.format("New notification Created! (User=%s)", user));

        Calendar cal = Calendar.getInstance();
        int notificationStep = 0;

        while (notificationStep * 60 * 1000 < user.getUserSettings().getNotifyPeriodBeforeSelectedTrain() * 60 * 1000) {

            if (cal.getTimeInMillis() < currentTrain.getDepartureTime().getTime() - user.getUserSettings().getTimeToGetRailway() * 60 * 1000 - notificationStep * 60 * 1000) {
                notificationMap.put(notificationStep, false);
            } else {
                notificationMap.put(notificationStep, true);
            }
            notificationStep = notificationStep + user.getUserSettings().getNotifyPeriodBeforeSelectedTrain();
        }
    }

    private void refreshNotification() {
        Calendar cal = Calendar.getInstance();
        int notificationStep = user.getUserSettings().getNotifyPeriodBeforeSelectedTrain();
        while (notificationStep >= 0) {
            if (notificationMap.get(notificationStep) == null) {
                notificationStep = notificationStep - 1;
                continue;
            }
            if (!(boolean) notificationMap.get(notificationStep)
                    && (cal.getTimeInMillis() > currentTrain.getDepartureTime().getTime() - user.getUserSettings().getTimeToGetRailway() * 60 * 1000 - notificationStep * 60 * 1000)) {
                notificationMap.put(notificationStep, true);
                shouldBeNotified = true;
                nearestNotificationInMinutes = notificationStep;
            }
            notificationStep = notificationStep - user.getUserSettings().getNotifyIntervalPeriodBeforeSelectedTrain();
        }
    }

    protected boolean isShouldBeNotified() {
        refreshNotification();
        return shouldBeNotified;
    }

    protected int getNearestNotification() {
        shouldBeNotified = false;
        return nearestNotificationInMinutes;
    }

    protected User getUser() {
        return user;
    }

}