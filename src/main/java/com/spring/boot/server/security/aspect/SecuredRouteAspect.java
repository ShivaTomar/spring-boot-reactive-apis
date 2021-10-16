package com.spring.boot.server.security.aspect;

import com.spring.boot.server.security.annotations.SecuredRoute;
import com.spring.boot.server.security.rules.SecurityRule;
import com.spring.boot.server.security.util.SecurityUtil;
import com.spring.boot.server.util.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@AllArgsConstructor
public class SecuredRouteAspect {
    private final SecurityUtil securityUtil;

    @Around("methodsAnnotatedWithSecuredRouteAnnotation()")
    public Object processMethodsAnnotatedWithSecuredAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(SecuredRoute.class)) {
            SecuredRoute securedRouteAnnotation = method.getAnnotation(SecuredRoute.class);
            String securityRule = securedRouteAnnotation.value();

            if (securityRule.equals(SecurityRule.IS_ANONYMOUS) || securityUtil.isAuthenticated()) {
                return joinPoint.proceed();
            }
        }

        throw new UnauthorizedException();
    }

    @Pointcut("@annotation(com.spring.boot.server.security.annotations.SecuredRoute)")
    private void methodsAnnotatedWithSecuredRouteAnnotation() {
    }

}
