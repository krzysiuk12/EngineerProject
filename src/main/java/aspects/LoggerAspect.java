package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import services.interfaces.ILoggerService;

import java.util.Arrays;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Aspect
@Order(2)
@Component
public class LoggerAspect {

    private ILoggerService loggerService;

    @Autowired
    public LoggerAspect(ILoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Before("aspects.pointcuts.PointcutDefinitions.allControllerMethods()")
    public void beforeLogger(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        builder.append("Before Execution: ");
        builder.append(joinPoint.getTarget().getClass().getSimpleName()).append(" - ");
        builder.append(joinPoint.getSignature().getName()).append(" - ");
        builder.append(Arrays.toString(joinPoint.getArgs()));
        loggerService.info(builder.toString());
    }

    @After("aspects.pointcuts.PointcutDefinitions.allControllerMethods()")
    public void afterReturningLogger(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        builder.append("After execution: ");
        builder.append(joinPoint.getTarget().getClass().getSimpleName()).append(" - ");
        builder.append(joinPoint.getSignature().getName());
        loggerService.info(builder.toString());
    }

}
