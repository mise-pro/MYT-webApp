import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.glassfish.jersey.client.ClientConfig;
import ru.myt.core.entity.Device;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.TrayDevice;
import ru.myt.core.entity.UserSettings;
import ru.myt.integration.simpleObject.SimpleEvent;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CollectionTest {

    public static void main(String args[]) {


        ArrayList<Device> activeDevices = new ArrayList<>();

        Device nDev = new TrayDevice("101");
        //activeDevices.add(nDev);

        nDev = new TrayDevice("102");
        //activeDevices.add(nDev);

        Device deviceToDeactivate = null;
        for (Device device : activeDevices) {
            if (device.getDeviceID().equalsIgnoreCase("1012")) {
                device.deactivate();
                deviceToDeactivate = device;
            }
        }
        activeDevices.remove(nDev);

    }

}

