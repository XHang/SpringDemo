package com.SpringBoot.Service;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * jws_rs的SEI接口
 */
@Path("/sayHello")
@Service
public interface CxfRsService {
        @GET
        @Path("/{name}")
        @Produces(MediaType.TEXT_PLAIN)
        String sayHello(@PathParam("name") String name);
}
