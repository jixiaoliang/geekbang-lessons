package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.ModelAndView;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户注册 Controller
 */
@Path("/user")
public class UserController implements PageController {

    @Resource(name ="bean/UserService")
    private  UserService userService;

    private  Map<String, ModelAndView<User>> rstMapping = new HashMap<String, ModelAndView<User>>(){
        {
            put("register", new ModelAndView<>(user -> userService.register(user), "index.jsp"));
            put("registerPage", new ModelAndView<>(user->{}, "register-form.jsp"));
            put("loginPage", new ModelAndView<>(user->{}, "login-form.jsp"));
            put("createTable", new ModelAndView<>(user->userService.createTable(),"index.jsp"));
        }
    };


    @GET
    @Path("/account") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return process(request);
    }

    private String process(HttpServletRequest request) {
        String methodName = request.getParameter("methodName");
        ModelAndView<User> modelAndView = rstMapping.get(methodName);
        if (Objects.isNull(modelAndView)) {
            throw new RuntimeException("未知方法");
        }
        User user = parseUser(request);
        request.getSession().setAttribute("name", user.getName());
        modelAndView.getAction().accept(user);
        return modelAndView.getView();
    }


    private User parseUser(HttpServletRequest request){
        User user = new User();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
