package ru.myt.integration.rest;

import ru.myt.integration.adapters.TrayAdapter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/v1/command")
public class CommandResoursesV1 {

    @POST
    @Path("setTrain")
    public Response setSelectedTrain(@QueryParam("deviceId") String deviceId,
                                       @QueryParam("newTrainId") String newTrainId) {
        if ((deviceId == null) || (newTrainId == null)) {
            return Response.noContent().build();
        }
        try {
            //System.out.println("Request received successfully with newTrainId=" + newTrainId);
            TrayAdapter.getInstance().setSelectedTrain(deviceId, newTrainId);
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return Response.status(200).build();

    }

    @POST
    @Path("activateDevice")
    public Response activateDevice(@QueryParam("deviceId") String deviceId) {
        if (deviceId == null) {
            return Response.noContent().build();
        }
        try {
            TrayAdapter.getInstance().activateDevice(deviceId);
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return Response.status(200).build();
    }

    @POST
    @Path("deactivateDevice")
    public Response deactivateDevice(@QueryParam("deviceId") String deviceId) {
        if (deviceId == null) {
            return Response.noContent().build();
        }
        try {
            TrayAdapter.getInstance().deactivateDevice(deviceId);
        } catch (Exception ex) {
            return Response.serverError().build();
        }
        return Response.status(200).build();
    }

    @POST
    @Path("mock/activateDevice")
    public Response activateDeviceMock() {
        System.out.println("Request /mock/activateDevice received successfully");
        return Response.status(200).build();
    }

    @POST
    @Path("mock/deactivateDevice")
    public Response deactivateDeviceMock() {
        System.out.println("Request /mock/deactivateDevice received successfully");
        return Response.status(200).build();
    }

    @POST
    @Path("mock/setTrain")
    public Response setSelectedTrainMock(@QueryParam("deviceId") String deviceId,
                                        @QueryParam("newTrainId") String newTrainId) {
        if ((deviceId == null) || (newTrainId == null)) {
            return Response.noContent().build();
        } else {
            System.out.println("Request mock/setTrain/{deviceId}/{newTrainId} received successfully with newTrainId=" + newTrainId);
            return Response.status(200).build();
        }
    }
}
