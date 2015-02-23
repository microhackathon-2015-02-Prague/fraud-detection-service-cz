package com.ofg.fraud;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class FraudDetectionServiceImpl implements FraudDetectionService {


    @Override
    public FraudDetectionStatusResult check(FraudDetectionRequest request, Client client) {
        Preconditions.checkNotNull(request, "Request must not be null");
        return FraudDetectionStatusResult.FISHY;
    }
}
