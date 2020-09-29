package ru.myt.integration.rsclient;

import org.glassfish.jersey.SslConfigurator;
import ru.myt.core.entity.Route;
import ru.myt.core.entity.Train;
import ru.myt.core.entity.User;
import ru.myt.integration.rsclient.obj.ResponseYa;
import ru.myt.integration.rsclient.obj.Threads;
import ru.myt.data.settings.EnvSettings;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import static java.lang.System.currentTimeMillis;

public class RSClient {

    //private static final Logger LOGGER = LogManager.getLogger(RSClient.class);
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://api.rasp.yandex.net/v1.0/search/";
    private static RSClient instance;

    public static RSClient getInstance() {
        if (instance == null) {
            instance = new RSClient();
        }
        return instance;
    }

    private RSClient() {
        SslConfigurator sslConfig = SslConfigurator.newInstance();
        final SSLContext sslContext = sslConfig.createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();

    }

    public ArrayList<Train> getTrainsForAllRoutes(User user) throws Exception {

        ArrayList<Train> listOfTrainsForRoute = new ArrayList<>();
        try {
            for (Route route : user.getRoutes()) {

                URI uri = UriBuilder.fromUri(BASE_URI + "?apikey={apikey}&format=xml&from={from}&to={to}&lang=ru&date={date}&transport_types=suburban")
                        .resolveTemplate("apikey", EnvSettings.YA_KEY)
                        .resolveTemplate("from", route.getFrom())
                        .resolveTemplate("to", route.getTo())
                        .resolveTemplate("date", new SimpleDateFormat("yyyy-MM-dd").format(currentTimeMillis()))
                        .build();

                webTarget = client.target(uri);

                ResponseYa result = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResponseYa.class);

                //Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
                //Response responseCore = invocationBuilder.get();
                //System.out.println(responseCore.readEntity(String.class));
                //System.out.println (result_);
              //ResponseYa result = new ResponseYa();

                //Response result = (String) result_;

                //List<Threads> rawList = new List<Threads>();
                List<Threads> rawList = result.getThreads();

                for (Threads threads : rawList) {
                    Train train = new Train();
                    train.setPriority(route.getPriority());
                    train.setUniqueID(threads.getThread().getUid());
                    train.setName(threads.getThread().getShortTitle());
                    train.setDepartureTime(Timestamp.valueOf(threads.getDeparture()));
                    train.setArrivalTime(Timestamp.valueOf(threads.getArrival()));
                    train.setFinalDestinationPlace(threads.getThread().getShortTitle());

                    if ((threads.getFrom().getShortTitle()).length() > 0) {
                        train.setDepartPlace(threads.getFrom().getShortTitle());
                    } else {
                        train.setDepartPlace(threads.getFrom().getTitle());
                    }

                    if ((threads.getTo().getShortTitle()).length() > 0) {
                        train.setArrPlace(threads.getTo().getShortTitle());
                    } else {
                        train.setArrPlace(threads.getTo().getTitle());
                    }

                    listOfTrainsForRoute.add(train);
                }
            }
            if (EnvSettings.DEBUG_MODE) {
                System.out.println("Trains updated from Yandex succesfully");
            }

        } finally {
            close();
        }
        return listOfTrainsForRoute;
    }

    public void close() {
        client.close();
        client = null;
        instance = null;
    }

}
