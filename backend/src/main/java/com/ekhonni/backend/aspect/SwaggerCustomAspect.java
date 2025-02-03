package com.ekhonni.backend.aspect;

import com.ekhonni.backend.annotation.SwaggerCustomDocumentation;
import com.ekhonni.backend.service.SwaggerCustomProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
@Aspect
@Component
public class SwaggerCustomAspect {
    private final SwaggerCustomProcessor processor;

    public SwaggerCustomAspect(SwaggerCustomProcessor processor) {
        this.processor = processor;
    }

    @Around("@annotation(documentation)")
    public Object processAnnotation(ProceedingJoinPoint joinPoint, SwaggerCustomDocumentation documentation) throws Throwable {
        try {
            processor.setCurrentAnnotation(documentation);
            return joinPoint.proceed();
        } finally {
            processor.clearCurrentAnnotation();
        }
    }
}

