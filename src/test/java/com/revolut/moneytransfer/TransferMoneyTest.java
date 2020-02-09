package com.revolut.moneytransfer;

import com.revolut.moneytransfer.controller.BankResourceV1_0;
import com.revolut.moneytransfer.usecase.BankImpl;
import com.revolut.moneytransfer.usecase.dto.CreateAccount;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TransferMoneyTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(BankResourceV1_0.class);
    }

    @BeforeClass
    public static void init() {
        for (Integer i=1; i <= 10; i++) {
            BankImpl.getInstance().createAccount(new CreateAccount(i.toString(), new BigDecimal(i)));
        }
    }

    @Test
    public void givenTransferMoney_whenFieldsAreCorrect_thenResponseCodeIsOk() {
        final Response response = target("/bank/1.0/transfer").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{\n" +
                        "  \"debit_account_id\": 10,\n" +
                        "  \"credit_account_id\": 1,\n" +
                        "  \"amount\": 9\n" +
                        "}"));

        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("Http Status should be: ", HttpStatus.OK_200, response.getStatus());
        assertTrue(response.hasEntity());
        final String json = response.readEntity(String.class);
        assertEquals("Error body should be: ","{\"debit_account_id\":10,\"credit_account_id\":1,\"amount\":9}", json);
    }

    @Test
    public void givenTransferMoney_whenPayloadIsInvalid_thenResponseCodeIsBadRequest() {
        final Response response = target("/bank/1.0/transfer").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{\n" +
                        "  \"debit_account_id\": 123,\n" +
                        "  \"credit_account_id\": 124\n" +
                        "}"));

        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("Http Status should be: ", HttpStatus.BAD_REQUEST_400, response.getStatus());
        assertTrue(response.hasEntity());
        final String json = response.readEntity(String.class);
        assertEquals("Error body should be: ","{\"message\":\"Amount cannot be null\"}", json);
    }

    @Test
    public void givenTransferMoney_whenAmountIsNotPositive_thenResponseCodeIsBadRequest() {
        final Response response = target("/bank/1.0/transfer").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{\n" +
                        "  \"debit_account_id\": 123,\n" +
                        "  \"credit_account_id\": 124,\n" +
                        "  \"amount\": -2\n" +
                        "}"));

        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("Http Status should be: ", HttpStatus.BAD_REQUEST_400, response.getStatus());
        assertTrue(response.hasEntity());
        final String json = response.readEntity(String.class);
        assertEquals("Error body should be: ","{\"message\":\"Amount must be greater than zero\"}", json);
    }

    @Test
    public void givenTransferMoney_whenBalanceIsNotEnough_thenResponseCodeIsBadRequest() {
        final Response response = target("/bank/1.0/transfer").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{\n" +
                        "  \"debit_account_id\": 2,\n" +
                        "  \"credit_account_id\": 1,\n" +
                        "  \"amount\": 100\n" +
                        "}"));

        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("Http Status should be: ", HttpStatus.BAD_REQUEST_400, response.getStatus());
        assertTrue(response.hasEntity());
        final String json = response.readEntity(String.class);
        assertEquals("Error body should be: ","{\"message\":\"Insufficient funds from debit account\"}", json);
    }

    @Test
    public void givenTransferMoney_whenAccountsFromAndToAreSame_thenResponseCodeIsBadRequest() {
        final Response response = target("/bank/1.0/transfer").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{\n" +
                        "  \"debit_account_id\": 1,\n" +
                        "  \"credit_account_id\": 1,\n" +
                        "  \"amount\": 100\n" +
                        "}"));

        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));
        assertEquals("Http Status should be: ", HttpStatus.BAD_REQUEST_400, response.getStatus());
        assertTrue(response.hasEntity());
        final String json = response.readEntity(String.class);
        assertEquals("Error body should be: ","{\"message\":\"Accounts must be different\"}", json);
    }
}
