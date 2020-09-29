package ru.myt.client.tray.settings;

public class TraySettings {
    //tray settings
    public static final boolean DEBUG_MODE  = XMLreader.getInstance().isDebugMode();
    public static final String DEVICE_ID = XMLreader.getInstance().getDeviceId();
    public static final String CONNECTION_TYPE = XMLreader.getInstance().getConnectionType();
    public static final String CONNECTION_ENDPOINT  = XMLreader.getInstance().getConnectionEndpoint();
}
