package ru.myt.core.entity;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

public class User {
    private String uniqueID;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<Device> devices = new ArrayList<>();
    private boolean isTrainSelected = false;
    private UserSettings userSettings = null;

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public User(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public void removeRoute(Route route) throws Exception {
        throw new Exception("Should be implemented!!");
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) throws Exception {
        throw new Exception("Should be implemented!!");
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    /*
    public boolean isTrainSelected() {
            return isTrainSelected;
        }

    public void setTrainSelected(boolean value) {
        this.isTrainSelected = value;
    }*/

    public String toString(){
        return getUniqueID();
    }

    private void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    private void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
}
