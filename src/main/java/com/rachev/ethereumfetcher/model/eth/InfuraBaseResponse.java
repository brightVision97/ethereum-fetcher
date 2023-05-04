package com.rachev.ethereumfetcher.model.eth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class InfuraBaseResponse<T> extends InfuraBaseDto {

    protected T result;
}
