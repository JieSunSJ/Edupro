package cn.hytc.tlias.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class MyAspect {
    //针对list方法、delete方法进行前置通知和后置通知

    //前置通知
    @Before("@annotation(cn.hytc.tlias.annotation.LogOperation)")
    public void before(){
        log.info("MyAspect -> before ...");
    }

    //后置通知
    @After("@annotation(cn.hytc.tlias.annotation.LogOperation)")
    public void after(){
        log.info("MyAspect -> after ...");
    }
}