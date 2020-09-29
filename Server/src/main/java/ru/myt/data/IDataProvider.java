package ru.myt.data;

import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;

import java.util.ArrayList;

public interface IDataProvider {

    ArrayList<Train> getTrains(User user);
    //User getUserInfo(User user);
    //User getUserByToken(String token);
}
