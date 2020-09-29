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
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

@Path("/v2/event")
public class EventResoursesV2 {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    //private static ExecutorService service = Executors.newFixedThreadPool(10);

    private static String responseToJSONstr(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getNextEvent(@QueryParam("deviceId") String deviceId,
                             @DefaultValue("") @QueryParam("lastEventId") String lastSuccessProcessedEventId,
                             @Suspended final AsyncResponse aResponse) {
        aResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                //service.shutdown();
                asyncResponse.resume(
                        Response.status(Response.Status.REQUEST_TIMEOUT).entity("Operation time out").build());
            }
        });
        aResponse.setTimeout(25, TimeUnit.SECONDS);

        //service = Executors.newSingleThreadExecutor();
        //service.execute( () -> {
            try {
                Event event = null;
                long beginTime = System.currentTimeMillis();
                while (beginTime+15*1000>System.currentTimeMillis()) {
                    event = TrayAdapter.getInstance().getNextEvent(deviceId, lastSuccessProcessedEventId);
                    if (event != null) {
                        SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
                        aResponse.resume(Response.status(200).entity(responseToJSONstr(simpleEvent)).build());
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                aResponse.resume(Response.status(500).build());
            }
       //


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Event event = null;
                    while (true) {
                        event = TrayAdapter.getInstance().getNextEvent(deviceId, lastSuccessProcessedEventId);
                        if (event != null) {
                            break;
                        }
                        Thread.sleep(1000);
                    }
                    SimpleEvent simpleEvent = CoreToSimpleObject.getInstance().convertEvent(event);
                    aResponse.resume(Response.status(200).entity(responseToJSONstr(simpleEvent)).build());
                } catch (Exception e) {
                    e.printStackTrace();
                    aResponse.resume(Response.status(500).build());
                }
            }
        }).start();*/


    }
}
