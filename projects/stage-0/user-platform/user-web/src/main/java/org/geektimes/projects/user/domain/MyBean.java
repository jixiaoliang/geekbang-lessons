package org.geektimes.projects.user.domain;

/**
 * @author jixiaoliang
 * @since 2021/03/07
 **/
public class MyBean {
    private String foo = "Default Foo";

    public String getFoo() {
        return (this.foo);
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    private int bar = 0;

    public int getBar() {
        return (this.bar);
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

}
