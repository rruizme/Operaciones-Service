package com.nttdata.bc.clients;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.nttdata.bc.models.Account;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/accounts")
@RegisterRestClient
public interface IAccountRestClient {

    @GET
    @Path("/{id}")
    Account fintById(@PathParam("id") Integer id);

    @GET
    @Path("/debit-card/{debitCardId}")
    List<Account> findDebitCardId(@PathParam("debitCardId") Integer debitCardId);

    @PUT
    @Path("/{id}")
    Account update(@PathParam("id") Integer id, Account obj);

}
