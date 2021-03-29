package org.geektimes.projects.user.web.listener;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/03/28
 **/
@Deprecated
public class TestingComponent {
    private static final Logger logger = Logger.getLogger(TestingComponent.class.getName());

    @Resource(name = "jms/activemq-topic")
    private Topic topic;

    @Resource(name = "jms/message-producer")
    private MessageProducer messageProducer;

    @PostConstruct
    public void init() {
        logger.info("初始化topic:"+topic);
    }

    @PostConstruct
    public void sendMessage() throws Throwable {

        for (int i = 0; i < 5; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                long time = System.currentTimeMillis();
                //创建消息
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode() + " :" + time;

                ActiveMQTextMessage message = new ActiveMQTextMessage();
                message.setText(text);
                messageProducer.send(message);

                logger.info(String.format("[Thread : %s] Sent message : %s\n", Thread.currentThread().getName(), message.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
