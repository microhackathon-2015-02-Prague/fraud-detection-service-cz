package com.ofg.fraud;

public class LoanAppDecisionRequest {

    private String clientId;
    private FraudDetectionStatusResult result;

    public LoanAppDecisionRequest(FraudDetectionStatusResult result, Client client, String clientId) {
        this.clientId = clientId;
        this.result = result;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public FraudDetectionStatusResult getResult() {
        return result;
    }

    public void setResult(FraudDetectionStatusResult result) {
        this.result = result;
    }
}
