package ru.myt.core;

import java.util.ArrayList;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.UserSettings;

public interface IUserNotificationEvents {
    void availableTrainsChangedEvent(ArrayList<Train> availableTrains);
    void nearestTrainsChangedEvent(ArrayList<Train> nearestTrains);
    void selectedTrainNotification(int timeBeforeSelectedTrain);
    void textNotification(String text);
    void userSettingsEvent(UserSettings userSettings);
}
