package com.example.demo.dao;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


@Aspect
@Component
public class BrokerAspect {

    public static class ValidatorResolver {
        public static <T> boolean validate(T t) throws Exception {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                StringChecker stringChecker = field.getAnnotation(StringChecker.class);

                System.out.println(stringChecker.isEmpty());

                System.out.println(field);
            }
            return true;
        }
    }

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut(value = "execution(* com.example.demo.dao..*.*(..))")
    public void BrokerAspect() {

    }

    /**
     * @description  在连接点执行之前执行的通知
     */
    @Before("BrokerAspect()")
    public String doBeforeGame(){

      return "asdasd";
    }

    @Around("BrokerAspect()")
    public Object doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        return null;
    }

}

