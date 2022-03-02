package com.bruno.payroll.service;


import com.bruno.payroll.entities.Payment;
import com.bruno.payroll.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hr-worker.host}")
    private String workerHost;

    public Payment getPayment(Long workerId, Integer days){
        Map<String, String> uriVariables = new HashMap<>();

        // 1 - id do path 2 - id Long do objeto
        uriVariables.put("id", String.valueOf(workerId));

        //
        Worker worker = restTemplate.getForObject(workerHost +"/workers/{id}", Worker.class, uriVariables);
        return new Payment(worker.getName(),worker.getDailyIncome(),days);

    }

}
