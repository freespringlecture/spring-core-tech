package me.freelife;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerfAspect {

    //annotation 기반으로 PerfLogging 애노테이션이 있는곳에 부가기능을 적용
    @Around("@annotation(me.freelife.PerfLogging)")
    // @Around("bean(simpleEventService)") // bean으로 적용도 가능함
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }

    /**
     * 모든 메서드 실행 이전에 메세지가 찍힘
     */
    @Before("bean(simpleEventService)")
    public void hello() {
        System.out.println("hello");
    }
}
