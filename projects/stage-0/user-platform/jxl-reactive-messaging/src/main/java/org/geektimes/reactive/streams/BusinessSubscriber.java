package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/28
 **/
public class BusinessSubscriber<T> implements Subscriber<T> {

    private final Logger logger;

    private  Subscription subscription;

    private final long maxRequest;

    private int count =0;


    public BusinessSubscriber(long maxRequest) {
        this.maxRequest = maxRequest;
        logger = Logger.getLogger(BusinessSubscriber.class.getName());
    }


    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        subscription.request(maxRequest);
    }

    @Override
    public void onNext(T t) {
        count++;
        if (count > maxRequest) {
            subscription.cancel();
            return;
        }
        logger.info("收到数据:" + t);
    }

    @Override
    public void onError(Throwable t) {
        logger.info("接收数据异常:"+t);
    }

    @Override
    public void onComplete() {
        logger.info("接收数据完成");
    }
}
