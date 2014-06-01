package controllers.web.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.interfaces.ILocationManagementService;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/*")
public class MainController {

    @Autowired
    private ILocationManagementService locationManagementService;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(ModelMap modelMap) {
        locationManagementService.createNewLocation();
        return "main";
    }
}
