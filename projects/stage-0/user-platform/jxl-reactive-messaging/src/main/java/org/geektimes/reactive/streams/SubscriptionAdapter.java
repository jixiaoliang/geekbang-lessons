package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/28
 **/
public class SubscriptionAdapter implements Subscription {

    private final Logger logger;

    private final DecoratingSubscriber<?> subscriber ;


    public SubscriptionAdapter(Subscriber<?> subscriber) {
        this.subscriber = new DecoratingSubscriber<>(subscriber);
        logger =Logger.getLogger(SubscriptionAdapter.class.getName());
    }

    @Override
    public void request(long n) {
        if (n < 1) {
            throw  new IllegalArgumentException("The number of elements to requests must be more than zero!");
        }
        logger.info("SubscriptionAdapter -> request:"+n);
        subscriber.setMaxRequest(n);
    }

    @Override
    public void cancel() {
        subscriber.canceled();
    }


    public DecoratingSubscriber<?> getSubscriber() {
        return subscriber;
    }

    public  Subscriber getSource(){
        return subscriber.getSource();
    }
}
