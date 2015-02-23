package com.ofg.fraud;

public interface FraudDetectionService {
    
    FraudDetectionStatusResult check(FraudDetectionRequest request, Client client);
    
}
