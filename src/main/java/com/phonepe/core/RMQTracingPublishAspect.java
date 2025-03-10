package com.phonepe.core;

import io.appform.opentracing.TracingAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Only methods annotated with {@link TracingAnnotation} are traced
 */
@Aspect
public class RMQTracingPublishAspect {
    private static final Logger log = LoggerFactory.getLogger(RMQTracingPublishAspect.class.getSimpleName());


    @Pointcut("execution(public final void io.appform.dropwizard.actors.actor.UnmanagedBaseActor.publish(..))")
    public void finalMethodExecution() {}

    @Around("finalMethodExecution()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Aspect: Before finalMethod, input: " + "input");

        Object result = joinPoint.proceed(); // Execute the original method

        log.info("Aspect: After finalMethod, result: " + result);
        return result;
    }

}
