package ru.myt.core.entity;

import java.io.Serializable;

public class UserSettings implements Serializable {
    private int timeToGetRailway; //<!--in minutes, time to get nearest departure station or railway-->
    private int notifyPeriodBeforeSelectedTrain; //<!--in minutes, period before depare and timeToGetDepStation-->
    private int notifyIntervalPeriodBeforeSelectedTrain; //<!--in minutes, create notification every ... -->

    public UserSettings() {
    }

    public int getTimeToGetRailway() {
        return timeToGetRailway;
    }

    public void setTimeToGetRailway(int timeToGetRailway) {
        this.timeToGetRailway = timeToGetRailway;
    }

    public int getNotifyPeriodBeforeSelectedTrain() {
        return notifyPeriodBeforeSelectedTrain;
    }

    public void setNotifyPeriodBeforeSelectedTrain(int notifyPeriodBeforeSelectedTrain) {
        this.notifyPeriodBeforeSelectedTrain = notifyPeriodBeforeSelectedTrain;
    }

    public int getNotifyIntervalPeriodBeforeSelectedTrain() {
        return notifyIntervalPeriodBeforeSelectedTrain;
    }

    public void setNotifyIntervalPeriodBeforeSelectedTrain(int notifyIntervalPeriodBeforeSelectedTrain) {
        this.notifyIntervalPeriodBeforeSelectedTrain = notifyIntervalPeriodBeforeSelectedTrain;
    }

    public UserSettings (int timeToGetRailway, int notifyPeriodBeforeSelectedTrain, int notifyIntervalPeriodBeforeSelectedTrain)
    {
        this.notifyIntervalPeriodBeforeSelectedTrain = notifyIntervalPeriodBeforeSelectedTrain;
        this.notifyPeriodBeforeSelectedTrain = notifyPeriodBeforeSelectedTrain;
        this.timeToGetRailway = timeToGetRailway;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "timeToGetRailway=" + timeToGetRailway +
                ", notifyPeriodBeforeSelectedTrain=" + notifyPeriodBeforeSelectedTrain +
                ", notifyIntervalPeriodBeforeSelectedTrain=" + notifyIntervalPeriodBeforeSelectedTrain +
                '}';
    }
}
