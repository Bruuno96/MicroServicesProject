package com.bruno.hroauth.resources;

import com.bruno.hroauth.entities.User;
import com.bruno.hroauth.service.UserServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserServic userService;

    @GetMapping("search")
    public ResponseEntity<User> findByEmail(@RequestParam String email){
        User byEmail = userService.findByEmail(email);
        return ResponseEntity.ok().body(byEmail);

    }


}
