package com.rachev.ethereumfetcher.model.eth;

import com.rachev.ethereumfetcher.model.transaction.LogDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfuraGetLogsResponseDto extends InfuraBaseResponse<List<LogDto>> {
}
