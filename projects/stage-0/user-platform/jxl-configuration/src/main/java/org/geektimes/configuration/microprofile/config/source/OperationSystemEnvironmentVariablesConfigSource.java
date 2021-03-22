package org.geektimes.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author jixiaoliang
 * @since 2021/03/21
 **/
public class OperationSystemEnvironmentVariablesConfigSource extends MapBasedConfigSource{

    public OperationSystemEnvironmentVariablesConfigSource(){
        super("OperationSystemEnvironmentVariablesConfigSource",0);
    }
    @Override
    protected void prepareConfigData(Map properties) throws Throwable {
        properties.putAll(System.getenv());
    }
}
