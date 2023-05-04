package com.rachev.ethereumfetcher.model.eth;

import com.rachev.ethereumfetcher.model.transaction.TransactionReceiptDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfuraTransactionReceiptResponseDto extends InfuraBaseResponse<TransactionReceiptDto> {
}
