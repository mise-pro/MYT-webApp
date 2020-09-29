package ru.myt.data;

import java.util.ArrayList;
import java.util.Collections;
import ru.myt.core.entity.*;
import ru.myt.data.settings.EnvSettings;

public class DataProvider implements IDataProvider {

    public static DataProvider instance;
    private ArrayList<Train> availableTrains = new ArrayList<>();


    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    private DataProvider() {

    }

    @Override
    public ArrayList<Train> getTrains(User user) {
        //TODO сделать учет переменной из файла конфига и выбирать ветку
        //TODO сделать получение файлов по пользователю User user
        if (EnvSettings.DEBUG_MODE) {
            System.out.println("Updating available trains...");
        }
        availableTrains.clear();
        try {

            //availableTrains.addAll(RSClient.getInstance().getTrainsForAllRoutesForUser(user));
            //place to replace for trains for debug
            TrainsMock debugTrains = new TrainsMock();
            availableTrains = debugTrains.getTrainsForAllRoutesForUser(user);

        } catch (Exception e) {
            System.out.println("Updating trains FAILED" + e.toString());
            availableTrains.clear();
            return availableTrains;
        }

        /*if (EnvSettings.DEBUG_MODE) {
            System.out.println("All Loaded Trains are:");

            for (Train train : availableTrains) {
                System.out.println(train.toFullString());
            }
        }*/
        deDublicateAndSortTrains();
        return availableTrains;
    }

    private void deDublicateAndSortTrains() {
        ArrayList<Train> uniqueTrains = new ArrayList<>();
        uniqueTrains.add(availableTrains.get(0));
        boolean isTrainSholdBeAdded;
        Train shouldBeRemovedTrain;

        //TODO may be BAD realization from the loop point of view 

        for (Train currentTrain : availableTrains) {
            isTrainSholdBeAdded = true;
            shouldBeRemovedTrain = null;
            for (Train candidateTrain : uniqueTrains) {
                switch (Train.isTheSameTrains(currentTrain, candidateTrain)) {
                    case notTheSame:
                        break;
                    case betterThenExistant:
                        shouldBeRemovedTrain = candidateTrain;
                        break;
                    case theSameOrWorseThenExistant:
                        isTrainSholdBeAdded = false;
                        break;
                }
            }
            if (isTrainSholdBeAdded) {
                uniqueTrains.add(currentTrain);
            }
            if (shouldBeRemovedTrain != null) {
                uniqueTrains.remove(shouldBeRemovedTrain);
            }
        }

        Collections.sort(uniqueTrains);
        availableTrains.clear();
        availableTrains.addAll(uniqueTrains);

        /*if (EnvSettings.DEBUG_MODE) {
            System.out.println("Unique and sorted trains are:");
            for (Train train : availableTrains) {
                System.out.println(train.toFullString());
            }
        }*/
    }

    public ArrayList<User> getAllUsers() {
        UsersMock mock = new UsersMock();
        return mock.getAllUsers();
    }

}
