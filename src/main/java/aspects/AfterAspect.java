package aspects;

import exceptions.ErrorMessages;
import exceptions.FormValidationError;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.common.ResponseStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
@Aspect
@Order(3)
@Component
public class AfterAspect {

    private static final List<ResponseStatus> resultErrorStatuses = new ArrayList<>(Arrays.asList(ResponseStatus.ACCESS_DENIED, ResponseStatus.REQUEST_DENIED, ResponseStatus.UNKNOWN_ERROR, ResponseStatus.VALIDATION_ERROR));

    @Around(value = "aspects.pointcuts.PointcutDefinitions.allControllerMethods()")
    public Object afterThrowing(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (FormValidationError ex) {
            return new ResponseSerializer<>(ResponseStatus.VALIDATION_ERROR, ex.getErrorMessages());
        } catch (Throwable ex) {
            return new ResponseSerializer<>(ResponseStatus.UNKNOWN_ERROR, ErrorMessages.SERVER_SIDE_ERROR);
        }
    }

    @AfterReturning(pointcut = "aspects.pointcuts.PointcutDefinitions.allControllerMethods()", returning = "returnedValue")
    public void afterReturning(ResponseSerializer returnedValue) {
        if(isCorrectResult(returnedValue)) {
            if (returnedValue.getResult() instanceof Collection) {
                returnedValue.setStatus(((Collection) returnedValue.getResult()).isEmpty() ? ResponseStatus.ZERO_RESULTS : ResponseStatus.OK);
            } else {
                returnedValue.setStatus(ResponseStatus.OK);
            }
        }
    }

    private boolean isCorrectResult(ResponseSerializer rs) {
        return !resultErrorStatuses.contains(rs.getStatus());
}

}