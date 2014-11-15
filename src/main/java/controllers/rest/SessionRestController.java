package controllers.rest;

import annotations.NotAuthorized;
import jsonserializers.LoginSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ISessionManagementService;
import services.interfaces.IUserManagementService;

import java.awt.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Controller(value = "sessionController")
@RequestMapping("/sessions")
public class SessionRestController {

    private ISessionManagementService sessionManagementService;

    @Autowired
    public SessionRestController(ISessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    @NotAuthorized
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody LoginSerializer loginUser(@RequestBody LoginSerializer serializer) throws Exception {
        String token = sessionManagementService.loginUser(serializer.getLogin(), serializer.getPassword());
        serializer.setToken(token);
        return serializer;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody void logoutUser(@RequestHeader(value = "authorization") String token) {
        sessionManagementService.logoutUser(token);
    }
}
