package com.bruno.hruser.resources;

import com.bruno.hruser.entities.User;
import com.bruno.hruser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserRepository repository;

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User found = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Object not found"));
        return ResponseEntity.ok().body(found);
    }
    @GetMapping("/search")
    public ResponseEntity<User> findById(@RequestParam String email){
        User found = repository.findByEmail(email);
        return ResponseEntity.ok().body(found);
    }

}
