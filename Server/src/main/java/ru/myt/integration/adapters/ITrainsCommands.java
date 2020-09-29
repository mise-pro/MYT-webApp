package ru.myt.integration.adapters;

import ru.myt.core.Event;

public interface ITrainsCommands {
    void setSelectedTrain(String deviceId, String newTrainId);
    void activateDevice(String deviceId);
    void deactivateDevice(String deviceId);
    Event getNextEvent (String deviceId, String lastSuccessProcessedEventId);
}
