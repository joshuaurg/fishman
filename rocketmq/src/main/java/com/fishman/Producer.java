package com.fishman;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by hema on 16/9/18.
 */
public class Producer {
    public static void main(String[] args){
        DefaultMQProducer producer = new DefaultMQProducer("producerGroup");//producerGroup
        producer.setNamesrvAddr("123.56.8.162:9876");
        try {
            producer.start();
            for (int i = 1; i <= 5; i++) {
                Message msg = new Message("TOPIC-" + i, ("I am from Topic+"+i).getBytes());
                SendResult ret = producer.send(msg);
                System.out.println("msgId:"+ret.getMsgId()+" status:"+ret.getSendStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }
}
