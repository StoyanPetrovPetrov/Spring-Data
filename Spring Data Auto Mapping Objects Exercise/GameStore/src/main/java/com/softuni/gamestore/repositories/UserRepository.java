package com.softuni.gamestore.repositories;

import com.softuni.gamestore.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findOneByEmailAndPassword(String email, String password);
}
