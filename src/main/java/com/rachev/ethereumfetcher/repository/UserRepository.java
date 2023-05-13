package com.rachev.ethereumfetcher.repository;

import com.rachev.ethereumfetcher.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph("user_transactions_graph")
    Optional<User> findByUsername(String username);
}
