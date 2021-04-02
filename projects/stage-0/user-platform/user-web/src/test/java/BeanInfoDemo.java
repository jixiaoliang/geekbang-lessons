import org.geektimes.projects.user.domain.User;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jixiaoliang
 * @since 2021/04/02
 **/
public class BeanInfoDemo {
    public static void main(String[] args) throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);
        User u = new User();
        PropertyDescriptor[] propertyDescriptors =beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            try {
                Method m = propertyDescriptor.getWriteMethod();
                Class<?>[] clazz = m.getParameterTypes();
                if(clazz[0] ==Long.class){
                    m.invoke(u,Long.valueOf("1"));
                }
                if(clazz[0] ==String.class){
                    m.invoke(u,"2");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println(u);
    }
}
