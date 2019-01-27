package me.freelife;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerfAspect {

    // me.freelife로 시작하는 package 밑에 있는 모든 클래스 중에서 EventService 안에 들어있는 모든 메서드에
    // 정의한 행위를 적용하라라는 의미 Pointcut을 여러 어드바이저에서 재사용할 것이 아니라면 이렇게 사용해도 됨
    @Around("execution(* me..*.EventService.*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }
}
