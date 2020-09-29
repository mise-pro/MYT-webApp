package ru.myt.integration.simpleObject;

import ru.myt.core.Event;

public class CoreToSimpleObject {

    private static CoreToSimpleObject instance;

    private CoreToSimpleObject() {
    }

    public static CoreToSimpleObject getInstance() {
        if (instance == null) {
            instance = new CoreToSimpleObject();
        }
        return instance;
    }

    public SimpleEvent convertEvent(Event event) {
        if (event != null) {
            return new SimpleEvent(event);
        } else {
            return null;
        }
    }
}
