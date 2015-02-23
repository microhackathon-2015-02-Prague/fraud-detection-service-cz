package com.ofg.fraud;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Component;

@Component
public class FraudDetectionServiceImpl implements FraudDetectionService {


    @Override
    public FraudDetectionStatusResult check(FraudDetectionRequest request, Client client) {
        Preconditions.checkNotNull(request, "Request must not be null");

        Integer age = Ints.tryParse(client.getAge());
        if (age == null) {
            return FraudDetectionStatusResult.NOK;
        }
        Integer amount = Ints.tryParse(request.getAmount());
        if (amount == null) {
            return FraudDetectionStatusResult.NOK;
        }

        // FRAUD
        if (client.getJob().equalsIgnoreCase("OTHER") && age < 18 && amount > 2000 && (client.getFirstname().length() < 2) || client.getSurname().length() < 2) {
            return FraudDetectionStatusResult.NOK;
        }
        // FISHY
        if (client.getJob().equalsIgnoreCase("FINANCE SECTOR") && age > 65 && amount > 1000 && amount < 2000 && (client.getFirstname().length() > 25) || client.getSurname().length() > 25) {
            return FraudDetectionStatusResult.FISHY;
        }

        // FISHY
        boolean fn = client.getFirstname().length() < 25 && client.getFirstname().length() > 2;
        boolean sn = client.getSurname().length() < 25 && client.getSurname().length() > 2;
        if (client.getJob().equalsIgnoreCase("IT") && age <= 65 && age >= 18 && amount < 1000 && fn && sn) {
            return FraudDetectionStatusResult.OK;
        }
        throw new IllegalStateException("Impossible state");
    }
}
