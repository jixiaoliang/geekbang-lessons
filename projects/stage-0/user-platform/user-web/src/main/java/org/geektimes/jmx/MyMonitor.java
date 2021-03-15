package org.geektimes.jmx;

import com.sun.jmx.mbeanserver.Introspector;

import javax.annotation.PostConstruct;
import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public class MyMonitor implements MyMonitorMBean{
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String prefix ="default";

    @PostConstruct
    private void registerToMBeanServer() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        DynamicMBean mbean =  Introspector.makeDynamicMBean(this);
        mBeanServer.registerMBean(mbean, new ObjectName("org.geektimes.jmx.MyMonitor:type=serverTime"));
    }

    @Override
    public String loadServerTime() {
        return String.format("%s_%s", prefix, SIMPLE_DATE_FORMAT.format(new Date()));
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
