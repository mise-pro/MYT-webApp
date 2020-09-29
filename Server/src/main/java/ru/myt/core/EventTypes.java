package ru.myt.core;

public enum EventTypes {
    DEVICE_ACTIVATION, //get user settings for initialization
    DEVICE_DEACTIVATION,
    NOTIF, //send noification (aka push) to appropriate devices
    NEAREST_TRAINS_CHANGED,
    AVAILABLE_TRAINS_CHANGED,
    NEW_TRAIN_SELECTED,
    NEW_TIME_FOR_SELECTED_TRAIN, //send noification time to selected train appropriate devices
    EVENT_CLEAR_NOTIF,//clear all notif for user
    CREATE_NOTIFICATION_FOR_SELECTED_TRAIN;


    EventTypes() {
    }
}



