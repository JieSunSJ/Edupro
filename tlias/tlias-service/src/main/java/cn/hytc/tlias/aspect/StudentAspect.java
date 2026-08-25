package cn.hytc.tlias.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAspect {
    @Around("execution(* cn.hytc.tlias.service.impl.StudentServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("开始");
        Object result = joinPoint.proceed();
        System.out.println("结束");
        return result;
    }
}
