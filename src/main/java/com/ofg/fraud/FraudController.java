package com.ofg.fraud;

import com.google.common.util.concurrent.ListenableFuture;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import groovy.transform.TypeChecked;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Slf4j
@RestController
@RequestMapping("/api/fraud-detection")
@TypeChecked
@Api(value = "detectFraud", description = "starts fraud-detection process")
public class FraudController {

    public static final String IN_CONTENT_TYPE = "application/vnd.loan-application-decission-maker.v1+json";
    private static final String CLIENT_SERVICE = "client-service";
    private static final String LOAN_APP_DECISION_MAKER = "loan-application-decission-maker";
    private static final String CLIENT_URL = "/clients/";;
    private static final String LOAN_APP_URL = "/api/loanApplication/";

    @Autowired
    private FraudDetectionServiceImpl fraudDetectionService;
    @Autowired
    private ServiceRestClient serviceRestClient;

    @RequestMapping(
            value = "{applicationId}",
            method = PUT,
            consumes = IN_CONTENT_TYPE)
    @ApiOperation(value = "Accepts fraud detection request",
            notes = "This will asynchronously call loan-application-decission-maker with our findings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void detectFraud(@PathVariable @NotNull String applicationId, @RequestBody @NotNull @Valid FraudDetectionRequest request) {
        // call get client details
        final ListenableFuture<Client> future = serviceRestClient.forService(CLIENT_SERVICE)
                .get()
                .onUrl(CLIENT_URL + request.getClientId())
                .anObject()
                .ofTypeAsync(Client.class);
        future.addListener(() -> {
            try {
                Client client = future.get();
                // do calculations
                FraudDetectionStatusResult result = fraudDetectionService.check(request, client);
                // call loan-application-decision-maker
                serviceRestClient.forService(LOAN_APP_DECISION_MAKER)
                        .put()
                        .onUrl(LOAN_APP_URL+applicationId)
                        .body(result)
                        .ignoringResponse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

}
