package ru.myt.integration.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/v2/command")
public class CommandResoursesV2 {

    @POST
    @Path("setTrain")
    public Response setSelectedTrain(@QueryParam("deviceId") String deviceId,
                                       @QueryParam("newTrainId") String newTrainId) {
        return new CommandResoursesV1().setSelectedTrain(deviceId,newTrainId);
    }

    @POST
    @Path("activateDevice")
    public Response activateDevice(@QueryParam("deviceId") String deviceId) {
        return new CommandResoursesV1().activateDevice(deviceId);
    }

    @POST
    @Path("deactivateDevice")
    public Response deactivateDevice(@QueryParam("deviceId") String deviceId) {
        return new CommandResoursesV1().deactivateDevice(deviceId);
    }

    @POST
    @Path("mock/activateDevice")
    public Response activateDeviceMock() {
        return new CommandResoursesV1().activateDeviceMock();
    }

    @POST
    @Path("mock/deactivateDevice")
    public Response deactivateDeviceMock() {
        return new CommandResoursesV1().deactivateDeviceMock();
    }

    @POST
    @Path("mock/setTrain")
    public Response setSelectedTrainMock(@QueryParam("deviceId") String deviceId,
                                        @QueryParam("newTrainId") String newTrainId) {
        return new CommandResoursesV1().setSelectedTrainMock(deviceId,newTrainId);
    }
}
