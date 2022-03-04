package com.bruno.hroauth.service;

import com.bruno.hroauth.entities.User;
import com.bruno.hroauth.feignClients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServic implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email){
        User byEmail = userFeignClient.findByEmail(email).getBody();
        if(byEmail == null) throw new IllegalArgumentException("Email not found");
        return byEmail;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byEmail = userFeignClient.findByEmail(username).getBody();
        if(byEmail == null) throw new UsernameNotFoundException("Email not found");
        return byEmail;
    }
}
