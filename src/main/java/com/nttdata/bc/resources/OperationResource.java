package com.nttdata.bc.resources;

import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;

import com.nttdata.bc.exceptions.ExceptionResponse;
import com.nttdata.bc.models.Operation;
import com.nttdata.bc.services.IOperationService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/operations")
public class OperationResource {
    @Inject
    Logger logger;

    @Inject
    private IOperationService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timeout(value = 5000)
    @Fallback(fallbackMethod = "fallbackRecommendations")
    @Transactional
    public Response insert(@Valid Operation obj) {
        Operation operation = this.service.insert(obj);
        return Response.status(Status.CREATED).entity(operation).build();
    }

    public Response fallbackRecommendations(@Valid Operation obj) {
        ExceptionResponse er = new ExceptionResponse(
                LocalDateTime.now(),
                "Un error a ocurrido.");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(er)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // @PUT
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Transactional
    // public Response update(@PathParam("id") Integer id, @Valid Operation obj) {
    // logger.info("Inicio ::: update ::: " + obj);
    // obj.setOperationId(id);
    // Operation operation = this.service.update(obj);
    // return Response.status(Status.CREATED).entity(operation).build();
    // }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fintAll() {
        List<Operation> accounts = this.service.findAll();
        return Response.ok(accounts).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id) {
        Operation operation = this.service.findById(id);
        return Response.ok(operation).build();
    }

    // @PUT
    // @Path("/{id}")
    // public Response delete(@PathParam("id") Integer id) {
    // this.service.delete(id);
    // return Response.noContent().build();
    // }
}
