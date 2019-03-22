package com.yuboz.springbootapi.aspect;


import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.yuboz.springbootapi.api.LogTestApi.log(..))")
    public void logg(){

    }

    @Before("logg()")
    public void doBefore(){

        logger.info("--------------doBefore1------------------");

    }

    @After("logg()")
    public void doAfter(){
        logger.info("--------------doAfter2------------------");
    }

    @AfterReturning(returning = "result", pointcut = "logg()")
    public void doAfterRturning (Object result){
        logger.info("--------------doAfterReturning------------------ : content");
    }
}
