package org.geektimes.context;

import com.google.common.collect.Lists;
import org.geektimes.function.ThrowableAction;
import org.geektimes.function.ThrowableFunction;
import org.geektimes.web.mvc.FrontControllerServlet;
import org.geektimes.web.mvc.HandlerMethodInfo;
import org.geektimes.web.mvc.context.ControllerContext;
import org.geektimes.web.mvc.controller.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @author jixiaoliang
 * @since 2021/03/07
 **/
public class ComponentContext {

    private final Logger logger = Logger.getLogger(ComponentContext.class.getName());

    private static final String COMPONENT_CONTEXT = "componentContext";

    private static ServletContext servletContext;

    private Context envContext;

    private ClassLoader classLoader;

    private final Map<String, Object> componentsMap = new LinkedHashMap<>();


    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";




    public void init(ServletContext servletContext) {
        ComponentContext.servletContext = servletContext;
        servletContext.setAttribute(COMPONENT_CONTEXT, this);
        // 获取当前 ServletContext（WebApp）ClassLoader
        this.classLoader = servletContext.getClassLoader();
        ControllerContext.initController();
        initEnvContext();
        instantiateComponents();
        initializeComponents();
    }

    private void initEnvContext() {
        if (envContext != null) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            close(context);
        }

    }

    private void initializeComponents() {
        componentsMap.forEach((name, component) -> {
            Class<?> componentClass = component.getClass();
            injectComponents(component, componentClass);
            // 初始阶段 - {@link PostConstruct}
            processPostConstruct(component, componentClass);
            // TODO 实现销毁阶段 - {@link PreDestroy}
            processPreDestroy();
        });
    }

    private void processPreDestroy() {
        //TODO
    }

    private void processPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredMethods())
                .filter(method -> {
                    int mod = method.getModifiers();
                    return !Modifier.isStatic(mod) &&
                            method.getParameterCount() == 0 &&
                            method.isAnnotationPresent(PostConstruct.class);
                }).forEach(method -> {
            method.setAccessible(true);
            try {
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    int mod = field.getModifiers();
                    return !Modifier.isStatic(mod) && field.isAnnotationPresent(Resource.class);
                }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String beanName = resource.name();
            Object bean = getComponentByName(beanName);
            field.setAccessible(true);
            try {
                field.set(component, bean);
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
            }
        });
    }

    private void instantiateComponents() {
        List<String> list = listAllCommentNames();
        list.forEach(name -> {
            componentsMap.put(name, lookupComponentByName(name));
        });
        ControllerContext.getAllController().forEach(controller -> componentsMap.put(controller.getClass().getName(),controller));
    }

    private List<String> listAllCommentNames() {
        return listCommentNames("/");
    }

    private <T> T lookupComponentByName(String name) {
        return executeInContext(context -> (T) context.lookup(name));
    }

    public  <T> T getComponentByName(String name) {
        return executeInContext(context -> (T) componentsMap.get(name));
    }

    private List<String> listCommentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> namingEnumeration = executeInContext(context, ctx -> ctx.list(name), true);
            if (namingEnumeration == null) {
                return Collections.emptyList();
            }
            List<String> names = Lists.newArrayList();
            while (namingEnumeration.hasMoreElements()) {
                NameClassPair element = namingEnumeration.nextElement();
                String className = element.getClassName();
                Class<?> targetClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)) {
                    names.addAll(listCommentNames(element.getName()));
                } else {
                    String fullName = element.getName().startsWith("/") ? element.getName() : name + "/" + element.getName();
                    names.add(fullName);
                }
            }
            return names;

        }, false);
    }


    public <T> T executeInContext(ThrowableFunction<Context, T> function) {
        return executeInContext(function, false);
    }


    public <T> T executeInContext(ThrowableFunction<Context, T> function, boolean ignoreException) {
        return executeInContext(envContext, function, ignoreException);
    }

    public <T> T executeInContext(Context context, ThrowableFunction<Context, T> function, boolean ignoreException) {
        T result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Exception e) {
            if (ignoreException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(COMPONENT_CONTEXT);
    }

    private void close(Context context) {
        if (servletContext != null) {
            ThrowableAction.execute(context::close);
        }
    }

    public void destroy() {
        close(envContext);
    }

}
