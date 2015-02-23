package com.ofg.fraud;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class FraudDetectionRequest {

    @NotEmpty
    @NotNull
    private String clientId;
    @NotEmpty
    @NotNull
    private String amount;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
