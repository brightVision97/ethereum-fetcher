package com.rachev.ethereumfetcher.model.eth;

import lombok.Data;

@Data
public abstract class InfuraBaseDto {

    protected String jsonrpc = "2.0";

    protected Long id = 1L;
}
