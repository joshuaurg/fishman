package com.fishman;

import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by hema on 16/9/18.
 */
public class MessageConsumerLisenter implements MqMessageListener{

    public boolean order() {
        return true;
    }

    public MqStatus onMessageReceived(List<MessageExt> messages) {

        for (int i = 0; i < messages.size(); i++) {
            try {
                System.out.println(Thread.currentThread().getName()+",receive msg:" + new String(messages.get(i).getBody(), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return MqStatus.SUCCESS;
    }
}
