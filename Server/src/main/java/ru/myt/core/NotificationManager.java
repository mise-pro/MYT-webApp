package ru.myt.core;

import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;
import ru.myt.data.settings.EnvSettings;

import java.util.ArrayList;

public class NotificationManager {
    private static NotificationManager instance;
    private ArrayList<Notification> notifications;

    private NotificationManager() {
        notifications = new ArrayList<>();
    }

    protected static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    protected void createNotification(User user, Train currentTrain) {
        notifications.add(new Notification(user, currentTrain));
    }

    protected void clearNotifications(User user) {
        ArrayList<Notification> notificationsToDelete = new ArrayList<>();

        for (Notification notification : notifications) {
            if (notification.getUser().getUniqueID().equalsIgnoreCase(user.getUniqueID())) {
                notificationsToDelete.add(notification);
            }
        }
        notifications.removeAll(notificationsToDelete);
        if (EnvSettings.DEBUG_MODE) {
            System.out.println("NotificationManager: Notifications cleared!");
        }
    }

    protected void processNotifications() {
        for (Notification notification : notifications) {
            if (notification.isShouldBeNotified()) {
                if (EnvSettings.DEBUG_MODE) {
                    System.out.println("NotificationManager -> Router: NEW_TIME_FOR_SELECTED_TRAIN");
                }
                Event event = new Event(EventTypes.NEW_TIME_FOR_SELECTED_TRAIN, notification.getNearestNotification());
                Router.getInstance().sendMessage(event,notification.getUser());
            }
        }
    }
}
