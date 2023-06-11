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
public class LoggingAspect {

    /**
     * the following code will represent api Point cut value to set and reuse it inside any point cut method
     * log controller is a point cut method
     **/
    private final String apiPointCut = "execution(* com.rachev.ethereumfetcher.scheduler.*.*(..))";

    @Pointcut(apiPointCut)
    public void logController() {
    }

    /**
     * @param joinPoint we can find inside it all the details of the method called inside the join point
     *                  <p>
     *                  the AOP will execute this method before execute controller method
     */
    @Around("logController()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // for log the controller name
        log.info(joinPoint.getSignature().getName());
        // for log the request attributes form client side
        log.info(createJoinPointForLogs(joinPoint, DataType.REQUEST));
        return joinPoint.proceed(joinPoint.getArgs());
    }


    /**
     * @param joinPoint we need to use it to see attributes in the original method
     * @return will return String after building all the attributes
     */
    private String createJoinPointForLogs(JoinPoint joinPoint, DataType dataType) {
        Object[] args = joinPoint.getArgs();
        /**
         * the joinPoint has arguments from the controller,
         * but we can see the args will receive here as an Array we need to check the length of it before making any Operations.
         */
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
                        .append("\r\n============= ======= ====== =============")
                        .toString())
                .reduce("\r\n", (a, b) -> requestValue.append(a).append(b).toString());
    }

    private enum DataType {
        REQUEST,
        RESPONSE,
    }

    @AfterReturning("logController()")
    public void logsResponse(JoinPoint joinPoint) {
        // for log the controller name
        log.info(joinPoint.getSignature().getName());
        log.info(createJoinPointForLogs(joinPoint, DataType.RESPONSE));
    }

    /**
     * the following point cut will scan all the project and catch any errors inside the project files
     */
    private final String exceptionPointcut = "execution(* com.rachev.ethereumfetcher.controller.*.*.*(..))";

    /**
     * This method will print
     *
     * @param joinPoint we can find inside it all the details of the method called inside the join point
     * @param exception from here we can know more details about the exception like exception type and exception message
     */
    @AfterThrowing(value = exceptionPointcut, throwing = "exception")
    public void logsErrors(JoinPoint joinPoint, Throwable exception) {
        log.info("========================== We have Error here ==========================");
        // for log the controller name
        log.info(joinPoint.getSignature().getName());
        // for know what the exception message
        log.info(exception.getMessage());
        log.info("==========================================================================");
    }
}
