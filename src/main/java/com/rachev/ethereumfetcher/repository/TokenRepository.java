package com.rachev.ethereumfetcher.repository;

import com.rachev.ethereumfetcher.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            select t from Token t\s
            inner join User u on t.user.username = u.username\s
            where u.username = :username and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(String username);

    Optional<Token> findByToken(final String token);
}
