package controllers.rest;

import annotations.NotAuthorized;
import jsonserializers.session.LoginSerializer;
import jsonserializers.common.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.interfaces.ISessionManagementService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
@Controller
@RequestMapping("/sessions")
public class SessionRestController {

    private ISessionManagementService sessionManagementService;

    @Autowired
    public SessionRestController(ISessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    @NotAuthorized
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseSerializer<LoginSerializer> loginUser(@RequestBody LoginSerializer serializer, HttpServletRequest request) throws Exception {
        String token = sessionManagementService.loginUser(serializer.getLogin(), serializer.getPassword(),request.getRemoteAddr(), request.getSession().getId());
        serializer.setToken(token);
        return new ResponseSerializer(serializer);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody
    ResponseSerializer<LoginSerializer> logoutUser(@RequestHeader(value = "authorization") String token, HttpServletRequest request) throws Exception{
        sessionManagementService.logoutUser(token, request.getRemoteAddr(), request.getSession().getId());
        return new ResponseSerializer();
    }
}
