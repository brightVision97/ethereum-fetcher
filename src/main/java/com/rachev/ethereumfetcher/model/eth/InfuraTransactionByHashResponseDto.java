package com.rachev.ethereumfetcher.model.eth;


import com.rachev.ethereumfetcher.model.transaction.TransactionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InfuraTransactionByHashResponseDto extends InfuraBaseResponse<TransactionDto> {
}
