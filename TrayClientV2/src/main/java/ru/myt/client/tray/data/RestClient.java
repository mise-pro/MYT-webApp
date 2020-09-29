package ru.myt.client.tray.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.glassfish.jersey.client.ClientConfig;
import ru.myt.client.tray.settings.TraySettings;
import ru.myt.core.EventTypes;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.UserSettings;
import ru.myt.integration.simpleObject.SimpleEvent;
//import ru.myt.core.Event;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Future;

public class RestClient {

    private static RestClient instance;
    private final Client client;
    private final URI uri;
    private WebTarget service;
    private Response response = null;
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    protected static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    private RestClient() {
        ClientConfig config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        uri = UriBuilder.fromUri(TraySettings.CONNECTION_ENDPOINT).build();
    }

    protected void setSelectedTrain(String deviceId, String newTrainId) {
        try {
            service = client.target(uri).path("command").path("setTrain")
                    .queryParam("deviceId", deviceId)
                    .queryParam("newTrainId", newTrainId);
            Invocation.Builder invocationBuilder = service.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.post(null);
            if (response.getStatus() != 200) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("Endpoint answer code was: " + response.getStatus());
                    System.out.println("Endpoint answer was: " + response.readEntity(String.class));
                }
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION: unable to send command to select new train");

            try {
                System.out.println("Endpoint answers was: " + response.readEntity(String.class));
            } catch (Exception ex_) {
            }
        }
    }

    protected void activateDevice(String deviceId) {
        try {
            service = client.target(uri).path("command").path("activateDevice")
                    .queryParam("deviceId", deviceId);
            Invocation.Builder invocationBuilder = service.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.post(null);
            if (response.getStatus() != 200) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("Endpoint answer code was: " + response.getStatus());
                    System.out.println("Endpoint answer was: " + response.readEntity(String.class));
                }
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION: unable to send command to activate device");

            try {
                System.out.println("Endpoint answers was: " + response.readEntity(String.class));
            } catch (Exception ex_) {
            }
        }
    }

    protected void deactivateDevice(String deviceId) {
        try {
            service = client.target(uri).path("command").path("deactivateDevice")
                    .queryParam("deviceId", deviceId);
            Invocation.Builder invocationBuilder = service.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.post(null);
            if (response.getStatus() != 200) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("Endpoint answer code was: " + response.getStatus());
                    System.out.println("Endpoint answer was: " + response.readEntity(String.class));
                }
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION: unable to send command to deactivate device");

            try {
                System.out.println("Endpoint answers was: " + response.readEntity(String.class));
            } catch (Exception ex_) {
            }
        }

    }

    protected SimpleEvent getNextEvent(String deviceId, String lastSuccessProcessedEventId) {
        String output = null;
        try {
            if (lastSuccessProcessedEventId != null) {
                service = client.target(uri).path("event").queryParam("deviceId", deviceId)
                        .queryParam("lastEventId", lastSuccessProcessedEventId);
            } else {
                service = client.target(uri).path("event").queryParam("deviceId", deviceId);
            }
            //Invocation.Builder invocationBuilder = service.request(MediaType.APPLICATION_JSON); //from sync to async
            AsyncInvoker asyncInvoker = service.request(MediaType.APPLICATION_JSON).async();
            Future<Response> responseFuture = asyncInvoker.get();
            response = responseFuture.get();
            output = response.readEntity(String.class);
            if (response.getStatus() != 200 && response.getStatus() != 204) {
                if (TraySettings.DEBUG_MODE) {
                    System.out.println("Endpoint answer code was: " + response.getStatus());
                    System.out.println("Endpoint answers was: " + output);
                }
                return null;
            }
            if ((output.equalsIgnoreCase(""))) {
                return null;
            }
            SimpleEvent event = mapper.readValue(output, SimpleEvent.class);
            //bad custom implementation because this class can't be unmarshall automatically
            processEventPayload(event, output);
            return event;
        } catch (Exception ex) {
            System.out.println("EXCEPTION: unable to handle new event");
            try {
                System.out.println("Endpoint answers was: " + output);
            } catch (Exception ex_) {
                return null;
            }
            return null;
        }
    }

    private void processEventPayload(SimpleEvent event, String output) throws IOException {
        JsonNode root = mapper.readTree(output);
        JsonNode result = root.findValue("eventPayload");
        if (event.getEventType().toString().equalsIgnoreCase(EventTypes.DEVICE_ACTIVATION.toString())) {
            UserSettings userSettings = mapper.readValue(result.toString(), UserSettings.class);
            event.setEventPayload(userSettings);
        }
        if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NEAREST_TRAINS_CHANGED.toString()) ||
                event.getEventType().toString().equalsIgnoreCase(EventTypes.AVAILABLE_TRAINS_CHANGED.toString())) {
            List<Train> trainList = mapper.readValue(result.toString(), new TypeReference<List<Train>>(){});
            event.setEventPayload(trainList);
        }
        if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NOTIF.toString())){
            String msg = (String) event.getEventPayload();
            event.setEventPayload(msg);
}
        if (event.getEventType().toString().equalsIgnoreCase(EventTypes.NEW_TIME_FOR_SELECTED_TRAIN.toString())){
            int digits = (int) event.getEventPayload();
            event.setEventPayload(digits);
        }
    }


}
