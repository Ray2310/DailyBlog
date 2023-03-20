package com.blog.aspect;

import com.alibaba.fastjson.JSON;
import com.blog.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 切面类
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    //确定切点
    @Pointcut("@annotation(com.blog.annotation.SystemLog)")
    public void pt(){

    }


    //通知方法（使用环绕通知）
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.getArgs();
        Object res; //得到目标方法调用的返回值
        try {
            handleBefore(joinPoint);
            //目标方法的调用
            res = joinPoint.proceed();
            //打印响应信息
            handleAfter(res);
        }//无论有没有异常都需要打印异常信息
        finally {
            //换行
            log.info("============End============" + System.lineSeparator());
        }


        return res;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("============Start============");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()) );
    }




    private void handleAfter(Object res) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(res));

    }

    //todo 获取被增强方法上的注解对象
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }



}
