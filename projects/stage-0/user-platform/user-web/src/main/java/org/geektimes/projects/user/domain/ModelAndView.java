package org.geektimes.projects.user.domain;

import java.util.function.Consumer;

/**
 * @author jixiaoliang
 * @since 2021/02/28
 **/
public class ModelAndView<T> {

    private Consumer<T> action;

    private String view;

    public ModelAndView(Consumer<T> action, String view) {
        this.action = action;
        this.view = view;
    }

    public Consumer<T> getAction() {
        return action;
    }

    public void setAction(Consumer<T> action) {
        this.action = action;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
