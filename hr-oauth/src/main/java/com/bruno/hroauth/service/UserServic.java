package com.bruno.hroauth.service;

import com.bruno.hroauth.entities.User;
import com.bruno.hroauth.feignClients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServic {

    @Autowired
    private UserFeignClient userFeignClient;


    public User findByEmail(String email){
        User byEmail = userFeignClient.findByEmail(email).getBody();
        if(byEmail == null) throw new IllegalArgumentException("Email not found");
        return byEmail;

    }
}
