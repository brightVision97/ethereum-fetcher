package com.rachev.ethereumfetcher.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@StandardException
public final class RlpDecodingException extends RuntimeException {
}
