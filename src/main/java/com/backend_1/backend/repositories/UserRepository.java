package com.backend_1.backend.repositories;

import com.backend_1.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    // We will add our custom search method here!
    Optional<User> findByEmail(String email);
}
