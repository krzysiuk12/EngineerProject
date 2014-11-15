package aspects.pointcuts;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Krzysztof Kicinger on 2014-11-15.
 */
@Aspect
public class PointcutDefinitions {

    @Pointcut("within(controllers.rest.*))")
    public void allControllerMethods() {}

    @Pointcut("@annotation(annotations.AdminAuthorized) && within(controllers.rest.*)")
    public void adminAuthorization() {}

    @Pointcut("!@annotation(annotations.AdminAuthorized) && !@annotation(annotations.NotAuthorized) && within(controllers.rest.*)")
    public void userAuthorization() {}

}
