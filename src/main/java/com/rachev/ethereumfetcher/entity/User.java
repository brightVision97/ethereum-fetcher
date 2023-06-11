package com.rachev.ethereumfetcher.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "_user")
@Data
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements UserDetails {

    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 5, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    private Long id;

    @Indexed
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_transaction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Token> tokens;

    public Transaction linkTransaction(Transaction transaction) {
        if (transactions.add(transaction)) {
            transaction.getUsers().add(this);
        }
        return transaction;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.checkedCollection(Collections.emptyList(), GrantedAuthority.class);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
