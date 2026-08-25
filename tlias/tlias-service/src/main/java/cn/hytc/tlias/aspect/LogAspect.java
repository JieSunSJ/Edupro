package cn.hytc.tlias.aspect;

import cn.hytc.tlias.annotation.Log;
import cn.hytc.tlias.event.LogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志切面 - 拦截@Log注解的方法，记录操作日志
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Around("@annotation(cn.hytc.tlias.annotation.Log)")
    public Object around(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);

        StringBuilder detail = new StringBuilder();
        detail.append("方法：").append(method.getName());
        detail.append("，参数：");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                detail.append(args[i].toString());
                if (i < args.length - 1) {
                    detail.append("; ");
                }
            }
        }

        try {
            Object result = joinPoint.proceed();
            long costTime = System.currentTimeMillis() - start;
            detail.append("，结果：成功");
            publisher.publishEvent(new LogEvent(this, log.module(), log.operation(), detail.toString(), costTime));
            return result;
        } catch (Throwable e) {
            long costTime = System.currentTimeMillis() - start;
            detail.append("，结果：失败，异常：").append(e.getMessage());
            publisher.publishEvent(new LogEvent(this, log.module(), log.operation(), detail.toString(), costTime));
            throw new RuntimeException(e);
        }
    }
}