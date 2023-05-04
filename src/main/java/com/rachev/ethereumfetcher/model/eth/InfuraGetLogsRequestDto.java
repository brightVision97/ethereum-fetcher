package com.rachev.ethereumfetcher.model.eth;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfuraGetLogsRequestDto extends InfuraBaseRequest<Map<String, Object>> {
}
