package org.geektimes.projects.user.web.controller;

import org.apache.commons.lang.StringUtils;
import org.eclipse.microprofile.config.Config;
import org.geektimes.configuration.microprofile.config.constant.Constants;
import org.geektimes.projects.user.domain.Auth2Bean;
import org.geektimes.projects.user.domain.GeeInfo;
import org.geektimes.projects.user.domain.ModelAndView;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.serializer.jackson.JacksonSerializer;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 用户注册 Controller
 */
@Path("/user")
public class UserController implements PageController {

    private Logger logger = Logger.getLogger("UserController");

    @Resource(name = "bean/UserService")
    private UserService userService;

    private Map<String, ModelAndView<User>> rstMapping = new HashMap<String, ModelAndView<User>>() {
        {
            put("register", new ModelAndView<>(user -> userService.register(user), "index.jsp"));
            put("authToken", new ModelAndView<>(user -> {
            }, "index.jsp"));
            put("registerPage", new ModelAndView<>(user -> {
            }, "register-form.jsp"));
            put("loginPage", new ModelAndView<>(user -> {
            }, "login-form.jsp"));
            put("createTable", new ModelAndView<>(user -> userService.createTable(), "index.jsp"));
        }
    };


    @GET
    @Path("/account") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return process(request);
    }

    private String process(HttpServletRequest request) {
        String methodName = request.getParameter("methodName");
        String userName = null ;
        if ("authToken".equals(methodName)) {
            userName =  processAccessToken(request);
        }

        ModelAndView<User> modelAndView = rstMapping.get(methodName);
        if (Objects.isNull(modelAndView)) {
            throw new RuntimeException("未知方法");
        }
        User user = parseUser(request);
        if(StringUtils.isNotEmpty(userName)){
            user.setName(userName);
        }
        request.getSession().setAttribute("name", user.getName());
        Config config = (Config) request.getServletContext().getAttribute(Constants.GLOBAL_CONFIG);
        request.getSession().setAttribute("applicationName", config.getValue(Constants.APPLICATION_NAME, String.class));

        modelAndView.getAction().accept(user);
        return modelAndView.getView();
    }

    private String processAccessToken(HttpServletRequest request) {


        String code = request.getParameter("code");
        String redirectUri = (String) request.getServletContext().getAttribute("redirectUri");
        String clientId = (String)request.getServletContext().getAttribute("clientId");
        String clientSecret = (String)request.getServletContext().getAttribute("clientSecret");
        Client client = ClientBuilder.newClient();
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code&client_id=" + clientId+"&code="+code;

        logger.info("待请求url"+ url);

        Form form = new Form();
        form.param("code",code);
        form.param("redirect_uri",redirectUri);
        form.param("client_id",clientId);
        form.param("client_secret",clientSecret);
        Entity<Form> entity = Entity.form(form);
        Response response = client
                .target(url)      // WebTarget
                .request() // Invocation.Builder
                .post(entity);                                     //  Response

        String content = response.readEntity(String.class);

        Auth2Bean auth2Bean = JacksonSerializer.of(content, Auth2Bean.class);


        Response resp = ClientBuilder.newClient()
                .target("https://gitee.com/api/v5/user?access_token=" + auth2Bean.getAccess_token())      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        content = resp.readEntity(String.class);

        GeeInfo geeInfo = JacksonSerializer.of(content, GeeInfo.class);


        return geeInfo.getName();
    }




    private User parseUser(HttpServletRequest request) {
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
