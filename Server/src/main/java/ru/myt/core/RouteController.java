package ru.myt.core;

import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;
import ru.myt.data.DataProvider;
import ru.myt.data.IDataProvider;
import ru.myt.data.settings.EnvSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class RouteController {
    private ArrayList<Train> availableTrains = new ArrayList<>();
    private ArrayList<Train> nearestTrains = new ArrayList<>();
    private static RouteController instance;

    private int lastDayOfYearUpdated = 0; //to control interval of data refresh
    private int countTrainsAvailableToCatch = 0;
    private boolean isAvailableTrainsChanged = false;
    private boolean isNearestTrainsChanged = false;

    private IDataProvider data;

    private RouteController(IDataProvider dataSource) {
        this.data = dataSource;
    }

    public static RouteController getInstance() {
        if (instance == null) {
            instance = new RouteController(DataProvider.getInstance()); //todo dataProviders used 2 times - too much
        }
        return instance;
    }

    private void notifyAboutRouteChangesListeners(User user) {

        if (isNearestTrainsChanged) {
            if (EnvSettings.DEBUG_MODE) {
                System.out.println("RouteController: NearestTrains Changed");
            }
            Event event = new Event(EventTypes.NEAREST_TRAINS_CHANGED, nearestTrains);
            Router.getInstance().sendMessage(event, user);
            if (nearestTrains.size() == 0) {
                event = new Event(EventTypes.NOTIF, "Ушел последний поезд :)");
                Router.getInstance().sendMessage(event, user);
            }
            isNearestTrainsChanged = false;
        }
        if (isAvailableTrainsChanged) {
            if (EnvSettings.DEBUG_MODE) {
                System.out.println("RouteController: AvailableTrainsChanged to router");
            }
            Event event = new Event(EventTypes.AVAILABLE_TRAINS_CHANGED, availableTrains);
            Router.getInstance().sendMessage(event, user);

            sendActualTextNotification(user, null);
        }
        isAvailableTrainsChanged = false;
    }

    protected void sendActualTextNotification(User user, String destinationDeviceId) {
        Event event;
        if (!availableTrains.isEmpty()) {
            event = new Event(EventTypes.NOTIF, "Расписание на сегодня загружено");
            if (destinationDeviceId == null) {
                Router.getInstance().sendMessage(event, user);
            } else {
                event.setDestinationDeviceId(destinationDeviceId);
                Router.getInstance().sendMessage(event, user);
            }
        } else {
            event = new Event(EventTypes.NOTIF, "Нет поездов или проблема с настройками");

            if (destinationDeviceId == null) {
                Router.getInstance().sendMessage(event, user);
            } else {
                event.setDestinationDeviceId(destinationDeviceId);
                Router.getInstance().sendMessage(event, user);
            }
        }

    }

    private void updateAvailableTrains(User user) {
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_YEAR) != lastDayOfYearUpdated) {

            try {
                availableTrains = data.getTrains(user);
                lastDayOfYearUpdated = cal.get(Calendar.DAY_OF_YEAR);

            } catch (Exception e) {
                System.out.println("Updating trains FAILED" + e.toString());
                availableTrains.clear();

            } finally {
                isAvailableTrainsChanged = true;
            }
        }
    }

    //todo use user!
    private void calculateNearestTrains(User user) {
        if (!availableTrains.isEmpty()) {

            ArrayList<Train> newNearestTrains = new ArrayList<>();
            int newCountTrainsAvailableToCatch = 0;
            long curTime = System.currentTimeMillis();

            for (Train train : availableTrains) {
                if (train.getDepartureTime().getTime() > (curTime)) {
                    newNearestTrains.add(train);
                    if (train.isEnoughTimeToCatchTrain(user.getUserSettings().getTimeToGetRailway())) {
                        newCountTrainsAvailableToCatch++;
                    }
                }
            }
            if (nearestTrains.size() != newNearestTrains.size()) {
                nearestTrains.clear();
                nearestTrains.addAll(newNearestTrains);
                isNearestTrainsChanged = true;
            }
            if (newCountTrainsAvailableToCatch != countTrainsAvailableToCatch) {
                countTrainsAvailableToCatch = newCountTrainsAvailableToCatch;
                isNearestTrainsChanged = true;
            }
        }
    }

    protected void setSelectedTrain(String newTrainId, User user) {
        for (Train currentAvailableTrain : availableTrains) {
            if (currentAvailableTrain.getUniqueID().equals(newTrainId)) {
                isNearestTrainsChanged = true;
                if (currentAvailableTrain.isSelected()) {
                    currentAvailableTrain.setSelected(false);
                    Event event = new Event(EventTypes.EVENT_CLEAR_NOTIF, null);
                    Router.getInstance().sendMessage(event, user);
                } else {
                    currentAvailableTrain.setSelected(true);

                    Event event = new Event(EventTypes.EVENT_CLEAR_NOTIF, null);
                    Router.getInstance().sendMessage(event, user);

                    event = new Event(EventTypes.CREATE_NOTIFICATION_FOR_SELECTED_TRAIN, currentAvailableTrain);
                    Router.getInstance().sendMessage(event, user);
                }
            } else {
                currentAvailableTrain.setSelected(false);
            }
        }
        notifyAboutRouteChangesListeners(user);
    }

    protected void checkRouteChangeForUser(User user) {
        updateAvailableTrains(user);
        calculateNearestTrains(user);
        notifyAboutRouteChangesListeners(user);
    }


    protected void getFullRouteInfoForDevice(User user, String destinationDeviceId) {
        updateAvailableTrains(user);
        calculateNearestTrains(user);


        Router.getInstance().sendMessage(new Event(EventTypes.AVAILABLE_TRAINS_CHANGED,
                availableTrains,destinationDeviceId), user);

        Router.getInstance().sendMessage(new Event(EventTypes.NEAREST_TRAINS_CHANGED,
                nearestTrains,destinationDeviceId), user);

        sendActualTextNotification(user, destinationDeviceId);
        isNearestTrainsChanged = false;
        isAvailableTrainsChanged = false;
    }


}
