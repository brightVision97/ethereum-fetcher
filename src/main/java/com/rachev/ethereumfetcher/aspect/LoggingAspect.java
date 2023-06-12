package com.rachev.ethereumfetcher.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public final class LoggingAspect {

    private static final String API_POINT_CUT = "execution(* com.rachev.ethereumfetcher.controller.*.*(..))";

    private static final String EXCEPTION_POINT_CUT = "execution(* com.rachev.ethereumfetcher.controller.*.*.*(..))";

    private enum DataType {
        REQUEST,
        RESPONSE,
    }

    @Pointcut(API_POINT_CUT)
    public void logController() {
    }

    @Around("logController()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().getName());
        log.info(createJoinPointForLogs(joinPoint, DataType.REQUEST));
        return joinPoint.proceed(joinPoint.getArgs());
    }

    private String createJoinPointForLogs(JoinPoint joinPoint, DataType dataType) {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return joinPoint.getSignature().getName().concat(" method has no parameters");
        }
        StringBuilder requestValue = new StringBuilder();
        if (dataType.equals(DataType.REQUEST)) {
            requestValue.append("\r\n========== The request values ==========");
        } else {
            requestValue.append("\r\n========== The response values ==========");
        }
        return Arrays.stream(args)
                .filter(Objects::nonNull)
                .map(arg -> requestValue.append("\r\n")
                        .append(arg)
                        .append("\r\n=======================================")
                        .toString())
                .reduce("\r\n", (a, b) -> requestValue.append(a).append(b).toString());
    }

    @AfterReturning("logController()")
    public void logsResponse(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName());
        log.info(createJoinPointForLogs(joinPoint, DataType.RESPONSE));
    }

    @AfterThrowing(value = EXCEPTION_POINT_CUT, throwing = "exception")
    public void logsErrors(JoinPoint joinPoint, Throwable exception) {
        log.info("========================== We have Error here ==========================");
        log.info(joinPoint.getSignature().getName());
        log.info(exception.getMessage());
        log.info("==========================================================================");
    }
}
