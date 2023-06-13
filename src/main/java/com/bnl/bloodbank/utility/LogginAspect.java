package com.bnl.bloodbank.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogginAspect {

    public static final Log LOGGER = LogFactory.getLog(LogginAspect.class);

    @AfterThrowing(pointcut = "execution(* com.bnl.bloodbank.service.*Impl.*(..))", throwing = "exception")
    public void logFromService(Exception exception){
        LOGGER.error(exception);
    }
    
}
