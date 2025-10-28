package org.hynaf.Core.Node;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ClockTickAspect {
    @Around("@annotation(ClockTick) && target(targetObject)")
    public Object executeBeforeMethod(ProceedingJoinPoint joinPoint, Object targetObject) throws Throwable {
        if(targetObject instanceof Node node) {
            node.getClock().tick();
        }else{
            throw new IllegalArgumentException("targetObject is not a Node");
        }
        return joinPoint.proceed();
    }
}
