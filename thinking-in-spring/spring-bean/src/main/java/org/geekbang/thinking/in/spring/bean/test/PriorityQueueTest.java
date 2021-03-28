package org.geekbang.thinking.in.spring.bean.test;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;

import java.util.PriorityQueue;

/**
 * @author jixiaoliang
 * @since 2021/03/25
 **/
public class PriorityQueueTest {
   private static PriorityQueue<User> userPriorityQueue = new PriorityQueue();

    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(1L);
        userPriorityQueue.add(user1);
        userPriorityQueue.add(user2);
        userPriorityQueue.forEach(System.out::println);
    }
}
