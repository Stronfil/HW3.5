package com.komissarov.springstore.logging;

import com.komissarov.springstore.entity.Product;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(public * com.komissarov.springstore.service.CartService.*(..))")
    public void cartLogging(JoinPoint joinPoint) {
        System.out.println("LOGGING: calling " + joinPoint.getSignature().getName());
        if (joinPoint.getArgs().length > 1) {
            Product prod = (Product) joinPoint.getArgs()[1];
            System.out.println("On product: \"" + prod.getTitle() + "\"");
        }
    }
}
