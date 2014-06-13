package aspects;

import org.aspectj.lang.JoinPoint;
import services.interfaces.ILoggerService;

import java.util.Arrays;

/**
 * Created by Krzysiu on 2014-06-12.
 */
public class LoggerAspect {

    private ILoggerService loggerService;

    public LoggerAspect(ILoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public void beforeLogger(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        builder.append(joinPoint.getTarget().getClass().getSimpleName()).append(" - ");
        builder.append(joinPoint.getSignature().getName()).append(" - ");
        builder.append(Arrays.toString(joinPoint.getArgs()));
        loggerService.info(builder.toString());
    }

    public void afterReturningLogger(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        builder.append(joinPoint.getTarget().getClass().getSimpleName()).append(" - ");
        builder.append(joinPoint.getSignature().getName());
        loggerService.info(builder.toString());
    }

}
