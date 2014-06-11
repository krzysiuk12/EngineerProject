package controllers.web.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/*")
public class MainController {

    @Autowired
    private ILocationManagementService locationManagementService;

    @Autowired
    private IUserManagementService userManagementService;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(ModelMap modelMap) {
/*        UserAccount admin = new UserAccount();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("tourtrip@email.com");
        admin.setStatus(UserAccount.Status.ACTIVE);
        admin.setInvalidSignInAttemptsCounter(0);
        admin.setLockoutCounter(0);
        admin.setToken("admin");
        admin.setCreationDate(Calendar.getInstance().getTime());
        Individual individual = new Individual();
        individual.setFirstName("Admin");
        individual.setLastName("Admin");
        admin.setIndividual(individual);
        userManagementService.saveUserAccount(admin);*/
        return "main";
    }
}
