package com.rachev.ethereumfetcher.aspect;

import com.rachev.ethereumfetcher.model.auth.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public final class LoggingAspect {

    @Before("execution(* com.rachev.ethereumfetcher.controller.*.*(..))")
    private String createJoinPointForLogs(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(args)) {
            return joinPoint.getSignature().getName().concat(" method has no parameters");
        }
        return Arrays.stream(args)
                .map(String::valueOf)
                .collect(Collectors.joining(
                        "\r\n",
                        "\r\n========== The request values ==========\n",
                        "\r\n=======================================")
                );
    }

    @AfterReturning(
            pointcut = "execution(* com.rachev.ethereumfetcher.service.AuthenticationServiceImpl.*(..))",
            returning = "authResponse"
    )
    public void logsResponse(AuthResponse authResponse) {
        log.info(authResponse.toString());
    }

    @AfterThrowing(pointcut = "execution(* com.rachev.ethereumfetcher.controller.*.*.*(..))", throwing = "exception")
    public void logsErrors(JoinPoint joinPoint, Throwable exception) {
        log.info("========================== We have Error here ==========================");
        log.info(joinPoint.getSignature().getName());
        log.info(exception.getMessage());
        log.info("==========================================================================");
    }
}
