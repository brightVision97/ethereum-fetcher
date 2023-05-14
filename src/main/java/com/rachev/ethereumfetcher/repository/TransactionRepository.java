package com.rachev.ethereumfetcher.repository;

import com.rachev.ethereumfetcher.entity.Transaction;
import com.rachev.ethereumfetcher.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionHash(String hash);

    List<Transaction> findAllByUsersIn(List<User> users, Sort sort);
}
