package ru.myt.integration.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.myt.core.Event;
import ru.myt.data.settings.EnvSettings;
import ru.myt.integration.adapters.TrayAdapter;
import ru.myt.integration.simpleObject.CoreToSimpleObject;
import ru.myt.integration.simpleObject.SimpleEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/event")
public class EventResoursesV1 {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextEvent(@QueryParam("deviceId") String deviceId,
                                   @DefaultValue("") @QueryParam("lastEventId") String lastSuccessProcessedEventId) {
        try {
            Event event = TrayAdapter.getInstance().getNextEvent(deviceId, lastSuccessProcessedEventId);
            if (event != null) {
                SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
                if (EnvSettings.DEBUG_MODE) {
                    //System.out.println("Request /v1/event/ returned " + simpleEvent.getEventType() +  " successfully");
                    }
                return Response.status(200).entity(responseToJSONstr(simpleEvent)).build();
            } else {
                return Response.noContent().build();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        System.out.println("Request API description");
        return "RestApi published here to receive events";
    }

    private String responseToJSONstr(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
