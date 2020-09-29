package ru.myt.integration.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.myt.core.Event;
import ru.myt.core.EventTypes;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;
import ru.myt.core.entity.UserSettings;
import ru.myt.data.TrainsMock;
import ru.myt.data.UsersMock;
import ru.myt.integration.simpleObject.CoreToSimpleObject;
import ru.myt.integration.simpleObject.SimpleEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/mock/event")
public class MockEventResoursesV1 {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    @GET
    @Path("1/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent1(@QueryParam("lastEventId") String lastSuccessProcessedEventId) {
        try {
            Event event = new Event(EventTypes.DEVICE_ACTIVATION, null);
            SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
            String result = responseToJSONstr (simpleEvent);
            return Response.status(200).entity(result).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(null).build();
        }
    }

    @GET
    @Path("2/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent2(@QueryParam("lastEventId") String lastSuccessProcessedEventId){
        try {
            TrainsMock debugTrains = new TrainsMock();
            ArrayList<Train>availableTrains = debugTrains.getTrainsForAllRoutesForUser(new User("test1"));
            Event event = new Event(EventTypes.AVAILABLE_TRAINS_CHANGED, availableTrains);
            SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
            String result = responseToJSONstr (simpleEvent);
            return Response.status(200).entity(result).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(null).build();
        }
    }


    @GET
    @Path("3/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent3(@QueryParam("lastEventId") String lastSuccessProcessedEventId) {
        try {
            UsersMock mock = new UsersMock();
            ArrayList<User> users = mock.getAllUsers();
            UserSettings us = users.get(0).getUserSettings();
            Event event = new Event(EventTypes.DEVICE_ACTIVATION, us);
            SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
            String result = responseToJSONstr (simpleEvent);
            return Response.status(200).entity(result).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(null).build();
        }
    }

    @GET
    @Path("4/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent4(@QueryParam("lastEventId") String lastSuccessProcessedEventId) {
        try {
            Event event = new Event(EventTypes.NOTIF, "new text!!");
            SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
            String result = responseToJSONstr (simpleEvent);
            return Response.status(200).entity(result).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(null).build();
        }
    }


    @GET
    @Path("5/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent5(@QueryParam("lastEventId") String lastSuccessProcessedEventId) {
        try {
            Event event = new Event(EventTypes.NEW_TIME_FOR_SELECTED_TRAIN, 365);
            SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
            String result = responseToJSONstr (simpleEvent);
            return Response.status(200).entity(result).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(null).build();
        }
    }

    private String responseToJSONstr (Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
