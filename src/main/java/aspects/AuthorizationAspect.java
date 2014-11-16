package aspects;

import exceptions.ErrorMessages;
import jsonserializers.common.ResponseSerializer;
import jsonserializers.common.ResponseStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Aspect
@Order(1)
@Component
public class AuthorizationAspect {

    private IUserManagementService userManagementService;

    @Autowired
    public AuthorizationAspect(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @Around("aspects.pointcuts.PointcutDefinitions.userAuthorization()")
    public Object authorizeUser(ProceedingJoinPoint joinPoint) throws Exception {
        if(!userManagementService.authenticateUserAccountByToken((String)joinPoint.getArgs()[0])) {
            return new ResponseSerializer<>(ResponseStatus.REQUEST_DENIED, ErrorMessages.INVALID_TOKEN);
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Around("aspects.pointcuts.PointcutDefinitions.adminAuthorization()")
    public Object authorizeAdmin(ProceedingJoinPoint joinPoint) throws Exception {
        if(!userManagementService.authenticateAdminAccountByToken((String) joinPoint.getArgs()[0])) {
            if(userManagementService.authenticateUserAccountByToken((String)joinPoint.getArgs()[0])) {
                return new ResponseSerializer<>(ResponseStatus.ACCESS_DENIED, null, null);
            }
            return new ResponseSerializer<>(ResponseStatus.REQUEST_DENIED, ErrorMessages.INVALID_TOKEN);
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
