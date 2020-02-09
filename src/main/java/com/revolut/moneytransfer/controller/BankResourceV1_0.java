package com.revolut.moneytransfer.controller;

import com.revolut.moneytransfer.controller.mapper.response.ErrorInfo;
import com.revolut.moneytransfer.controller.mapper.request.TransferPayload;
import com.revolut.moneytransfer.controller.mapper.response.TransferCompleted;
import com.revolut.moneytransfer.domain.Exceptions.InsufficientFundsException;
import com.revolut.moneytransfer.usecase.Bank;
import com.revolut.moneytransfer.usecase.BankImpl;
import com.revolut.moneytransfer.usecase.dto.Transfer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/bank/1.0")
public class BankResourceV1_0 {

    @POST
    @Path("/transfer")
    @Consumes("application/json")
    @Produces("application/json")
    public Response transfer(TransferPayload payload) {
        Bank bank = BankImpl.getInstance();
        Transfer payloadDto = new Transfer(
                payload.getDebitAccountId(),
                payload.getCreditAccountId(),
                payload.getAmount()
        );
        try {
            bank.transferMoney(payloadDto);
        } catch (InsufficientFundsException | NullPointerException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorInfo(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorInfo("Internal Server Error"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.ok(
                new TransferCompleted(
                        payload.getDebitAccountId(),
                        payload.getCreditAccountId(),
                        payload.getAmount()))
                .build();
    }
}
