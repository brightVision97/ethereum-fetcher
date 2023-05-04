package com.rachev.ethereumfetcher.model.user;

import com.rachev.ethereumfetcher.model.transaction.TransactionDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserWithTransactionsDto {

    private Long id;

    private String username;

    private String password;

    private Set<TransactionDto> transactions;
}
