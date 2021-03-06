package com.bruno.payroll.resources;

import com.bruno.payroll.entities.Payment;
import com.bruno.payroll.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;

    @HystrixCommand(fallbackMethod = "getPaymentAlternative")
    @GetMapping("{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days){
        Payment payment = paymentService.getPayment(workerId, days);
        return ResponseEntity.ok().body(payment);
    }

    public ResponseEntity<Payment> getPaymentAlternative(Long workerId, Integer days){
        Payment payment = new Payment("Bran", 400.00, days);
        return ResponseEntity.ok(payment);
    }



}
