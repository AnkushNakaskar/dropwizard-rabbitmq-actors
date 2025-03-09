package com.phonepe.core;

import com.rabbitmq.client.MessageProperties;
import com.phonepe.core.UnmanagedBaseActor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author ankush.nakaskar
 */
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/opentracing/event")
public class OpenTracingResource {


    @GET
    @Path("/{event}")
    public Response openTracingTesting(@PathParam("event") final String event) throws Exception {
        log.info("Into the resource layer with details of event {}",event);
        new UnmanagedBaseActor<>(null,null).publish(new Object(), MessageProperties.MINIMAL_PERSISTENT_BASIC);
        return Response.ok().entity("Success").build();

    }


}
