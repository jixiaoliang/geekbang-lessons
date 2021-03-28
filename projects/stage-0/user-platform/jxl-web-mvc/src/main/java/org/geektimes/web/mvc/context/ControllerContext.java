package org.geektimes.web.mvc.context;

import org.geektimes.web.mvc.HandlerMethodInfo;
import org.geektimes.web.mvc.controller.Controller;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author jixiaoliang
 * @since 2021/03/08
 **/
public class ControllerContext {

    /**
     * 请求路径和 Controller 的映射关系缓存
     */
    private static Map<String, Controller> controllersMapping = new HashMap<>();

    /**
     * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
     */
    private static Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();

    public static List<Controller> getAllController(){
        return new ArrayList<>(controllersMapping.values());
    }

    public static Controller findMatchController(String path){
        return controllersMapping.get(path);
    }

    public static HandlerMethodInfo findMatchMethod(String path){
        return handleMethodInfoMapping.get(path);
    }

    /**
     * 读取所有的 RestController 的注解元信息 @Path
     * 利用 ServiceLoader 技术（Java SPI）
     */
    public static void initController() {
        for (Controller controller : ServiceLoader.load(Controller.class)) {
            Class<?> controllerClass = controller.getClass();
            Path pathFromClass = controllerClass.getAnnotation(Path.class);
            String requestPath = pathFromClass.value();
            Method[] publicMethods = controllerClass.getMethods();
            // 处理方法支持的 HTTP 方法集合
            for (Method method : publicMethods) {
                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                Path pathFromMethod = method.getAnnotation(Path.class);
                if (pathFromMethod != null) {
                    requestPath += pathFromMethod.value();
                }
                handleMethodInfoMapping.put(requestPath,
                        new HandlerMethodInfo(requestPath, method, supportedHttpMethods));
            }
            controllersMapping.put(requestPath, controller);
        }
    }

    /**
     * 获取处理方法中标注的 HTTP方法集合
     *
     * @param method 处理方法
     * @return
     */
    private static Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationFromMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationFromMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }

        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST,
                    HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }

        return supportedHttpMethods;
    }

}
