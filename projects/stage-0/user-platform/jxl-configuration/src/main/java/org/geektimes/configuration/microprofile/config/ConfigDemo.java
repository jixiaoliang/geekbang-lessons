package org.geektimes.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.ConfigValue;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class ConfigDemo {

    public static void main(String[] args) {
        Config config = ConfigProvider.getConfig();

        String name = "java.specification.version";
        String appName = "application.name";
        ConfigValue configValue = config.getConfigValue(name);
        System.out.println(configValue);
        System.out.println(name+":"+config.getValue(name, Float.class));
        System.out.println(appName+":"+config.getValue(appName, String.class));
    }
}
