package com.bruno.hruser.repositories;

import com.bruno.hruser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
}
