package aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-06-12.
 */
public class AuthorizationAspect {

    private IUserManagementService userManagementService;

    public AuthorizationAspect(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    public Object authorize(ProceedingJoinPoint joinPoint) throws Exception {
        String token = (String)joinPoint.getArgs()[0];
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
