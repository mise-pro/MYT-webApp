package ru.myt.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;

public class TrainsMock {

    public ArrayList<Train> getTrainsForAllRoutesForUser(User user) {
        ArrayList<Train> listOfTrainsForRoute = new ArrayList<>();

        long currentMs = System.currentTimeMillis();//15min
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();

        for (int step = -60; step < 60; step++) {
            Train train = new Train();
            train.setPriority(1);
            train.setUniqueID(user + ": fakeCode_" + step);
            train.setName(user + ": Поезд " + step);
            train.setDepartPlace("Точка " + step);
            train.setArrPlace("Точка " + step);
            train.setDepartureTime(Timestamp.valueOf(sdf.format(new Date(currentMs + step * 60 * 60 * 600))));//every 30min
            train.setArrivalTime(Timestamp.valueOf(sdf.format(new Date(currentMs + step * 60 * 60 * 600 + (random.nextInt(60) + 20) * 100000))));// about 1 hour
            listOfTrainsForRoute.add(train);
            //System.out.println(train.toFullString());
        }
        return listOfTrainsForRoute;
    }

}
