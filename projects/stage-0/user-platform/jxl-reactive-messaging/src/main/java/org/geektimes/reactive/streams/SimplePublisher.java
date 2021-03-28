package org.geektimes.reactive.streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jixiaoliang
 * @since 2021/03/28
 **/
public class SimplePublisher<T> implements Publisher<T> {

    private List<Subscriber> subscriberList = new ArrayList<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        SubscriptionAdapter subscriptionAdapter = new SubscriptionAdapter(subscriber);
        subscriber.onSubscribe(subscriptionAdapter);

        subscriberList.add(subscriptionAdapter.getSubscriber());
    }


    public void publish(T data){
        subscriberList.forEach(subscriber -> {
            subscriber.onNext(data);
        });
    }

    public static void main(String[] args) {
        SimplePublisher<Integer> simplePublisher = new SimplePublisher<>();

        simplePublisher.subscribe(new BusinessSubscriber(2));
        for (int i = 0; i < 5; i++) {
            simplePublisher.publish(i);
        }
    }

}
