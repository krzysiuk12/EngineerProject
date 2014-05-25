package controllers.web.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Krzysiu on 2014-05-25.
 */
@Controller
@RequestMapping("/*")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage(ModelMap modelMap) {
        return "main";
    }
}
