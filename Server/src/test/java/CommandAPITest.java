import org.glassfish.jersey.client.ClientConfig;
import ru.myt.core.Event;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class CommandAPITest {

    public static void main(String args[]) {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        //mock/activateDevice
        URI uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/mock/activateDevice").build();
        WebTarget service = client.target(uri);
        Invocation.Builder invocationBuilder = service.request(MediaType.APPLICATION_XML);
        Response response = invocationBuilder.post(null);
        System.out.println(response.toString());

        //mock/setTrain
        uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/mock/setTrain").build();
        service = client.target(uri).queryParam("deviceId", "100").queryParam("newTrainId", "200");
        invocationBuilder = service.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(null);
        System.out.println(response.toString());

        //activateDevice
        uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/activateDevice").build();
        service = client.target(uri).queryParam("deviceId", "XXX_e7185469");
        invocationBuilder = service.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(null);
        System.out.println(response.toString());

        //setTrain/
        uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/setTrain").build();
        service = client.target(uri).queryParam("deviceId", "XXX_e7185469").queryParam("newTrainId", "fakeTrain");
        invocationBuilder = service.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(null);
        System.out.println(response.toString());

        //mock/deactivateDevice
        uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/mock/deactivateDevice").build();
        service = client.target(uri);
        invocationBuilder = service.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(null);
        System.out.println(response.toString());

        //deactivateDevice
        uri = UriBuilder.fromUri("http://localhost:8080/MYTServer/api/v1/command/deactivateDevice").build();
        service = client.target(uri).queryParam("deviceId", "XXX_e7185469");
        invocationBuilder = service.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(null);
        System.out.println(response.toString());

    }
}