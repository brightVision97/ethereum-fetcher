package com.rachev.ethereumfetcher.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "user_transactions_graph",
        attributeNodes = {
                @NamedAttributeNode("transactions"),
                @NamedAttributeNode("tokens")
        }
)
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 5, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_transaction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public void linkTransaction(Transaction transaction) {
        if (transactions.add(transaction)) {
            transaction.getUsers().add(this);
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
