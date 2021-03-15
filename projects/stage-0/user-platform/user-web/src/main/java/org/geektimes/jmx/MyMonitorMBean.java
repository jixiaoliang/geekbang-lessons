package org.geektimes.jmx;

/**
 * @author jixiaoliang
 * @since 2021/03/15
 **/
public interface MyMonitorMBean {
    String loadServerTime();

    void setPrefix(String prefix);
}
