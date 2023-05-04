package com.rachev.ethereumfetcher.model.eth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InfuraTransactionByHashRequestDto extends InfuraBaseRequest<String> {

}
