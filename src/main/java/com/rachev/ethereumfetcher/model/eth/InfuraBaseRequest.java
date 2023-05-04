package com.rachev.ethereumfetcher.model.eth;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class InfuraBaseRequest<T> extends InfuraBaseDto {

    protected String method;

    protected List<T> params;
}
