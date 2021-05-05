package org.geektimes.configuration.microprofile.config.constant;

import org.geektimes.configuration.microprofile.config.source.listener.ServletContextConfigInitializer;

/**
 * @author jixiaoliang
 * @since 2021/03/22
 **/
public interface Constants {
    /**
     * 全局配置key
     * @see ServletContextConfigInitializer
     */
    String GLOBAL_CONFIG ="globalConfig";

    /**
     * clientId
     */
    String GITEE_AUTH_CLIENTID ="gitee.auth.clientId";

    /**
     * clientSecret
     */
    String GITEE_AUTH_CLIENTSECRET ="gitee.auth.clientSecret";

    /**
     * REDIRECT_URI
     */
    String GITEE_AUTH_REDIRECT_URI ="gitee.auth.redirectUri";

    /**
     * applicationName
     */
    String APPLICATION_NAME ="application.name";
}
