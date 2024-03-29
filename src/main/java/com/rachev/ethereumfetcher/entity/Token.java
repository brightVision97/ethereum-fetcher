package com.rachev.ethereumfetcher.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token", indexes = @Index(columnList = "token"))
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseEntity {

    @Id
    @SequenceGenerator(name = "tokenSeqGen", sequenceName = "tokenSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenSeqGen")
    private Long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public enum TokenType {
        BEARER,

        ;

        @Override
        public String toString() {
            return StringUtils.capitalize(this.name().toLowerCase());
        }
    }
}