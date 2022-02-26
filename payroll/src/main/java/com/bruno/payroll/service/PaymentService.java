package com.bruno.payroll.service;


import com.bruno.payroll.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Payment getPayment(Long id, Integer days){
        return new Payment("Bob", 200.00,days);

    }

}
