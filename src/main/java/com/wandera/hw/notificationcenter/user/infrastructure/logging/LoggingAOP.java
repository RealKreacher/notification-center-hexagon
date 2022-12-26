package com.wandera.hw.notificationcenter.user.infrastructure.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Aspect
@Component
@Slf4j
public class LoggingAOP {

    private final Map<String, Consumer<String>> logginMap;
    public static final String INFO = "INFO";
    public static final String DEBUG = "DEBUG";
    public static final String TRACE = "TRACE";

    public LoggingAOP() {
        logginMap = new HashMap<>();
        logginMap.put(INFO, log::info);
        logginMap.put(DEBUG, log::debug);
        logginMap.put(TRACE, log::trace);
    }

    @Around(value = "@annotation(logAccess)")
    public Object logAccess(ProceedingJoinPoint joinPoint, LogAccess logAccess) throws Throwable {
        var logger = logginMap.getOrDefault(logAccess.level(), log::debug);

        var signature = joinPoint.getSignature();
        var controllerName = signature.getDeclaringType().getSimpleName();
        var methodName = signature.getName();
        var params = joinPoint.getArgs();

        var start = System.currentTimeMillis();
        var result = joinPoint.proceed();
        var executionTime = System.currentTimeMillis() - start;

        var logMessage = String.format("Controller: %s. Method invoked: %s. Request params: %s. Execution time: %s", controllerName, methodName, Arrays.toString(params), executionTime);

        logger.accept(logMessage);

        return result;
    }
}
