package org.example.usermicroservice.repository;

import jakarta.validation.constraints.Email;
import org.example.usermicroservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Email String email);
    boolean existsByEmail(@Email String email);
}
