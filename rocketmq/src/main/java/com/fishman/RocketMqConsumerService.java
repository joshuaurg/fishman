package com.fishman;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.common.message.MessageExt;
import java.util.List;
import static com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
import static com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus.RECONSUME_LATER;
import static com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
import static com.fishman.MqStatus.SUCCESS;

/**
 * Created by hema on 16/9/18.
 */
public class RocketMqConsumerService extends ConsumerService{
    private static final String EXPRESSION = "*";
    private static final String ADDRESS = "123.56.8.162:9876";
    private static final String CONSUME_GROUP = "CONSUME_GROUP";
    private DefaultMQPushConsumer consumer;

    public void start() {
        try {
            consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(ADDRESS);
            consumer.setConsumerGroup(CONSUME_GROUP);
            consumer.setConsumeThreadMax(16);
            consumer.setConsumeThreadMin(4);
            for (String topic : getTopics()) {
                consumer.subscribe(topic, EXPRESSION);
            }
            if (getListener().order()) {
                consumer.registerMessageListener(new MessageListenerOrderlyAdaptor(getListener()));
            } else {
                consumer.registerMessageListener(new MessageListenerConcurrentlyAdaptor(getListener()));
            }
            consumer.start();
        } catch (Exception e) {
            throw new RuntimeException("rocket mq consumer start fail.", e);
        }
    }

    public void shutdown() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }

    private static final class MessageListenerConcurrentlyAdaptor implements MessageListenerConcurrently {

        private MqMessageListener listener;

        public MessageListenerConcurrentlyAdaptor(MqMessageListener listener) {
            this.listener = listener;
        }

        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MqStatus status = listener.onMessageReceived(msgs);
            return SUCCESS.equals(status) ? CONSUME_SUCCESS : RECONSUME_LATER;
        }
    }

    private static final class MessageListenerOrderlyAdaptor implements MessageListenerOrderly {

        private MqMessageListener listener;

        public MessageListenerOrderlyAdaptor(MqMessageListener listener) {
            this.listener = listener;
        }

        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            MqStatus status = listener.onMessageReceived(msgs);
            return SUCCESS.equals(status) ? ConsumeOrderlyStatus.SUCCESS : SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
    }
}
